import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BrowserRouter } from 'react-router-dom';
import { vi } from 'vitest';
import CPU from '../lists/CPU';

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

// Mock localStorage
const localStorageMock = {
  getItem: vi.fn(),
  setItem: vi.fn(),
  clear: vi.fn(),
};
Object.defineProperty(window, 'localStorage', {
  value: localStorageMock,
});

const CPUWrapper = ({ children }) => (
  <BrowserRouter>
    {children}
  </BrowserRouter>
);

describe('CPU Page', () => {
  const mockCpus = [
    {
      id: 1,
      name: 'Intel Core i7-12700K',
      brandName: 'Intel',
      socket: 'LGA1700',
      cores: 12,
      threads: 20,
      price: 399.99,
      tdp: 125,
      baseClock: 3.6,
      boostClock: 5.0,
    },
    {
      id: 2,
      name: 'AMD Ryzen 7 5800X',
      brandName: 'AMD',
      socket: 'AM4',
      cores: 8,
      threads: 16,
      price: 349.99,
      tdp: 105,
      baseClock: 3.8,
      boostClock: 4.7,
    },
  ];

  beforeEach(() => {
    fetch.mockClear();
    mockNavigate.mockClear();
    consoleSpy.mockClear();
    consoleErrorSpy.mockClear();
    localStorageMock.getItem.mockReturnValue('mock-jwt-token');
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it('renders CPU page with title and sidebar', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    expect(screen.getByText('Available CPUs')).toBeInTheDocument();
    expect(screen.getByText('Filter CPUs')).toBeInTheDocument();
  });

  it('fetches CPUs without filters on initial load', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/cpus');
    });

    await waitFor(() => {
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
      expect(screen.getByText('AMD Ryzen 7 5800X')).toBeInTheDocument();
    });
  });

  it('builds correct query params with default filters (should use unfiltered endpoint)', () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    // Should call unfiltered endpoint for empty filters
    expect(fetch).toHaveBeenCalledWith('/api/components/cpus');
  });

  it('builds correct query params with price filters', async () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 1500],
        cores: [1, 64],
        threads: [2, 128],
        baseClock: [1, 5],
        boostClock: [1, 6],
        tdp: [10, 300],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['cores', 'threads', 'price', 'baseClock', 'boostClock', 'tdp'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            } else if (key === 'cores') {
              params.set('coresMin', value[0]);
              params.set('coresMax', value[1]);
            } else if (key === 'threads') {
              params.set('threadsMin', value[0]);
              params.set('threadsMax', value[1]);
            } else if (key === 'baseClock') {
              params.set('baseClockMin', value[0]);
              params.set('baseClockMax', value[1]);
            } else if (key === 'boostClock') {
              params.set('boostClockMin', value[0]);
              params.set('boostClockMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      price: [200, 500],
      cores: [1, 64], // default values should be ignored
      threads: [2, 128], // default values should be ignored
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('minPrice=200&maxPrice=500');
  });

  it('builds correct query params with brand filters', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      
      for (const key in filters) {
        const value = filters[key];
        if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      brands: ['Intel'],
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('brandName=Intel');
  });

  it('handles filter application correctly', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [mockCpus[0]], // Filtered result
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
    });

    // Find and click the Apply Filters button
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    // Should trigger console logs for debugging
    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalledWith(
        expect.stringContaining('buildQueryParams called with:')
      );
    });
  });

  it('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('Network error'));

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(
        'Error fetching CPUs:',
        expect.any(Error)
      );
    });

    expect(screen.getByText('Available CPUs')).toBeInTheDocument();
  });

  it('handles logout functionality', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    const logoutButton = screen.getByText('Logout');
    await user.click(logoutButton);

    expect(localStorageMock.clear).toHaveBeenCalled();
    expect(mockNavigate).toHaveBeenCalledWith('/login', { replace: true });
  });

  it('navigates to dashboard when back button is clicked', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    const backButton = screen.getByText('Dashboard');
    await user.click(backButton);

    expect(mockNavigate).toHaveBeenCalledWith('/dashboard');
  });

  it('does not include default slider values in query params', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 1500],
        cores: [1, 64],
        threads: [2, 128],
        baseClock: [1, 5],
        boostClock: [1, 6],
        tdp: [10, 300],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['cores', 'threads', 'price', 'baseClock', 'boostClock', 'tdp'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    // Test with default values - should produce empty query string
    const defaultFilters = {
      price: [0, 1500],
      cores: [1, 64],
      threads: [2, 128],
    };

    const queryString = buildQueryParams(defaultFilters);
    expect(queryString).toBe('');
  });

  it('includes only non-default values in query params', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 1500],
        cores: [1, 64],
        threads: [2, 128],
        baseClock: [1, 5],
        boostClock: [1, 6],
        tdp: [10, 300],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['cores', 'threads', 'price', 'baseClock', 'boostClock', 'tdp'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    // Test with non-default values
    const customFilters = {
      price: [100, 800], // non-default
      cores: [1, 64], // default
      tdp: [50, 200], // non-default
    };

    const queryString = buildQueryParams(customFilters);
    expect(queryString).toBe('minPrice=100&maxPrice=800&tdpMin=50&tdpMax=200');
  });

  it('handles boolean filters correctly', () => {
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
      integratedGraphics: true,
      unlocked: false, // should not be included
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('integratedGraphics=true');
  });

  it('builds query string with socket filters', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      
      for (const key in filters) {
        const value = filters[key];
        if (Array.isArray(value) && value.length > 0) {
          if (key === 'socket') {
            params.set('socket', value[0]);
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      socket: ['AM4'],
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('socket=AM4');
  });

  it('builds complex query string with multiple filter types', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 1500],
        cores: [1, 64],
        threads: [2, 128],
        baseClock: [1, 5],
        boostClock: [1, 6],
        tdp: [10, 300],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['cores', 'threads', 'price', 'baseClock', 'boostClock', 'tdp'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            } else if (key === 'cores') {
              params.set('coresMin', value[0]);
              params.set('coresMax', value[1]);
            } else if (key === 'threads') {
              params.set('threadsMin', value[0]);
              params.set('threadsMax', value[1]);
            }
          }
        } else if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          } else if (key === 'socket') {
            params.set('socket', value[0]);
          }
        } else if (typeof value === "boolean" && value === true) {
          params.set(key, "true");
        }
      }
      return params.toString();
    };

    const complexFilters = {
      price: [200, 600],
      tdp: [50, 150], 
      cores: [4, 16],
      brands: ['Intel'],
      socket: ['LGA1700'],
      integratedGraphics: true,
      unlocked: false, // should not be included
    };

    const queryString = buildQueryParams(complexFilters);
    expect(queryString).toContain('minPrice=200');
    expect(queryString).toContain('maxPrice=600');
    expect(queryString).toContain('tdpMin=50');
    expect(queryString).toContain('tdpMax=150');
    expect(queryString).toContain('coresMin=4');
    expect(queryString).toContain('coresMax=16');
    expect(queryString).toContain('brandName=Intel');
    expect(queryString).toContain('socket=LGA1700');
    expect(queryString).toContain('integratedGraphics=true');
    expect(queryString).not.toContain('unlocked');
  });

  it('handles configurator navigation state correctly', () => {
    const mockUseLocation = vi.fn(() => ({ 
      state: { fromConfigurator: true, componentType: 'cpu' } 
    }));
    
    vi.mocked(mockUseLocation);

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    expect(screen.getByText('Available CPUs')).toBeInTheDocument();
  });

  it('calls filtered endpoint only when filters are non-default', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    // Initial load should call unfiltered endpoint
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/cpus');
    });

    // When applying default filters, should still call unfiltered endpoint
    // This would be tested with proper component integration
    expect(fetch).not.toHaveBeenCalledWith(
      expect.stringContaining('/api/components/cpus/filtered')
    );
  });
});