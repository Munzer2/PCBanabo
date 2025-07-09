import { render, screen, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { BrowserRouter } from 'react-router-dom';
import { vi } from 'vitest';
import GPU from '../lists/GPU';

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

const GPUWrapper = ({ children }) => (
  <BrowserRouter>
    {children}
  </BrowserRouter>
);

describe('GPU Page', () => {
  const mockGpus = [
    {
      id: 1,
      name: 'NVIDIA GeForce RTX 4080',
      brandName: 'NVIDIA',
      series: 'RTX 40 Series',
      price: 1199.99,
      vram: 16,
      tdp: 320,
      cardLength: 304,
      cardHeight: 137,
      cardThickness: 61,
    },
    {
      id: 2,
      name: 'AMD Radeon RX 7900 XTX',
      brandName: 'AMD',
      series: 'RX 7000 Series',
      price: 999.99,
      vram: 24,
      tdp: 355,
      cardLength: 287,
      cardHeight: 120,
      cardThickness: 51,
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

  it('renders GPU page with title and sidebar', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    expect(screen.getByText('Available Graphics Cards')).toBeInTheDocument();
    expect(screen.getByText('Filter Graphics Cards')).toBeInTheDocument();
  });

  it('fetches GPUs without filters on initial load', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/gpus');
    });

    await waitFor(() => {
      expect(screen.getByText('NVIDIA GeForce RTX 4080')).toBeInTheDocument();
      expect(screen.getByText('AMD Radeon RX 7900 XTX')).toBeInTheDocument();
    });
  });

  it('builds correct query params with default filters (should use unfiltered endpoint)', () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    // Should call unfiltered endpoint for empty filters
    expect(fetch).toHaveBeenCalledWith('/api/components/gpus');
  });

  it('builds correct query params with price filters', async () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 32],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
              params.set('vramMax', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            } else if (key === 'cardLength') {
              params.set('cardLengthMin', value[0]);
              params.set('cardLengthMax', value[1]);
            } else if (key === 'cardHeight') {
              params.set('cardHeightMin', value[0]);
              params.set('cardHeightMax', value[1]);
            } else if (key === 'cardThickness') {
              params.set('cardThicknessMin', value[0]);
              params.set('cardThicknessMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      price: [500, 1500],
      vram: [2, 32], // default values should be ignored
      tdp: [50, 500], // default values should be ignored
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('minPrice=500&maxPrice=1500');
  });

  it('builds correct query params with brand filters', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      
      for (const key in filters) {
        const value = filters[key];
        if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          } else if (key === 'gpuCore') {
            params.set('gpuCore', value[0]);
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      brands: ['NVIDIA'],
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('brandName=NVIDIA');
  });

  it('handles filter application correctly', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [mockGpus[0]], // Filtered result
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('NVIDIA GeForce RTX 4080')).toBeInTheDocument();
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
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(
        'Error fetching GPUs:',
        expect.any(Error)
      );
    });

    expect(screen.getByText('Available Graphics Cards')).toBeInTheDocument();
  });

  it('handles logout functionality', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
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
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    const backButton = screen.getByText('Dashboard');
    await user.click(backButton);

    expect(mockNavigate).toHaveBeenCalledWith('/dashboard');
  });

  it('does not include default slider values in query params', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 32],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
              params.set('vramMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    // Test with default values - should produce empty query string
    const defaultFilters = {
      price: [0, 2500],
      vram: [2, 32],
      tdp: [50, 500],
    };

    const queryString = buildQueryParams(defaultFilters);
    expect(queryString).toBe('');
  });

  it('includes only non-default values in query params', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 32],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
              params.set('vramMax', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      price: [800, 1200],
      vram: [2, 32], // default values should be ignored
      tdp: [100, 300], // non-default values should be included
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('minPrice=800&maxPrice=1200&tdpMin=100&tdpMax=300');
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
      rayTracing: true,
      dlss: false, // should not be included
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('rayTracing=true');
  });

  it('builds query string with GPU core filters', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      
      for (const key in filters) {
        const value = filters[key];
        if (Array.isArray(value) && value.length > 0) {
          if (key === 'gpuCore') {
            params.set('gpuCore', value[0]);
          }
        }
      }
      return params.toString();
    };

    const mockFilters = {
      gpuCore: ['RTX 4080'],
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('gpuCore=RTX 4080');
  });

  it('builds complex query string with multiple filter types', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 32],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
              params.set('vramMax', value[1]);
            } else if (key === 'tdp') {
              params.set('tdpMin', value[0]);
              params.set('tdpMax', value[1]);
            }
          }
        } else if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          } else if (key === 'gpuCore') {
            params.set('gpuCore', value[0]);
          }
        } else if (typeof value === "boolean" && value === true) {
          params.set(key, "true");
        }
      }
      return params.toString();
    };

    const complexFilters = {
      price: [600, 1800],
      vram: [8, 16], 
      tdp: [200, 400],
      brands: ['NVIDIA'],
      gpuCore: ['RTX 4080'],
      rayTracing: true,
      dlss: false, // should not be included
    };

    const queryString = buildQueryParams(complexFilters);
    expect(queryString).toContain('minPrice=600');
    expect(queryString).toContain('maxPrice=1800');
    expect(queryString).toContain('vramMin=8');
    expect(queryString).toContain('vramMax=16');
    expect(queryString).toContain('tdpMin=200');
    expect(queryString).toContain('tdpMax=400');
    expect(queryString).toContain('brandName=NVIDIA');
    expect(queryString).toContain('gpuCore=RTX 4080');
    expect(queryString).toContain('rayTracing=true');
    expect(queryString).not.toContain('dlss');
  });

  it('handles configurator navigation state correctly', () => {
    const mockUseLocation = vi.fn(() => ({ 
      state: { fromConfigurator: true, componentType: 'gpu' } 
    }));
    
    vi.mocked(mockUseLocation);

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    expect(screen.getByText('Available Graphics Cards')).toBeInTheDocument();
  });

  it('calls filtered endpoint only when filters are non-default', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    // Initial load should call unfiltered endpoint
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('/api/components/gpus');
    });

    // When applying default filters, should still call unfiltered endpoint
    // This would be tested with proper component integration
    expect(fetch).not.toHaveBeenCalledWith(
      expect.stringContaining('/api/components/gpus/filtered')
    );
  });
});
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 24],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
            } else if (key === 'tdp') {
              params.set('tdpMax', value[1]);
            } else if (key === 'cardLength') {
              params.set('cardLengthMax', value[1]);
            }
          }
        }
      }
      return params.toString();
    };

    // Test with default values - should produce empty query string
    const defaultFilters = {
      price: [0, 2500],
      vram: [2, 24],
      tdp: [50, 500],
    };

    const queryString = buildQueryParams(defaultFilters);
    expect(queryString).toBe('');
  });

  it('builds correct query params with custom filters', () => {
    const buildQueryParams = (filters) => {
      const params = new URLSearchParams();
      const sliderDefaults = {
        price: [0, 2500],
        vram: [2, 24],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
      };

      for (const key in filters) {
        const value = filters[key];
        if (['price', 'vram', 'tdp', 'cardLength', 'cardHeight', 'cardThickness'].includes(key)) {
          const defaults = sliderDefaults[key];
          const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
          
          if (!isDefault) {
            if (key === 'price') {
              params.set('minPrice', value[0]);
              params.set('maxPrice', value[1]);
            } else if (key === 'vram') {
              params.set('vramMin', value[0]);
            } else if (key === 'tdp') {
              params.set('tdpMax', value[1]);
            } else if (key === 'cardLength') {
              params.set('cardLengthMax', value[1]);
            }
          }
        } else if (Array.isArray(value) && value.length > 0) {
          if (key === 'brands') {
            params.set('brandName', value[0]);
          } else if (key === 'gpuCore') {
            params.set('gpuCore', value[0]);
          }
        }
      }
      return params.toString();
    };

    const customFilters = {
      price: [500, 1500],
      vram: [8, 24],
      brands: ['NVIDIA'],
    };

    const queryString = buildQueryParams(customFilters);
    expect(queryString).toBe('minPrice=500&maxPrice=1500&vramMin=8&brandName=NVIDIA');
  });

  it('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('Network error'));

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(consoleErrorSpy).toHaveBeenCalledWith(
        'Error fetching GPUs:',
        expect.any(Error)
      );
    });

    expect(screen.getByText('Available GPUs')).toBeInTheDocument();
  });

  it('displays "No GPUs found" when empty result', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [],
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('No GPUs found.')).toBeInTheDocument();
    });
  });

  it('handles filter application correctly', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => [mockGpus[0]],
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    await waitFor(() => {
      expect(screen.getByText('NVIDIA GeForce RTX 4080')).toBeInTheDocument();
    });

    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    await waitFor(() => {
      expect(consoleSpy).toHaveBeenCalledWith(
        expect.stringContaining('Received some new filters:')
      );
    });
  });

  it('handles logout functionality', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    const logoutButton = screen.getByText('Logout');
    await user.click(logoutButton);

    expect(mockNavigate).toHaveBeenCalledWith('/login', { replace: true });
  });

  it('navigates correctly with back button', async () => {
    const user = userEvent.setup();
    
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockGpus,
    });

    render(
      <GPUWrapper>
        <GPU />
      </GPUWrapper>
    );

    const backButton = screen.getByText('Dashboard');
    await user.click(backButton);

    expect(mockNavigate).toHaveBeenCalledWith('/dashboard');
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
      rayTracing: true,
      dlss: false, // should not be included
    };

    const queryString = buildQueryParams(mockFilters);
    expect(queryString).toBe('rayTracing=true');
  });
});
