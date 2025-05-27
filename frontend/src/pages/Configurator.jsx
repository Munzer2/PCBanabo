// src/pages/Configurator.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import api from '../api';
import { Computer, ArrowLeft, User, LogOut, Save } from 'lucide-react';
import FilterSidebar from '../components/configurator/FilterSidebar';
import ComponentSlot from '../components/configurator/ComponentSlot';

const initialComponents = {
  casing: null,
  cpu: null,
  cpuCooler: null,
  gpu: null,
  motherboard: null,
  psu: null,
  ram: null,
  storage: null
};

export default function Configurator() {
  const [components, setComponents] = useState(initialComponents);
  const [user, setUser] = useState(null);
  const [hasChanges, setHasChanges] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    // Check if user is logged in
    (async () => {
      const userId = localStorage.getItem('userId');
      if (!userId) return navigate('/login', { replace: true });
      const res = await api.get(`/users/${userId}`);
      setUser(res.data);
    })();
  }, [navigate]);

  // Handle component selection
  const handleSelectComponent = (type) => {
    // Logic for selecting a component
    // navigate(`/components/select/${type}`, { 
    //   state: { selectedComponents: components } 
    // });
  };

  // Handle saving the build
  const handleSaveBuild = () => {
    // API call to save the build would go here
    console.log('Saving build:', components);
    setHasChanges(false);
  };

  // Handle navigation with unsaved changes warning
  const handleNavigation = (path) => {
    if (hasChanges) {
      const confirm = window.confirm('You have unsaved changes. Are you sure you want to leave?');
      if (!confirm) return;
    }
    navigate(path);
  };

  // Handle logout with unsaved changes warning
  const handleLogout = () => {
    if (hasChanges) {
      const confirm = window.confirm('You have unsaved changes. Are you sure you want to logout?');
      if (!confirm) return;
    }
    localStorage.clear();
    navigate('/login', { replace: true });
  };

  // Invalid user data
  if (!user) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-50">
        <p className="text-gray-500 text-lg">Loading configurator…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex bg-gray-50 text-gray-800">

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col">
        {/* Top Navigation Bar */}
        <header className="flex justify-between items-center bg-white border-b border-gray-200 px-8 h-16 shadow-sm">
          <div className="flex items-center gap-2">
            <button 
              onClick={() => handleNavigation('/dashboard')} 
              className="flex items-center text-blue-600 hover:text-blue-800 transition"
            >
              <ArrowLeft size={18} className="mr-1" />
              Dashboard
            </button>
          </div>
          <div className="flex items-center gap-6">
            <button 
              onClick={() => handleNavigation('/profile')} 
              className="flex items-center text-gray-600 hover:text-blue-600 transition"
            >
              <User size={18} className="mr-2" />
              Profile
            </button>
            <button 
              onClick={handleLogout} 
              className="flex items-center text-gray-600 hover:text-red-600 transition"
            >
              <LogOut size={18} className="mr-2" />
              Logout
            </button>
          </div>
        </header>

        {/* Configurator Content */}
        <main className="flex-1 overflow-y-auto p-6">
          <div className="max-w-5xl mx-auto">
            {/* Header */}
            <div className="mb-8 text-center">
              <h1 className="text-3xl font-bold text-gray-900">Configurator</h1>
              <p className="text-gray-600 mt-2">Select compatible components for your custom build</p>
            </div>

            {/* Component Selection Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
              {Object.entries(components).map(([type, component]) => (
                <ComponentSlot 
                  key={type}
                  type={type}
                  component={component}
                  onClick={() => handleSelectComponent(type)}
                />
              ))}
            </div>

            {/* Build Actions */}
            <div className="flex justify-center mt-8">
              <button
                onClick={handleSaveBuild}
                className="flex items-center bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-lg shadow-md transition transform hover:scale-105"
              >
                <Save size={20} className="mr-2" />
                Save Build
              </button>
            </div>
          </div>
        </main>
      </div>
    </div>
  );
}