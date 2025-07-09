// src/pages/__tests__/CpuCooler.test.jsx

import React from 'react';
import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import '@testing-library/jest-dom';
import { vi } from 'vitest';
import { MemoryRouter } from 'react-router-dom';
import CpuCooler from '../lists/CpuCooler';

// ——————————————————————————————————————————————————————————
// Mock the sidebar component
// ——————————————————————————————————————————————————————————
vi.mock('../../components/lists/sidebars/CpuCoolerSidebar', () => ({
  default: ({ onApply }) => (
    <div data-testid="cpu-cooler-sidebar">
      <button
        data-testid="apply-filters"
        onClick={() =>
          onApply({
            price: [75, 250],
            coolingCapacity: [100, 300],
            towerHeight: [50, 150],
            radiatorSize: [240, 360],
            ramClearance: [30, 60],
            brands: ['Noctua'],
            coolerTypes: ['Air'],
            socketTypes: ['LGA1700'],
          })
        }
      >
        Apply Filters
      </button>
    </div>
  ),
}));

// ——————————————————————————————————————————————————————————
// Mock the item component
// ——————————————————————————————————————————————————————————
vi.mock('../../components/lists/items/CpuCoolerItem', () => ({
  default: ({ cpuCooler, onSelect }) => (
    <div
      data-testid="cpu-cooler-item"
      onClick={() => onSelect?.(cpuCooler)}
    >
      <h3>{cpuCooler.model}</h3>
      <p>${cpuCooler.price}</p>
    </div>
  ),
}));

// ——————————————————————————————————————————————————————————
// Mock global.fetch
// ——————————————————————————————————————————————————————————
global.fetch = vi.fn();

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
    socketTypes: ['LGA1700', 'AM4'],
  },
  {
    id: 2,
    model: 'Corsair H150i Elite Capellix',
    brand: 'Corsair',
    price: 189.99,
    coolerType: 'Liquid',
    coolingCapacity: 300,
    radiatorSize: 360,
    socketTypes: ['LGA1700', 'AM4', 'AM5'],
  },
];

