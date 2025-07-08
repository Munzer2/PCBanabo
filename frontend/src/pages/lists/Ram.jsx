import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import RamSidebar from "../../components/lists/sidebars/RamSidebar";
import RamItem from "../../components/lists/items/RamItem";

const sliders = ["price", "speed"];

export default function Ram() {
  const [rams, setRams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  const fromConfigurator = location.state?.fromConfigurator;
  const componentType = location.state?.componentType;

  const buildQueryParams = (filters) => {
    const params = new URLSearchParams();

    for (const key in filters) {
      const value = filters[key];
      if (sliders.includes(key)) {
        params.set(`${key}_gte`, value[0]);
        params.set(`${key}_lte`, value[1]);
      } else if (Array.isArray(value)) {
        value.forEach((v) => params.append(key, v));
      } else if (typeof value === "boolean") {
        params.set(key, value ? "true" : "false");
      }
    }

    return params.toString();
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  useEffect(() => {
    const fetchRams = async () => {
      try {
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/api/components/rams/filtered?${query}`
          : `/api/components/rams`;

        const res = await fetch(url);
        const data = await res.json();
        console.log("Data received from new query:", data);
        setRams(data);
      } catch (error) {
        console.error("Error fetching RAM modules:", error);
      }
    };

    fetchRams();
  }, [filters]);

  const handleApplyFilters = (newFilters) => {
    console.log("Applying RAM filters:", newFilters);
    
    // Transform filters for API
    const apiFilters = { ...newFilters };
    
    // Transform memory types filter
    if (newFilters.memoryTypes?.length > 0) {
      apiFilters.memType = newFilters.memoryTypes;
      delete apiFilters.memoryTypes;
    }
    
    setFilters(apiFilters);
  };

  const handleSelectRam = (ram) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: ram,
          componentType: componentType
        }
      });
    }
  };

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      <RamSidebar onApply={handleApplyFilters} />
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
              Available RAM Modules
            </h1>
            {rams.length === 0 ? (
              <p className="text-center text-gray-400">No RAM modules found.</p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {rams.map((ram) => (
                  <RamItem 
                    key={ram.id || ram._id} 
                    ram={ram} 
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectRam(ram)}
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