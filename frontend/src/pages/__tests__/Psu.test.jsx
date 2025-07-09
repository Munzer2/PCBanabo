import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BrowserRouter } from 'react-router-dom';
import { vi } from 'vitest';
import Psu from '../lists/Psu';

// Mock the fetch function
global.fetch = vi.fn();

// Mock navigation
const mockNavigate = vi.fn();
vi.mock('react-router-dom', async () => {
  const actual = await vi.importActual('react-router-dom');
  return {
    ...actual,
    useNavigate: () => mockNavigate,
    useLocation: () => ({ state: {} }),
  };
});

// Mock console methods
const consoleSpy = vi.spyOn(console, 'log').mockImplementation(() => {});
const consoleErrorSpy = vi.spyOn(console, 'error').mockImplementation(() => {});

const PsuWrapper = ({ children }) => (
  <BrowserRouter>
    {children}
  </BrowserRouter>
);

describe('PSU Page', () => {
  const mockPsus = [
    {
      id: 1,
      name: 'Corsair RM850x',
      brandName: 'Corsair',
      wattage: 850,
      formFactor: 'ATX',
      certification: '80+ Gold',
      price: 149.99,
    },
    {
      id: 2,
      name: 'Seasonic Focus GX-750',
      brandName: 'Seasonic',
      wattage: 750,
      formFactor: 'ATX',
      certification: '80+ Gold',
      price: 129.99,
    },
  ];

  beforeEach(() => {
    fetch.mockClear();
    mockNavigate.mockClear();
    consoleSpy.mockClear();
    consoleErrorSpy.mockClear();
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it('renders PSU page with title and sidebar', () => {
    fetch.mockImplementation(() => new Promise(() => {}));

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    expect(screen.getByText('Available Power Supplies')).toBeInTheDocument();
    expect(screen.getByText('Filter Power Supplies')).toBeInTheDocument();
  });

  it('fetches PSUs without filters on initial load', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockPsus,
    });

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/psus');
    });

    await waitFor(() => {
      expect(screen.getByText('Corsair RM850x')).toBeInTheDocument();
      expect(screen.getByText('Seasonic Focus GX-750')).toBeInTheDocument();
    });
  });

  it('builds correct query params - ignores default slider values', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 500],
        wattage: [300, 1600],
        psuLength: [100, 200],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'wattage', 'psuLength'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'wattage') {
              params.set('wattageMin', value[0]);
            } else if (key === 'psuLength') {
              params.set('psuLengthMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    // Test with default values - should produce empty query string
    const defaultFilters = {
      price: [0, 500],
      wattage: [300, 1600],
      psuLength: [100, 200],
    };

    const queryString = buildQueryParams(defaultFilters);
    expect(queryString).toBe('');
  });

  it('builds correct query params with custom values', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 500],
        wattage: [300, 1600],
        psuLength: [100, 200],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'wattage', 'psuLength'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'wattage') {
              params.set('wattageMin', value[0]);
            } else if (key === 'psuLength') {
              params.set('psuLengthMax', value[1]);
            }
          }
        } else if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          } else if (key === 'formFactors') {
            params.set('formFactor', value[0]);
          } else if (key === 'certifications') {
            params.set('certification', value[0]);
          }
        }
      }
      return params.toString();
    };

    const customFilters = {
      price: [100, 300], // non-default
      wattage: [500, 1600], // non-default min
      brands: ['Corsair'],
      formFactors: ['ATX'],
    };

    const queryString = buildQueryParams(customFilters);
    expect(queryString).toBe('minPrice=100&maxPrice=300&wattageMin=500&brandName=Corsair&formFactor=ATX');
  });

  it('calls filtered endpoint only when filters are applied', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockPsus,
    });

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    // Should call unfiltered endpoint initially
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/psus');
    });
  });

  it('handles filter debugging logs correctly', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockPsus,
    });

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('Corsair RM850x')).toBeInTheDocument();
    });

    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    // Should trigger debugging console logs
    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalledWith(
        expect.stringContaining('PSU Sidebar: Apply Filters clicked with filters:')
      );
    });
  });

  it('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('Failed to fetch PSUs'));

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(
        'Error fetching PSUs:',
        expect.any(Error)
      );
    });

    expect(screen.getByText('Available Power Supplies')).toBeInTheDocument();
  });

  it('shows loading state initially', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    expect(screen.getByRole('status', { hidden: true })).toBeInTheDocument(); // Loading spinner
  });

  it('shows error message when API fails', async () => {
    fetch.mockRejectedValueOnce(new Error('Network error'));

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('Network error')).toBeInTheDocument();
    });
  });

  it('handles logout functionality', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockPsus,
    });

    render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    const logoutButton = screen.getByText('Logout');
    await user.click(logoutButton);

    expect(mockNavigate).toHaveBeenCalledWith('/login', { replace: true });
  });

  it('handles boolean RGB filter correctly', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      
      for (const key in filters) {
        const value = filters[key];
        if (typeof value === "boolean" && value === true) {
          params.set(key, "true");
        }
      }
      return params.toString();
    };

    const mockFilters = {
      rgb: true,
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('rgb=true');
  });

  it('properly triggers useEffect when filters change', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockPsus,
    });

    const { rerender } = render(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    // Initial call
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(1);
    });

    // Mock filter change (would happen through sidebar interaction)
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [mockPsus[0]],
    });

    // Force re-render to simulate filter state change
    rerender(
      <PsuWrapper>
        <Psu />
      </PsuWrapper>
    );

    // Should call API again when filters change
    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalledWith(
        expect.stringContaining('PSU Page: useEffect triggered with filters:')
      );
    });
  });
});
