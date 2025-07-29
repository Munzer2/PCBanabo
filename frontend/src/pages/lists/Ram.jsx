import React, { useEffect, useState, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import RamSidebar from "../../components/lists/sidebars/RamSidebar";
import RamItem from "../../components/lists/items/RamItem";
import SearchAndSort from "../../components/common/SearchAndSort";
import { sortComponents, filterComponentsBySearch } from "../../utils/componentUtils";
import api from "../../api";

const sliders = ["price", "speed"];

export default function Ram() {
  const [rams, setRams] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("name-asc");
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  // const fromConfigurator = location.state?.fromConfigurator;
  // const componentType = location.state?.componentType;

  const { fromConfigurator, selectedComponents, componentType } = location?.state || {}; 

  const buildQueryParams = (filters) => {
    const params = new URLSearchParams();

    // Define default values to skip when they haven't been changed
    const sliderDefaults = {
      price: [0, 500],
      speed: [2400, 6400],
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
          } else if (key === 'speed') {
            params.set('speedMin', value[0]);
          }
        }
      } else if (Array.isArray(value) && value.length > 0) {
        // Map frontend array names to backend parameter names
        if (key === 'brands' && value.length > 0) {
          // Backend expects single brandName parameter
          params.set('brandName', value[0]);
        } else if (key === 'memoryTypes' && value.length > 0) {
          // Backend expects single memType parameter
          params.set('memType', value[0]);
        } else if (key === 'capacities' && value.length > 0) {
          // Backend expects single memCapacity parameter
          params.set('memCapacity', value[0]);
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
    const fetchRams = async () => {
      try {
        setLoading(true);
        const effectiveFilters = {...filters};
        console.log(selectedComponents?.motherboard); 
        if(selectedComponents?.motherboard?.mem_type) {
          effectiveFilters.memoryTypes = [ selectedComponents.motherboard.mem_type ];
        }
        const query = buildQueryParams(effectiveFilters); 
        const url = query.length > 0 
          ? `/api/components/rams/filtered?${query}`
          : `/api/components/rams`;

        console.log("New API call:", url);
        const res = await api.get(url);
        const data = res.data;
        console.log("Data received from new query:", data);
        setRams(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching RAM modules:", error);
        setError(error.message);
        setLoading(false);
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

  // Filter and sort RAMs based on search term and sort option
  const filteredAndSortedRams = useMemo(() => {
    const filtered = filterComponentsBySearch(rams, searchTerm);
    return sortComponents(filtered, sortBy);
  }, [rams, searchTerm, sortBy]);

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
            <SearchAndSort
              searchTerm={searchTerm}
              onSearchChange={setSearchTerm}
              sortBy={sortBy}
              onSortChange={setSortBy}
              placeholder="Search RAM by name, brand, or series..."
            />
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-4 border-slate-700 border-t-blue-500"></div>
              </div>
            ) : error ? (
              <div className="text-center text-red-400 py-8">
                <p>Error loading RAM modules: {error}</p>
                <button 
                  onClick={() => window.location.reload()} 
                  className="mt-4 px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                >
                  Retry
                </button>
              </div>
            ) : filteredAndSortedRams.length === 0 ? (
              <p className="text-center text-gray-400">
                {rams.length === 0 ? "No RAM modules found." : "No RAM modules match your search."}
              </p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredAndSortedRams.map((ram) => (
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