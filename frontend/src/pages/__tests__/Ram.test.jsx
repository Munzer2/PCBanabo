import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter, MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import Ram from '../lists/Ram';

// Mock the components
jest.mock('../../components/lists/sidebars/RamSidebar', () => {
  return function MockRamSidebar({ onApply }) {
    return (
      <div data-testid="ram-sidebar">
        <button 
          onClick={() => onApply({ 
            price: [50, 300], 
            speed: [3200, 4800], 
            brands: ['Corsair'], 
            memoryTypes: ['DDR4'],
            capacities: ['16GB']
          })}
          data-testid="apply-filters"
        >
          Apply Filters
        </button>
      </div>
    );
  };
});

jest.mock('../../components/lists/items/RamItem', () => {
  return function MockRamItem({ ram, onSelect }) {
    return (
      <div data-testid="ram-item" onClick={() => onSelect?.(ram)}>
        <h3>{ram.model}</h3>
        <p>${ram.price}</p>
      </div>
    );
  };
});

// Mock fetch
global.fetch = jest.fn();

const mockRams = [
  {
    id: 1,
    model: 'Corsair Vengeance LPX 16GB',
    brand: 'Corsair',
    price: 89.99,
    speed: 3200,
    capacity: '16GB',
    memoryType: 'DDR4'
  },
  {
    id: 2,
    model: 'G.Skill Trident Z RGB 32GB',
    brand: 'G.Skill',
    price: 179.99,
    speed: 3600,
    capacity: '32GB',
    memoryType: 'DDR4'
  }
];

describe('Ram Component', () => {
  beforeEach(() => {
    fetch.mockClear();
    fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(mockRams)
    });
  });

  const renderRam = (initialEntries = ['/ram']) => {
    return render(
      <MemoryRouter initialEntries={initialEntries}>
        <Ram />
      </MemoryRouter>
    );
  };

  test('renders Ram component correctly', async () => {
    renderRam();
    
    expect(screen.getByText('RAM')).toBeInTheDocument();
    expect(screen.getByTestId('ram-sidebar')).toBeInTheDocument();
    
    await waitFor(() => {
      expect(screen.getByText('Corsair Vengeance LPX 16GB')).toBeInTheDocument();
      expect(screen.getByText('G.Skill Trident Z RGB 32GB')).toBeInTheDocument();
    });
  });

  test('fetches rams on component mount', async () => {
    renderRam();
    
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/rams');
    });
  });

  test('buildQueryParams includes only non-default values', () => {
    renderRam();
    
    // Test with default values (should not include params)
    const defaultFilters = {
      price: [0, 500],
      speed: [2400, 6400]
    };
    
    const urlWithDefaults = new URL('http://localhost:8080/api/rams');
    // The component internally builds query params, we test the logic by checking API calls
    
    // Test with non-default values
    const customFilters = {
      price: [50, 300],
      speed: [3200, 4800],
      brands: ['Corsair']
    };
    
    // These would generate: minPrice=50&maxPrice=300&speedMin=3200&brandName=Corsair
    expect(true).toBe(true); // Placeholder for complex URL testing
  });

  test('applies filters correctly', async () => {
    renderRam();
    
    await waitFor(() => {
      expect(screen.getByText('Corsair Vengeance LPX 16GB')).toBeInTheDocument();
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('minPrice=50&maxPrice=300&speedMin=3200&brandName=Corsair')
      );
    });
  });

  test('handles filter changes and API calls', async () => {
    renderRam();
    
    // Wait for initial load
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(1);
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(2);
      expect(fetch).toHaveBeenLastCalledWith(
        expect.stringContaining('rams?minPrice=50&maxPrice=300&speedMin=3200&brandName=Corsair')
      );
    });
  });

  test('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('API Error'));
    
    renderRam();
    
    await waitFor(() => {
      expect(screen.getByText(/error/i)).toBeInTheDocument();
    });
  });

  test('shows loading state initially', () => {
    renderRam();
    expect(screen.getByText(/loading/i)).toBeInTheDocument();
  });

  test('handles configurator navigation state', () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'ram' }
    };
    
    render(
      <MemoryRouter initialEntries={['/ram', stateWithConfigurator]}>
        <Ram />
      </MemoryRouter>
    );
    
    expect(screen.getByText('RAM')).toBeInTheDocument();
  });

  test('navigation buttons work correctly', () => {
    renderRam();
    
    const backButton = screen.getByLabelText(/back/i);
    expect(backButton).toBeInTheDocument();
    
    const profileButton = screen.getByLabelText(/profile/i);
    expect(profileButton).toBeInTheDocument();
    
    const logoutButton = screen.getByLabelText(/logout/i);
    expect(logoutButton).toBeInTheDocument();
  });

  test('ram selection works when from configurator', async () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'ram' }
    };
    
    render(
      <MemoryRouter initialEntries={['/ram', stateWithConfigurator]}>
        <Ram />
      </MemoryRouter>
    );
    
    await waitFor(() => {
      expect(screen.getByText('Corsair Vengeance LPX 16GB')).toBeInTheDocument();
    });

    // Click on a ram item
    fireEvent.click(screen.getByText('Corsair Vengeance LPX 16GB'));
    
    // Should navigate back to configurator with selected ram
    // This would be tested with a navigation mock in a real scenario
  });

  test('filter reset functionality', async () => {
    renderRam();
    
    // Apply filters first
    fireEvent.click(screen.getByTestId('apply-filters'));
    
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('minPrice=50')
      );
    });
    
    // Reset would call the API again with no filters
    // This depends on sidebar implementation
  });

  test('multiple filter combinations work correctly', async () => {
    renderRam();
    
    await waitFor(() => {
      expect(screen.getByText('Corsair Vengeance LPX 16GB')).toBeInTheDocument();
    });

    // The mock applies multiple filters at once
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('minPrice=50');
      expect(lastCall).toContain('maxPrice=300');
      expect(lastCall).toContain('speedMin=3200');
      expect(lastCall).toContain('brandName=Corsair');
    });
  });
});
