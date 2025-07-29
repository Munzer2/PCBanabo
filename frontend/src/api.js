import axios from 'axios';

// In Docker/production, use relative URLs to work with nginx proxy
// In development, use the backend URL directly
const getBaseURL = () => {
  if (import.meta.env.VITE_BACKEND_URL) {
    return import.meta.env.VITE_BACKEND_URL;
  }
  
  // Check if we're in Docker environment
  if (import.meta.env.DOCKER === 'true' || process.env.DOCKER === 'true') {
    return ''; // Use relative URLs in Docker (nginx will proxy)
  }
  
  // Development default
  return 'http://localhost:8080';
};

const api = axios.create({
  baseURL: getBaseURL()
});

api.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export default api;