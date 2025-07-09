import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, ChevronDown, ChevronUp, Filter, User, LogOut } from "lucide-react";
import CpuCoolerItem from "../../components/lists/items/CpuCoolerItem";
import CpuCoolerSidebar from "../../components/lists/sidebars/CpuCoolerSidebar";

export default function CpuCooler() {
  const [cpuCoolers, setCpuCoolers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  const fromConfigurator = location.state?.fromConfigurator;
  const componentType = location.state?.componentType;

  // Fetch CPU Coolers
  const buildQueryParams = (filters) => {
    const params = new URLSearchParams();

    // Define default values to skip when they haven't been changed
    const sliderDefaults = {
      price: [0, 500],
      coolingCapacity: [50, 400],
      towerHeight: [30, 200],
      radiatorSize: [120, 420],
      ramClearance: [20, 80],
    };

    for (const key in filters) {
      const value = filters[key];
      if (Array.isArray(value) && value.length === 2) {
        // Only add slider params if they differ from defaults
        const defaults = sliderDefaults[key];
        const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
        
        if (!isDefault) {
          // Map frontend slider names to backend parameter names
          if (key === 'price') {
            params.set('minPrice', value[0]);
            params.set('maxPrice', value[1]);
          } else if (key === 'coolingCapacity') {
            params.set('coolingCapacity', value[0]);
          } else if (key === 'towerHeight') {
            params.set('towerHeight', value[0]);
          } else if (key === 'radiatorSize') {
            params.set('radiatorSize', value[0]);
          }
        }
      } else if (Array.isArray(value) && value.length > 0) {
        // Map frontend array names to backend parameter names
        if (key === 'brands' && value.length > 0) {
          // Backend expects single brandName parameter
          params.set('brandName', value[0]);
        } else if (key === 'coolerTypes' && value.length > 0) {
          // Backend expects single coolerType parameter
          params.set('coolerType', value[0]);
        }
      } else if (typeof value === "boolean" && value === true) {
        // Only add boolean params if they're true (not default false)
        params.set(key, "true");
      }
    }

    return params.toString();
  };

  useEffect(() => {
    const fetchCpuCoolers = async () => {
      try {
        setLoading(true);
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/api/components/cpu-coolers/filtered?${query}`
          : `/api/components/cpu-coolers`;

        console.log("Making API call to:", url);
        const res = await fetch(url);
        
        if (!res.ok) {
          throw new Error("Failed to fetch CPU coolers");
        }
        
        const data = await res.json();
        console.log("Received data:", data.length, "items");
        setCpuCoolers(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching CPU coolers:", error);
        setError(error.message);
        setLoading(false);
      }
    };

    fetchCpuCoolers();
  }, [filters]);

  // Apply filters from the sidebar
  const handleApplyFilters = (newFilters) => {
    console.log("Applying filters:", newFilters);
    setFilters(newFilters);
    // You would typically filter the CPU coolers here or make an API call with filters
  };

  const handleSelectCpuCooler = (cpuCooler) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: cpuCooler,
          componentType: componentType
        }
      });
    }
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      {/* Sidebar */}
      <CpuCoolerSidebar onApply={handleApplyFilters} />
      
      {/* Main content */}
      <div className="flex-1 flex flex-col">
        {/* Top header */}
        <header className="flex justify-between items-center bg-slate-800 border-b border-gray-700 px-8 h-16 shadow-md">
          <div>
            <button
              onClick={() => navigate(fromConfigurator ? "/configurator" : "/dashboard")}
              className="flex items-center text-blue-400 hover:text-blue-300"
            >
              <ArrowLeft size={18} className="mr-1" />
              {fromConfigurator ? "Configurator" : "Dashboard"}
            </button>
          </div>
          <div className="flex gap-6">
            <button
              onClick={() => navigate("/profile")}
              className="text-gray-300 hover:text-blue-400"
            >
              <User size={18} className="mr-2" />
              Profile
            </button>
            <button
              onClick={handleLogout}
              className="text-gray-300 hover:text-red-400"
            >
              <LogOut size={18} className="mr-2" />
              Logout
            </button>
          </div>
        </header>
        
        {/* Main content area */}
        <main className="flex-1 overflow-y-auto p-6">
          <div className="max-w-7xl mx-auto">
            <h1 className="text-3xl font-bold text-center mb-6 text-white">
              Available CPU Coolers
            </h1>
            
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-4 border-slate-700 border-t-blue-500"></div>
              </div>
            ) : error ? (
              <div className="bg-red-900/30 border border-red-800 text-red-200 p-4 rounded-md">
                <p>{error}</p>
              </div>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {cpuCoolers.map((cooler) => (
                  <CpuCoolerItem 
                    key={cooler.id} 
                    cpuCooler={cooler}
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectCpuCooler(cooler)}
                  />
                ))}
              </div>
            )}
          </div>
        </main>
      </div>
    </div>
  );
}