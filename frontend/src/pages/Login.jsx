import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const { data } = await api.post('/auth/login', { email, password });
      localStorage.setItem('token', data.token);
      localStorage.setItem('userId', data.user.id);
      navigate('/dashboard', { replace: true });
    } catch (err) {
      setError('Invalid email or password');
    }
  };
  return (
    <div className="min-h-screen flex items-center justify-center bg-[#0a0e17] px-4">
      <div className="bg-[#1a1a1a] p-8 rounded-lg border border-[#2c2c2c] shadow-2xl w-full max-w-md">
        <div className="flex justify-center mb-8">
          <div className="h-12 w-12 rounded-lg bg-gradient-to-br from-[#38b2ac] to-[#3182ce] flex items-center justify-center shadow-lg">
            <svg className="h-7 w-7 text-white" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
              <path d="M2 3h20v14H2z"></path>
              <path d="M8 21h8"></path>
              <path d="M12 17v4"></path>
            </svg>
          </div>
        </div>
        <h1 className="text-2xl font-bold text-white mb-2 text-center">
          Welcome Back
        </h1>
        <p className="text-center text-gray-400 mb-8">Sign in to continue to PCBanabo</p>

        <form onSubmit={handleSubmit} className="space-y-6">
          <div>
            <label htmlFor="email" className="block text-sm font-medium text-gray-300 mb-2">
              Email address
            </label>
            <div className="relative rounded-md shadow-sm">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
                </svg>
              </div>              <input
                id="email"
                type="email"
                value={email}
                onChange={e => setEmail(e.target.value)}
                required
                className="w-full pl-10 pr-3 py-3 bg-[#252525] border border-[#3a3a3a] rounded-md 
                         text-gray-200 placeholder-gray-500 focus:outline-none focus:ring-2
                         focus:ring-[#38b2ac] focus:border-transparent"
                placeholder="you@example.com"
              />
            </div>
          </div>

          <div>
            <div className="flex justify-between items-center mb-2">
              <label htmlFor="password" className="block text-sm font-medium text-gray-300">
                Password
              </label>
              <a href="#" className="text-xs text-[#38b2ac] hover:text-[#2c9b95]">Forgot password?</a>
            </div>
            <div className="relative rounded-md shadow-sm">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                </svg>
              </div>              <input
                id="password"
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                required
                className="w-full pl-10 pr-3 py-3 bg-[#252525] border border-[#3a3a3a] rounded-md 
                         text-gray-200 placeholder-gray-500 focus:outline-none focus:ring-2
                         focus:ring-[#38b2ac] focus:border-transparent"
                placeholder="••••••••"
              />
            </div>
          </div>

          <button
            type="submit"
            className="w-full py-3 px-4 bg-gradient-to-r from-[#38b2ac] to-[#3182ce] text-white font-medium rounded-md 
                       hover:opacity-90 transition-all duration-300 shadow-lg
                       focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-[#38b2ac] focus:ring-offset-[#1a1a1a]"
          >
            Sign In
          </button>

          {error && (
            <div className="text-center p-3 rounded-md bg-red-900/30 border border-red-800/50 mt-4">
              <p className="text-sm text-red-400 flex items-center justify-center">
                <svg className="h-4 w-4 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                {error}
              </p>
            </div>
          )}
        </form>

        <p className="mt-8 text-center text-sm text-gray-400">
          Don't have an account?{' '}
          <span
            onClick={() => navigate('/signup')}
            className="font-medium text-[#38b2ac] hover:text-[#319795] hover:underline cursor-pointer transition-colors"
          >
            Sign up
          </span>
        </p>
      </div>
    </div>
  );
}