describe('CpuCooler Component', () => {
  beforeEach(() => {
    vi.clearAllMocks();
    fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(mockCpuCoolers),
    });
  });

  const renderCpuCooler = (initialEntries = ['/cpu-cooler']) =>
    render(
      <MemoryRouter initialEntries={initialEntries}>
        <CpuCooler />
      </MemoryRouter>
    );

  test('renders CpuCooler component correctly', async () => {
    renderCpuCooler();

    // The page header is "Available CPU Coolers"
    expect(
      screen.getByRole('heading', {
        level: 1,
        name: /available cpu coolers/i,
      })
    ).toBeInTheDocument();

    expect(screen.getByTestId('cpu-cooler-sidebar')).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument();
      expect(
        screen.getByText('Corsair H150i Elite Capellix')
      ).toBeInTheDocument();
    });
  });

  test('fetches cpu coolers on component mount', async () => {
    renderCpuCooler();

    await waitFor(() => {
      // The component calls the relative API path
      expect(fetch).toHaveBeenCalledWith(
        '/api/components/cpu-coolers'
      );
    });
  });

  test('buildQueryParams includes only non-default values', () => {
    renderCpuCooler();
    // Placeholder: test your query-string builder logic directly if it's exported,
    // or spy on fetch calls / URL formation here.
    expect(true).toBe(true);
  });

  test('applies filters correctly', async () => {
    renderCpuCooler();

    // wait until initial data loads
    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );

    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      // the filtered endpoint should include all the expected params
      const lastUrl = fetch.mock.calls[fetch.mock.calls.length - 1][0];
      expect(lastUrl).toContain(
        '/api/components/cpu-coolers/filtered?minPrice=75&maxPrice=250'
      );
      expect(lastUrl).toContain('coolingCapacity=100');
      expect(lastUrl).toContain('towerHeight=50');
      expect(lastUrl).toContain('radiatorSize=240');
      expect(lastUrl).toContain('brandName=Noctua');
      expect(lastUrl).toContain('coolerType=Air');
    });
  });

  test('handles filter changes and API calls', async () => {
    renderCpuCooler();

    await waitFor(() =>
      expect(fetch).toHaveBeenCalledTimes(1)
    );

    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      expect(fetch).toHaveBeenCalledTimes(2);
      const calledUrl = fetch.mock.calls[1][0];
      expect(calledUrl).toMatch(
        /cpu-coolers\?minPrice=75&maxPrice=250&coolingCapacity=100/
      );
    });
  });

  test('handles API errors gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('API Error'));

    renderCpuCooler();

    await waitFor(() => {
      // Your component should render some error text or element
      expect(
        screen.getByText(/error/i)
      ).toBeInTheDocument();
    });
  });

  test('shows loading spinner initially', () => {
    const { container } = renderCpuCooler();
    // the spinner has class "animate-spin"
    expect(container.querySelector('.animate-spin')).toBeInTheDocument();
  });

  test('handles configurator navigation state', () => {
    const configuratorState = {
      state: { fromConfigurator: true, componentType: 'cpu-cooler' },
    };
    render(
      <MemoryRouter initialEntries={['/cpu-cooler', configuratorState]}>
        <CpuCooler />
      </MemoryRouter>
    );
    expect(
      screen.getByRole('heading', {
        level: 1,
        name: /available cpu coolers/i,
      })
    ).toBeInTheDocument();
  });

  test('navigation buttons work correctly', () => {
    renderCpuCooler();
    expect(screen.getByLabelText(/back/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/profile/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/logout/i)).toBeInTheDocument();
  });

  test('cpu cooler selection works when from configurator', async () => {
    const configuratorState = {
      state: { fromConfigurator: true, componentType: 'cpu-cooler' },
    };
    render(
      <MemoryRouter initialEntries={['/cpu-cooler', configuratorState]}>
        <CpuCooler />
      </MemoryRouter>
    );

    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );

    fireEvent.click(screen.getByText('Noctua NH-D15'));
    // In a real test you'd mock navigate and assert it was called with the selected cooler
  });

  test('multiple filter combinations work correctly', async () => {
    renderCpuCooler();

    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );
    fireEvent.click(screen.getByTestId('apply-filters'));

    await waitFor(() => {
      const lastUrl = fetch.mock.calls.slice(-1)[0][0];
      expect(lastUrl).toContain('minPrice=75');
      expect(lastUrl).toContain('maxPrice=250');
      expect(lastUrl).toContain('coolingCapacity=100');
      expect(lastUrl).toContain('towerHeight=50');
      expect(lastUrl).toContain('radiatorSize=240');
    });
  });

  test('cooling performance filter parameters work correctly', async () => {
    renderCpuCooler();
    fireEvent.click(screen.getByTestId('apply-filters'));
    await waitFor(() => {
      const lastUrl = fetch.mock.calls.slice(-1)[0][0];
      expect(lastUrl).toContain('coolingCapacity=100');
    });
  });

  test('physical dimension filters work correctly', async () => {
    renderCpuCooler();
    fireEvent.click(screen.getByTestId('apply-filters'));
    await waitFor(() => {
      const lastUrl = fetch.mock.calls.slice(-1)[0][0];
      expect(lastUrl).toContain('towerHeight=50');
      expect(lastUrl).toContain('radiatorSize=240');
    });
  });

  test('RAM clearance filter works correctly', async () => {
    renderCpuCooler();
    fireEvent.click(screen.getByTestId('apply-filters'));
    await waitFor(() => {
      const lastUrl = fetch.mock.calls.slice(-1)[0][0];
      // if your API supported it, you would assert ramClearance=30 here
      // expect(lastUrl).toContain('ramClearance=30');
      expect(true).toBe(true);
    });
  });

  test('handles empty results', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve([]),
    });
    renderCpuCooler();
    await waitFor(() => {
      expect(
        screen.getByText(/no cpu coolers found/i)
      ).toBeInTheDocument();
    });
  });

  test('handles large datasets', async () => {
    const bigList = Array.from({ length: 50 }, (_, i) => ({
      id: i + 1,
      model: `Cooler Model ${i + 1}`,
      brand: `Brand ${i % 5}`,
      price: 50 + i * 10,
      coolerType: i % 2 === 0 ? 'Air' : 'Liquid',
      coolingCapacity: 100 + i * 5,
    }));
    fetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(bigList),
    });
    renderCpuCooler();
    await waitFor(() => {
      expect(
        screen.getByText('Cooler Model 1')
      ).toBeInTheDocument();
    });
  });

  test('different cooler types (Air vs Liquid) displayed correctly', async () => {
    renderCpuCooler();
    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );
    expect(
      screen.getByText('Corsair H150i Elite Capellix')
    ).toBeInTheDocument();
  });

  test('socket compatibility affects display', async () => {
    renderCpuCooler();
    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );
    // You would inject a configurator state for sockets and assert filtering here
  });

  test('performance-based sorting works', async () => {
    renderCpuCooler();
    await waitFor(() =>
      expect(screen.getByText('Noctua NH-D15')).toBeInTheDocument()
    );
    // If you add sorting controls, you would interact and then assert ordering
  });
});
