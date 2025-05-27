// src/pages/Dashboard.jsx
import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { ChevronDown } from 'lucide-react';
import api from '../api';

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
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
        <h1 className="text-2xl font-semibold text-blue-700">PCBanabo</h1>
      </div>
      <nav className="mt-6">
        <ul>
          <li className="mb-1">
            <Link to="/builds/my" className="block px-6 py-3 rounded-r-full text-blue-700 hover:bg-blue-50 transition">
              My Builds
            </Link>
          </li>
          <li className="mb-1">
            <Link to="/builds" className="block px-6 py-3 rounded-r-full text-green-700 hover:bg-green-50 transition">
              All Builds
            </Link>
          </li>

          {/* Dropdown starts here */}
          <li className="mb-1">
            <button
              onClick={() => setIsDropdownOpen(!isDropdownOpen)}
              className="flex justify-between items-center w-full px-6 py-3 text-purple-700 hover:bg-purple-50 rounded-r-full transition"
            >
              <span>All Components</span>
              <ChevronDown className={`ml-2 h-4 w-4 transition-transform ${isDropdownOpen ? 'rotate-180' : ''}`} />
            </button>
            {isDropdownOpen && (
              <ul className="ml-6 mt-1 space-y-1">
                <li>
                  <Link to="/components/casing" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Casings
                  </Link>
                </li>
                <li>
                  <Link to="/components/cpu" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    CPUs
                  </Link>
                </li>
                <li>
                  <Link to="/components/cpucooler" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    CPU Coolers
                  </Link>
                </li>
                <li>
                  <Link to="/components/gpu" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Graphics Cards (GPUs)
                  </Link>
                </li>
                <li>
                  <Link to="/components/motherboard" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Motherboards
                  </Link>
                </li>
                <li>
                  <Link to="/components/psu" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Power Supplies (PSUs)
                  </Link>
                </li>
                <li>
                  <Link to="/components/ram" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Memories (RAMs)
                  </Link>
                </li>
                <li>
                  <Link to="/components/ssd" className="block px-4 py-2 text-sm text-purple-700 hover:bg-purple-100 rounded-md">
                    Storages (SSDs/HDDs)
                  </Link>
                </li>
              </ul>
            )}
          </li>
          {/* Dropdown ends */}

          <li className="mb-1">
            <Link to="/configurator" className="block px-6 py-3 rounded-r-full text-black hover:bg-black/10 transition">
              Launch Configurator
            </Link>
          </li>
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
