import { vi } from 'vitest';

// Mock axios
const mockAxiosInstance = {
  interceptors: {
    request: {
      use: vi.fn(),
    },
    response: {
      use: vi.fn(),
    },
  },
  get: vi.fn(),
  post: vi.fn(),
  put: vi.fn(),
  delete: vi.fn(),
};

vi.mock('axios', () => ({
  default: {
    create: vi.fn(() => mockAxiosInstance),
  },
}));

describe('API Service', () => {
  const localStorageMock = {
    getItem: vi.fn(),
    setItem: vi.fn(),
    removeItem: vi.fn(),
  };
  
  Object.defineProperty(window, 'localStorage', {
    value: localStorageMock,
  });

  beforeEach(() => {
    vi.clearAllMocks();
    // Set Docker environment for tests
    process.env.DOCKER = 'true';
    process.env.VITE_API_BASE_URL = 'http://localhost';
  });

  it('creates axios instance with correct Docker base URL', async () => {
    const axios = await import('axios');
    const api = await import('../api');

    expect(axios.default.create).toHaveBeenCalledWith({
      baseURL: 'http://localhost',
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  });

  it('adds authorization header when token exists', () => {
    const mockToken = 'mock-jwt-token';
    localStorageMock.getItem.mockReturnValue(mockToken);

    // Import API to trigger interceptor setup
    import('../api');

    expect(mockAxiosInstance.interceptors.request.use).toHaveBeenCalled();
    
    // Get the interceptor function
    const interceptorFn = mockAxiosInstance.interceptors.request.use.mock.calls[0][0];
    
    const config = { headers: {} };
    const result = interceptorFn(config);

    expect(result.headers.Authorization).toBe(`Bearer ${mockToken}`);
  });

  it('does not add authorization header when no token exists', () => {
    localStorageMock.getItem.mockReturnValue(null);

    // Import API to trigger interceptor setup
    import('../api');

    const interceptorFn = mockAxiosInstance.interceptors.request.use.mock.calls[0][0];
    
    const config = { headers: {} };
    const result = interceptorFn(config);

    expect(result.headers.Authorization).toBeUndefined();
  });

  it('handles response interceptor for error handling', () => {
    import('../api');

    expect(mockAxiosInstance.interceptors.response.use).toHaveBeenCalled();
  });

  it('configures correct timeout for Docker environment', async () => {
    const axios = await import('axios');
    
    expect(axios.default.create).toHaveBeenCalledWith(
      expect.objectContaining({
        timeout: 10000, // 10 seconds for Docker
      })
    );
  });
});