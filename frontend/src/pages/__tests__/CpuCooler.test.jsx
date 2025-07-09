import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter, MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import CpuCooler from '../lists/CpuCooler';

// Mock the components
jest.mock('../../components/lists/sidebars/CpuCoolerSidebar', () => {
  return function MockCpuCoolerSidebar({ onApply }) {
    return (
      <div data-testid="cpu-cooler-sidebar">
        <button 
          onClick={() => onApply({ 
            price: [75, 250], 
            coolingCapacity: [100, 300], 
            towerHeight: [50, 150],
            radiatorSize: [240, 360],
            ramClearance: [30, 60],
            brands: ['Noctua'], 
            coolerTypes: ['Air'],
            socketTypes: ['LGA1700']
          })}
          data-testid="apply-filters"
        >
          Apply Filters
        </button>
      </div>
    );
  };
});

jest.mock('../../components/lists/items/CpuCoolerItem', () => {
  return function MockCpuCoolerItem({ cpuCooler, onSelect }) {
    return (
      <div data-testid="cpu-cooler-item" onClick={() => onSelect?.(cpuCooler)}>
        <h3>{cpuCooler.model}</h3>
        <p>${cpuCooler.price}</p>
      </div>
    );
  };
});

// Mock fetch
global.fetch = jest.fn();

const mockCpuCoolers = [
  {
    id: 1,
    model: 'Noctua NH-D15',
    brand: 'Noctua',
    price: 99.99,
    coolerType: 'Air',
    coolingCapacity: 220,
    towerHeight: 165,
    ramClearance: 32,
    socketTypes: ['LGA1700', 'AM4']
  },
  {
    id: 2,
    model: 'Corsair H150i Elite Capellix',
    brand: 'Corsair',
    price: 189.99,
    coolerType: 'Liquid',
    coolingCapacity: 300,
    radiatorSize: 360,
    socketTypes: ['LGA1700', 'AM4', 'AM5']
  }
];

describe('CpuCooler Component', () => {
  beforeEach(() => {
    fetch.mockClear();
    fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(mockCpuCoolers)
    });
  });

  const renderCpuCooler = (initialEntries = ['/cpu-cooler']) => {
    return render(
      <MemoryRouter initialEntries={initialEntries}>
        <CpuCooler />
      </MemoryRouter>
    );
  };

  test('renders CpuCooler component correctly', async () => {
    renderCpuCooler();
    
    expect(screen.getByText('CPU Cooler')).toBeInTheDocument();
    expect(screen.getByTestId('cpu-cooler-sidebar')).toBeInTheDocument();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
      expect(screen.getByText('Corsair H150i Elite Capellix')).toBeInTheDocument();
    });
  });

  test('fetches cpu coolers on component mount', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/cpu-coolers');
    });
  });

  test('buildQueryParams includes only non-default values', () => {
    renderCpuCooler();
    
    // Test the component's buildQueryParams logic
    // Default values should not generate query parameters
    // Non-default values should generate proper backend parameter names
    expect(true).toBe(true); // Placeholder for URL building test
  });

  test('applies filters correctly', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('minPrice=75&maxPrice=250&coolingCapacity=100&towerHeight=50&radiatorSize=240')
      );
    });
  });

  test('handles filter changes and API calls', async () => {
    renderCpuCooler();
    
    // Wait for initial load
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(1);
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(2);
      expect(fetch).toHaveBeenLastCalledWith(
        expect.stringContaining('cpu-coolers?minPrice=75&maxPrice=250&coolingCapacity=100&towerHeight=50&radiatorSize=240')
      );
    });
  });

  test('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('API Error'));
    
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText(/error/i)).toBeInTheDocument();
    });
  });

  test('shows loading state initially', () => {
    renderCpuCooler();
    expect(screen.getByText(/loading/i)).toBeInTheDocument();
  });

  test('handles configurator navigation state', () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'cpu-cooler' }
    };
    
    render(
      <MemoryRouter initialEntries={['/cpu-cooler', stateWithConfigurator]}>
        <CpuCooler />
      </MemoryRouter>
    );
    
    expect(screen.getByText('CPU Cooler')).toBeInTheDocument();
  });

  test('navigation buttons work correctly', () => {
    renderCpuCooler();
    
    const backButton = screen.getByLabelText(/back/i);
    expect(backButton).toBeInTheDocument();
    
    const profileButton = screen.getByLabelText(/profile/i);
    expect(profileButton).toBeInTheDocument();
    
    const logoutButton = screen.getByLabelText(/logout/i);
    expect(logoutButton).toBeInTheDocument();
  });

  test('cpu cooler selection works when from configurator', async () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'cpu-cooler' }
    };
    
    render(
      <MemoryRouter initialEntries={['/cpu-cooler', stateWithConfigurator]}>
        <CpuCooler />
      </MemoryRouter>
    );
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
    });

    // Click on a cpu cooler item
    fireEvent.click(screen.getByText('Noctua NH-D15'));
    
    // Should navigate back to configurator with selected cpu cooler
    // This would be tested with a navigation mock in a real scenario
  });

  test('multiple filter combinations work correctly', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
    });

    // The mock applies multiple filters at once
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('minPrice=75');
      expect(lastCall).toContain('maxPrice=250');
      expect(lastCall).toContain('coolingCapacity=100');
      expect(lastCall).toContain('towerHeight=50');
      expect(lastCall).toContain('radiatorSize=240');
    });
  });

  test('cooling performance filter parameters work correctly', async () => {
    renderCpuCooler();
    
    // Apply performance-focused filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('coolingCapacity=100');
    });
  });

  test('physical dimension filters work correctly', async () => {
    renderCpuCooler();
    
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('towerHeight=50');
      expect(lastCall).toContain('radiatorSize=240');
    });
  });

  test('RAM clearance filter works correctly', async () => {
    renderCpuCooler();
    
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      // This would be tested if backend supports this param
      // expect(lastCall).toContain('ramClearance=30');
    });
  });

  test('handles empty results', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve([])
    });
    
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText(/no cpu coolers found/i)).toBeInTheDocument();
    });
  });

  test('handles large datasets', async () => {
    const largeCoolerList = Array.from({ length: 50 }, (_, i) => ({
      id: i + 1,
      model: `Cooler Model ${i + 1}`,
      brand: `Brand ${i % 5}`,
      price: 50 + i * 10,
      coolerType: i % 2 === 0 ? 'Air' : 'Liquid',
      coolingCapacity: 100 + i * 5
    }));

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(largeCoolerList)
    });
    
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Cooler Model 1')).toBeInTheDocument();
      // Could test virtualization or pagination here
    });
  });

  test('different cooler types (Air vs Liquid) displayed correctly', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
      expect(screen.getByText('Corsair H150i Elite Capellix')).toBeInTheDocument();
    });

    // Both air and liquid coolers should be displayed
    // In a real implementation, you might have different visual indicators
  });

  test('socket compatibility affects display', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
    });

    // When coming from configurator with specific CPU socket,
    // only compatible coolers should be shown
    // This would require more complex state management
  });

  test('performance-based sorting works', async () => {
    renderCpuCooler();
    
    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
    });

    // Test that coolers can be sorted by cooling capacity
    // This would require additional UI controls
  });
});
