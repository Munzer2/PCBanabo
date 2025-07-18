// src/pages/Configurator.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import api from '../api';
import { Computer, ArrowLeft, User, LogOut, Save } from 'lucide-react';
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
  const [components, setComponents] = useState(() => {
    // Load components from localStorage on initialization
    const saved = localStorage.getItem('configurator-components');
    return saved ? JSON.parse(saved) : initialComponents;
  });
  const [user, setUser] = useState(null);
  const [hasChanges, setHasChanges] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();

  // Save components to localStorage whenever they change
  useEffect(() => {
    localStorage.setItem('configurator-components', JSON.stringify(components));
    console.log(components);
  }, [components]);

  // Debug localStorage on component mount
  useEffect(() => {
    
    // For development: if no userId exists, create a mock one
    if (!localStorage.getItem('userId')) {
      console.log('No userId found, setting mock userId for development');
      localStorage.setItem('userId', 'dev-user-123');
    }
  }, []);

  useEffect(() => {
    // Set a timeout to prevent infinite loading
    const timeout = setTimeout(() => {
      if (isLoading) {
        console.log('Loading timeout reached, setting fallback user');
        setUser({ id: localStorage.getItem('userId') || 'unknown', name: 'User' });
        setIsLoading(false);
      }
    }, 3000); // Reduced to 3 second timeout
    
    // Check if user is logged in
    (async () => {
      try {
        const userId = localStorage.getItem('userId');
        if (!userId) {
          console.log('No userId found, redirecting to login');
          clearTimeout(timeout);
          setIsLoading(false);
          return navigate('/login', { replace: true });
        }
        
        console.log('Fetching user data for userId:', userId);
        
        // Try to fetch user data, but don't fail if it doesn't work
        try {
          const res = await api.get(`/users/${userId}`);
          clearTimeout(timeout);
          setUser(res.data);
          setIsLoading(false);
        } catch (apiError) {
          console.log('API call failed, using fallback user data');
          clearTimeout(timeout);
          setUser({ id: userId, name: 'User', email: 'user@example.com' });
          setIsLoading(false);
        }
      } catch (error) {
        console.error('Error in user setup:', error);
        clearTimeout(timeout);
        setUser({ id: 'fallback-user', name: 'User' });
        setIsLoading(false);
      }
    })();
    
    return () => clearTimeout(timeout);
  }, [navigate, isLoading]);

  useEffect(() => {
    // Handle component selection from component pages
    if (location.state?.selectedComponent && location.state?.componentType) {
      const { selectedComponent, componentType } = location.state;
      setComponents(prev => ({
        ...prev,
        [componentType]: {
          id: selectedComponent.id || selectedComponent._id,
          name: `${selectedComponent.brand_name} - ${selectedComponent.model_name}`,
          price: selectedComponent.avg_price || selectedComponent.average_price || selectedComponent.avgPrice,
          ...selectedComponent
        }
      }));
      setHasChanges(true);
      // Clear the state to prevent re-selection on page refresh
      window.history.replaceState({}, document.title);
    }
  }, [location.state]);

  // Handle component selection
  const handleSelectComponent = (type) => {
    const componentRoutes = {
      casing: '/casing',
      cpu: '/cpu',
      cpuCooler: '/cpu-cooler',
      gpu: '/gpu',
      motherboard: '/motherboard',
      psu: '/psu',
      ram: '/ram',
      storage: '/ssd'
    };
    
    const route = '/components' + componentRoutes[type];
    if (route) {
      navigate(route, { 
        state: { 
          fromConfigurator: true,
          componentType: type,
          selectedComponents: components 
        } 
      });
    }
  };

  // Handle component removal
  const handleRemoveComponent = (type) => {
    setComponents(prev => ({
      ...prev,
      [type]: null
    }));
    setHasChanges(true);
  };

  // Handle saving the build
  const handleSaveBuild = async () => {
    try {
      const buildData = {
        userId: localStorage.getItem('userId'),
        components: Object.fromEntries(
          Object.entries(components)
            .filter(([, component]) => component !== null)
            .map(([type, component]) => [type, component.id])
        ),
        totalPrice: Object.values(components)
          .filter(component => component !== null)
          .reduce((sum, component) => sum + parseFloat(component.price || 0), 0)
      };
      
      // console.log('Saving build:', buildData);
      
      // Make API call to save the build
      const dto = {
        cpuId:          buildData.components.cpu, 
        motherboard:    buildData.components.motherboard,
        ramId:          buildData.components.ram, 
        ssdId:    buildData.components.ssd, 
        gpuId:    buildData.components.gpu, 
        psuId:    buildData.components.psu, 
        casingId:    buildData.components.casing, 
        cpucoolerId:    buildData.components.cpuCooler,
        buildName:      `My build (${new Date().toLocaleDateString()})`,
        isPublic:       true 
      };

      console.log(dto);

      const res = await fetch(
        `/api/shared-builds/${buildData.userId}`,
        {
          method: 'POST',
          headers: { 'Content-Type' : 'application/json' },
          body:   JSON.stringify(dto)
        }
      ); 

      if(!res.ok) throw new Error(`Error saving the build with status: ${res.status}`); 

      const saved = await res.json(); 
      console.log('Build has been saved on server: ', saved); 
      
      setHasChanges(false);
      alert('Build saved successfully!');
    } catch (error) {
      console.error('Error saving build:', error);
      alert('Failed to save build. Please try again.');
    }
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

  // Invalid user data or still loading
  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-screen bg-slate-900">
        <p className="text-gray-400 text-lg">Loading configurator…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col">
        {/* Top Navigation Bar */}
        <header className="flex justify-between items-center bg-slate-800 border-b border-gray-700 px-8 h-16 shadow-md">
          <div className="flex items-center gap-2">
            <button 
              onClick={() => handleNavigation('/dashboard')} 
              className="flex items-center text-blue-400 hover:text-blue-300 transition"
            >
              <ArrowLeft size={18} className="mr-1" />
              Dashboard
            </button>
          </div>
          <div className="flex items-center gap-6">
            <button 
              onClick={() => handleNavigation('/profile')} 
              className="flex items-center text-gray-300 hover:text-blue-400 transition"
            >
              <User size={18} className="mr-2" />
              Profile
            </button>
            <button 
              onClick={handleLogout} 
              className="flex items-center text-gray-300 hover:text-red-400 transition"
            >
              <LogOut size={18} className="mr-2" />
              Logout
            </button>
          </div>
        </header>

        {/* Configurator Content */}
        <main className="flex-1 overflow-y-auto p-6 bg-slate-900">
          <div className="max-w-5xl mx-auto">
            {/* Header */}
            <div className="mb-8 text-center">
              <h1 className="text-3xl font-bold text-white">Configurator</h1>
              <p className="text-gray-400 mt-2">Select compatible components for your custom build</p>
            </div>

            {/* Component Selection Grid */}
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-8">
              {Object.entries(components).map(([type, component]) => (
                <ComponentSlot 
                  key={type}
                  type={type}
                  component={component}
                  onClick={() => handleSelectComponent(type)}
                  onRemove={() => handleRemoveComponent(type)}
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