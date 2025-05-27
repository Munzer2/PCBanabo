// src/pages/Dashboard.jsx
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const userId = localStorage.getItem('userId');
      if (!userId) return navigate('/login', { replace: true });
      const res = await api.get(`/users/${userId}`);
      setUser(res.data);
    })();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.clear();
    navigate('/login', { replace: true });
  };

  if (!user) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-50">
        <p className="text-gray-500 text-lg">Loading your dashboard…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex bg-gray-50 text-gray-800">
      {/* SIDEBAR */}
      <aside className="w-64 bg-white border-r border-gray-200">
        <div className="px-6 py-8">
          <h1 className="text-2xl font-semibold text-blue-700">PCbanabo</h1>
        </div>
        <nav className="mt-6">
          <ul>
            {[
              { to: '/builds/my', label: 'My Builds', color: 'blue' },
              { to: '/builds',   label: 'All Builds', color: 'green' },
              { to: '/components', label: 'All Components', color: 'purple' },
            ].map(({ to, label, color }) => (
              <li key={to} className="mb-1">
                <Link
                  to={to}
                  className={`
                    block px-6 py-3 rounded-r-full
                    text-${color}-700 hover:bg-${color}-50
                    transition
                  `}
                >
                  {label}
                </Link>
              </li>
            ))}
          </ul>
        </nav>
      </aside>

      {/* RIGHT PANEL */}
      <div className="flex-1 flex flex-col">
        {/* TOP BAR */}
        <header className="flex justify-end items-center bg-white border-b border-gray-200 px-8 h-16">
          <button
            onClick={handleLogout}
            className="text-gray-600 hover:text-red-600 font-medium"
          >
            Logout
          </button>
        </header>

        {/* MAIN CONTENT */}
        <main className="flex-1 overflow-y-auto p-8">
          <div className="max-w-3xl mx-auto">
            <section className="bg-white shadow-md rounded-xl p-8">
              <h2 className="text-3xl font-bold text-gray-900 mb-4">
                Welcome, {user.userName}
              </h2>
              <dl className="grid grid-cols-1 gap-4 text-gray-700">
                <div className="flex">
                  <dt className="w-32 font-medium">Email:</dt>
                  <dd>{user.email}</dd>
                </div>
              </dl>
            </section>
          </div>
        </main>
      </div>
    </div>
  );
}
