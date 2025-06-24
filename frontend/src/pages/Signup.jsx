// src/pages/Signup.jsx
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api';

export default function Signup() {
  const [userName, setUserName] = useState('');
  const [email,    setEmail]    = useState('');
  const [password, setPassword] = useState('');
  const [error,    setError]    = useState(null);
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    setError(null);
    try {
      const { data } = await api.post('/auth/register', {
        userName,
        email,
        password
      });
      // assume the API returns { token, user }
      localStorage.setItem('token', data.token);
      localStorage.setItem('userId', data.user.id);
      navigate('/dashboard', { replace: true });
    } catch (err) {
      setError(
        err.response?.data?.message ||
        'Registration failed. Please try again.'
      );
    }
  };  return (
    <div className="min-h-screen flex items-center justify-center bg-[#0a0e17]">
      <div className="bg-[#1a1a1a] p-8 rounded-lg border border-[#2c2c2c] shadow-2xl w-full max-w-md">
        <h1 className="text-2xl font-bold text-white mb-6 text-center">
          Create your account
        </h1>

        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="name" className="block text-sm font-medium text-gray-300 mb-2">
              Your name
            </label>
            <div className="relative rounded-md shadow-sm">
              <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 12a4 4 0 10-8 0 4 4 0 008 0zm0 0v1.5a2.5 2.5 0 005 0V12a9 9 0 10-9 9m4.5-1.206a8.959 8.959 0 01-4.5 1.207" />
                </svg>
              </div>              <input
                id="name"
                type="text"
                value={userName}
                onChange={e => setUserName(e.target.value)}
                required
                className="w-full pl-10 pr-3 py-3 bg-[#252525] border border-[#3a3a3a] rounded-md 
                         text-gray-200 placeholder-gray-500 focus:outline-none focus:ring-2
                         focus:ring-[#38b2ac] focus:border-transparent"
                placeholder="Jane Doe"
              />
            </div>
          </div>

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
            Sign Up
          </button>

          {error && (
            <p className="text-center text-sm text-red-600 mt-2">
              {error}
            </p>
          )}
        </form>

        <p className="mt-6 text-center text-sm text-gray-600">
          Already have an account?{' '}
          <button
            onClick={() => navigate('/login', { replace: true })}
            className="font-medium text-blue-600 hover:underline"
          >
            Log in
          </button>
        </p>
      </div>
    </div>
  );
}
