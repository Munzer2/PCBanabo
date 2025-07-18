import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import GpuItem from "../../components/lists/items/GpuItem";
import GpuSidebar from "../../components/lists/sidebars/GpuSidebar";

const sliders = [
  "price",
  "vram",
  "tdp",
  "cardLength",
  "cardHeight",
  "cardThickness",
];

export default function GPU() {
  const [gpus, setGpus] = useState([]);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  const fromConfigurator = location.state?.fromConfigurator;
  const componentType = location.state?.componentType;

  const buildQueryParams = (filters) => {
    const params = new URLSearchParams();

    // Define default values to skip when they haven't been changed
    const sliderDefaults = {
      price: [0, 2500],
      vram: [2, 24],
      tdp: [50, 500],
      cardLength: [100, 400],
      cardHeight: [20, 200],
      cardThickness: [20, 100],
    };

    for (const key in filters) {
      const value = filters[key];
      if (sliders.includes(key)) {
        // Only add slider params if they differ from defaults
        const defaults = sliderDefaults[key];
        const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
        
        if (!isDefault) {
          // Map frontend slider names to backend parameter names
          if (key === 'price') {
            params.set('minPrice', value[0]);
            params.set('maxPrice', value[1]);
          } else if (key === 'vram') {
            params.set('vramMin', value[0]);
          } else if (key === 'tdp') {
            params.set('tdpMax', value[1]);
          } else if (key === 'cardLength') {
            params.set('cardLengthMax', value[1]);
          }
        }
      } else if (Array.isArray(value) && value.length > 0) {
        // Map frontend array names to backend parameter names
        if (key === 'brands' && value.length > 0) {
          // Backend expects single brandName parameter
          params.set('brandName', value[0]);
        } else if (key === 'gpuCore' && value.length > 0) {
          // Backend expects single gpuCore parameter
          params.set('gpuCore', value[0]);
        }
      } else if (typeof value === "boolean" && value === true) {
        // Only add boolean params if they're true (not default false)
        params.set(key, "true");
      }
    }

    return params.toString();
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  useEffect(() => {
    const fetchGPUs = async () => {
      try {
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/api/components/gpus/filtered?${query}`
          : `/api/components/gpus`;

        console.log("New API call:", url);
        const res = await fetch(url);
        const data = await res.json();
        console.log("Data received from new query:", data);
        setGpus(data);
      } catch (error) {
        console.error("Error fetching GPUs:", error);
      }
    };

    fetchGPUs();
  }, [filters]);

  const handleApplyFilters = (newFilters) => {
    console.log("Received some new filters:", newFilters);
    setFilters(newFilters);
    console.log("Now my filters are:", filters);
  };

  const handleSelectGPU = (gpu) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: gpu,
          componentType: componentType
        }
      });
    }
  };

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      <GpuSidebar onApply={handleApplyFilters} />
      <div className="flex-1 flex flex-col">
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
        <main className="flex-1 overflow-y-auto p-6 bg-slate-900">
          <div className="max-w-5xl mx-auto">
            <h1 className="text-3xl font-bold text-center mb-6 text-white">
              Available GPUs
            </h1>
            {gpus.length === 0 ? (
              <p className="text-center text-gray-400">No GPUs found.</p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {gpus.map((gpu) => (
                  <GpuItem 
                    key={gpu.id || gpu._id} 
                    gpu={gpu} 
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectGPU(gpu)}
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