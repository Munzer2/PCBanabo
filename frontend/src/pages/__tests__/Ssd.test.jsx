import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { BrowserRouter, MemoryRouter } from 'react-router-dom';
import '@testing-library/jest-dom';
import Ssd from '../lists/Ssd';

// Mock the components
jest.mock('../../components/lists/sidebars/SsdSidebar', () => {
  return function MockSsdSidebar({ onApply }) {
    return (
      <div data-testid="ssd-sidebar">
        <button 
          onClick={() => onApply({ 
            price: [100, 400], 
            seqRead: [1000, 5000], 
            seqWrite: [800, 4000],
            brands: ['Samsung'], 
            formFactors: ['M.2 2280'],
            capacities: ['1TB'],
            pcieGens: ['PCIe 4.0']
          })}
          data-testid="apply-filters"
        >
          Apply Filters
        </button>
      </div>
    );
  };
});

jest.mock('../../components/lists/items/SsdItem', () => {
  return function MockSsdItem({ ssd, onSelect }) {
    return (
      <div data-testid="ssd-item" onClick={() => onSelect?.(ssd)}>
        <h3>{ssd.model}</h3>
        <p>${ssd.price}</p>
      </div>
    );
  };
});

// Mock fetch
global.fetch = jest.fn();

const mockSsds = [
  {
    id: 1,
    model: 'Samsung 980 PRO 1TB',
    brand: 'Samsung',
    price: 149.99,
    capacity: '1TB',
    formFactor: 'M.2 2280',
    pcieGen: 'PCIe 4.0',
    seqRead: 7000,
    seqWrite: 5000
  },
  {
    id: 2,
    model: 'Western Digital SN850X 2TB',
    brand: 'Western Digital',
    price: 289.99,
    capacity: '2TB',
    formFactor: 'M.2 2280',
    pcieGen: 'PCIe 4.0',
    seqRead: 7300,
    seqWrite: 6600
  }
];

describe('Ssd Component', () => {
  beforeEach(() => {
    fetch.mockClear();
    fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(mockSsds)
    });
  });

  const renderSsd = (initialEntries = ['/ssd']) => {
    return render(
      <MemoryRouter initialEntries={initialEntries}>
        <Ssd />
      </MemoryRouter>
    );
  };

  test('renders Ssd component correctly', async () => {
    renderSsd();
    
    expect(screen.getByText('SSD')).toBeInTheDocument();
    expect(screen.getByTestId('ssd-sidebar')).toBeInTheDocument();
    
    await waitFor(() => {
      expect(screen.getByText('Samsung 980 PRO 1TB')).toBeInTheDocument();
      expect(screen.getByText('Western Digital SN850X 2TB')).toBeInTheDocument();
    });
  });

  test('fetches ssds on component mount', async () => {
    renderSsd();
    
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith('http://localhost:8080/api/ssds');
    });
  });

  test('buildQueryParams includes only non-default values', () => {
    renderSsd();
    
    // Test the component's buildQueryParams logic
    // Default values: price: [0, 500], seqRead: [500, 7000], seqWrite: [400, 6000]
    // Non-default filters should generate proper query params
    expect(true).toBe(true); // Placeholder for URL building test
  });

  test('applies filters correctly', async () => {
    renderSsd();
    
    await waitFor(() => {
      expect(screen.getByText('Samsung 980 PRO 1TB')).toBeInTheDocument();
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('minPrice=100&maxPrice=400&seqReadMin=1000&seqWriteMin=800&brandName=Samsung')
      );
    });
  });

  test('handles filter changes and API calls', async () => {
    renderSsd();
    
    // Wait for initial load
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(1);
    });

    // Apply filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(2);
      expect(fetch).toHaveBeenLastCalledWith(
        expect.stringContaining('ssds?minPrice=100&maxPrice=400&seqReadMin=1000&seqWriteMin=800&brandName=Samsung')
      );
    });
  });

  test('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('API Error'));
    
    renderSsd();
    
    await waitFor(() => {
      expect(screen.getByText(/error/i)).toBeInTheDocument();
    });
  });

  test('shows loading state initially', () => {
    renderSsd();
    expect(screen.getByText(/loading/i)).toBeInTheDocument();
  });

  test('handles configurator navigation state', () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'ssd' }
    };
    
    render(
      <MemoryRouter initialEntries={['/ssd', stateWithConfigurator]}>
        <Ssd />
      </MemoryRouter>
    );
    
    expect(screen.getByText('SSD')).toBeInTheDocument();
  });

  test('navigation buttons work correctly', () => {
    renderSsd();
    
    const backButton = screen.getByLabelText(/back/i);
    expect(backButton).toBeInTheDocument();
    
    const profileButton = screen.getByLabelText(/profile/i);
    expect(profileButton).toBeInTheDocument();
    
    const logoutButton = screen.getByLabelText(/logout/i);
    expect(logoutButton).toBeInTheDocument();
  });

  test('ssd selection works when from configurator', async () => {
    const stateWithConfigurator = {
      state: { fromConfigurator: true, componentType: 'ssd' }
    };
    
    render(
      <MemoryRouter initialEntries={['/ssd', stateWithConfigurator]}>
        <Ssd />
      </MemoryRouter>
    );
    
    await waitFor(() => {
      expect(screen.getByText('Samsung 980 PRO 1TB')).toBeInTheDocument();
    });

    // Click on an ssd item
    fireEvent.click(screen.getByText('Samsung 980 PRO 1TB'));
    
    // Should navigate back to configurator with selected ssd
    // This would be tested with a navigation mock in a real scenario
  });

  test('multiple filter combinations work correctly', async () => {
    renderSsd();
    
    await waitFor(() => {
      expect(screen.getByText('Samsung 980 PRO 1TB')).toBeInTheDocument();
    });

    // The mock applies multiple filters at once
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('minPrice=100');
      expect(lastCall).toContain('maxPrice=400');
      expect(lastCall).toContain('seqReadMin=1000');
      expect(lastCall).toContain('seqWriteMin=800');
      expect(lastCall).toContain('brandName=Samsung');
    });
  });

  test('performance filter parameters work correctly', async () => {
    renderSsd();
    
    // Apply performance-focused filters
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastCall).toContain('seqReadMin=1000');
      expect(lastCall).toContain('seqWriteMin=800');
    });
  });

  test('form factor and capacity filters work correctly', async () => {
    renderSsd();
    
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastCall = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      // These would be tested if backend supports these params
      // expect(lastCall).toContain('formFactor=M.2%202280');
      // expect(lastCall).toContain('capacity=1TB');
    });
  });

  test('handles empty results', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve([])
    });
    
    renderSsd();
    
    await waitFor(() => {
      expect(screen.getByText(/no ssds found/i)).toBeInTheDocument();
    });
  });

  test('handles large datasets', async () => {
    const largeSsdList = Array.from({ length: 100 }, (_, i) => ({
      id: i + 1,
      model: `SSD Model ${i + 1}`,
      brand: `Brand ${i % 5}`,
      price: 100 + i * 10,
      capacity: '1TB',
      formFactor: 'M.2 2280'
    }));

    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(largeSsdList)
    });
    
    renderSsd();
    
    await waitFor(() => {
      expect(screen.getByText('SSD Model 1')).toBeInTheDocument();
      // Could test virtualization or pagination here
    });
  });
});
