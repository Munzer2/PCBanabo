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
  };
});

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
      brand: 'Intel',
      socket: 'LGA1700',
      cores: 12,
      threads: 20,
      price: 399.99,
    },
    {
      id: 2,
      name: 'AMD Ryzen 7 5800X',
      brand: 'AMD',
      socket: 'AM4',
      cores: 8,
      threads: 16,
      price: 349.99,
    },
  ];

  beforeEach(() => {
    fetch.mockClear();
    mockNavigate.mockClear();
    localStorageMock.getItem.mockReturnValue('mock-jwt-token');
  });

  afterEach(() => {
    vi.resetAllMocks();
  });

  it('renders CPU page with title', () => {
    fetch.mockImplementation(() => new Promise(() => {})); // Never resolves

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    expect(screen.getByText('Available CPUs')).toBeInTheDocument();
  });

  it('fetches and displays CPUs on initial load', async () => {
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    // Verify API call to correct Docker endpoint
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        'http://localhost/api/components/cpus',
        expect.objectContaining({
          headers: expect.objectContaining({
            'Authorization': 'Bearer mock-jwt-token',
          }),
        })
      );
    });

    // Verify CPUs are displayed
    await waitFor(() => {
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
      expect(screen.getByText('AMD Ryzen 7 5800X')).toBeInTheDocument();
    });
  });

  it('handles API error gracefully', async () => {
    fetch.mockRejectedValueOnce(new Error('Network error'));

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    await waitFor(() => {
      expect(fetch).toHaveBeenCalled();
    });

    // Should handle error without crashing
    expect(screen.getByText('Available CPUs')).toBeInTheDocument();
  });

  it('applies filters and fetches filtered results', async () => {
    const user = userEvent.setup();
    
    // Initial fetch
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => mockCpus,
    });

    // Filtered fetch
    const filteredCpus = [mockCpus[0]]; // Only Intel CPU
    fetch.mockResolvedValueOnce({
      ok: true,
      json: async () => filteredCpus,
    });

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    // Wait for initial load
    await waitFor(() => {
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
    });

    // Simulate filter application
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    // Verify filtered API call to correct Docker endpoint
    await waitFor(() => {
      expect(fetch).toHaveBeenCalledWith(
        expect.stringContaining('http://localhost/api/components/cpus/filtered'),
        expect.objectContaining({
          headers: expect.objectContaining({
            'Authorization': 'Bearer mock-jwt-token',
          }),
        })
      );
    });
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

  it('displays CPU details correctly', async () => {
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
      // Verify CPU details are displayed
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
      expect(screen.getByText('$399.99')).toBeInTheDocument();
      expect(screen.getByText('12 cores')).toBeInTheDocument();
      expect(screen.getByText('20 threads')).toBeInTheDocument();
      expect(screen.getByText('LGA1700')).toBeInTheDocument();
    });
  });

  it('handles unauthorized access', async () => {
    localStorageMock.getItem.mockReturnValue(null);

    render(
      <CPUWrapper>
        <CPU />
      </CPUWrapper>
    );

    await waitFor(() => {
      expect(mockNavigate).toHaveBeenCalledWith('/login', { replace: true });
    });
  });

  it('handles pagination if implemented', async () => {
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

    await waitFor(() => {
      expect(screen.getByText('Intel Core i7-12700K')).toBeInTheDocument();
    });

    // If pagination exists, test it
    const nextButton = screen.queryByText('Next');
    if (nextButton) {
      await user.click(nextButton);
      // Add expectations for pagination behavior
    }
  });
});