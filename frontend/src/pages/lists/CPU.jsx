import React, { useEffect, useState, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import CPUItem from "../../components/lists/items/CPUItem";
import CpuSidebar from "../../components/lists/sidebars/CpuSidebar";
import SearchAndSort from "../../components/common/SearchAndSort";
import { sortComponents, filterComponentsBySearch } from "../../utils/componentUtils";
import api from "../../api";

const sliders = [
  "cores",
  "threads",
  "price",
  "baseClock",
  "boostClock",
  "tdp",
];

export default function CPU() {
  const [CPUs, setCPUs] = useState([]);
  const [filters, setFilters] = useState({});
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("name-asc");
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  const { fromConfigurator, selectedComponents, componentType} = location.state || {}; 

  const buildQueryParams = (filters) => {
    console.log("CPU buildQueryParams called with:", filters);
    const params = new URLSearchParams();

    // Define default values to skip when they haven't been changed
    const sliderDefaults = {
      price: [0, 1500],
      cores: [1, 64],
      threads: [2, 128],
      baseClock: [1, 5],
      boostClock: [1, 6],
      tdp: [10, 300],
    };

    for (const key in filters) {
      const value = filters[key];
      console.log(`CPU buildQueryParams processing key: ${key}, value:`, value);
      if (sliders.includes(key)) {
        // Only add slider params if they differ from defaults
        const defaults = sliderDefaults[key];
        const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
        console.log(`CPU buildQueryParams slider ${key}: isDefault=${isDefault}, defaults=`, defaults);
        
        if (!isDefault) {
          // Map frontend slider names to backend parameter names
          if (key === 'price') {
            params.set('minPrice', value[0]);
            params.set('maxPrice', value[1]);
            console.log("CPU buildQueryParams: Added price params");
          } else if (key === 'tdp') {
            params.set('tdpMin', value[0]);
            params.set('tdpMax', value[1]);
            console.log("CPU buildQueryParams: Added tdp params");
          } else if (key === 'cores') {
            params.set('coresMin', value[0]);
            params.set('coresMax', value[1]);
            console.log("CPU buildQueryParams: Added cores params");
          } else if (key === 'threads') {
            params.set('threadsMin', value[0]);
            params.set('threadsMax', value[1]);
            console.log("CPU buildQueryParams: Added threads params");
          } else if (key === 'baseClock') {
            params.set('baseClockMin', value[0]);
            params.set('baseClockMax', value[1]);
            console.log("CPU buildQueryParams: Added baseClock params");
          } else if (key === 'boostClock') {
            params.set('boostClockMin', value[0]);
            params.set('boostClockMax', value[1]);
            console.log("CPU buildQueryParams: Added boostClock params");
          }
        }
      } else if (Array.isArray(value) && value.length > 0) {
        // Map frontend array names to backend parameter names
        if (key === 'brands' && value.length > 0) {
          // Backend expects single brandName parameter
          params.set('brandName', value[0]);
          console.log("CPU buildQueryParams: Added brandName param");
        } else if (key === 'socket' && value.length > 0) {
          // Backend expects single socket parameter
          params.set('socket', value[0]);
          console.log("CPU buildQueryParams: Added socket param");
        }
      } else if (typeof value === "boolean" && value === true) {
        // Only add boolean params if they're true (not default false)
        params.set(key, "true");
        console.log(`CPU buildQueryParams: Added boolean param ${key}=true`);
      }
    }

    const result = params.toString();
    console.log("CPU buildQueryParams final result:", result);
    return result;
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  useEffect(() => {
    const fetchCPUs = async () => {
      try {
        setLoading(true);
        // const query = buildQueryParams(filters);
        // const url = Object.keys(filters).length
        //   ? `/api/components/cpus/filtered?${query}`
        //   : `/api/components/cpus`;

        const effectiveFilters = {...filters};
        if(selectedComponents?.motherboard?.socket) {
          effectiveFilters.socket =[ selectedComponents.motherboard.socket ] ;
        }
        
        const query = buildQueryParams(effectiveFilters); 

        const url = query.length > 0 
          ? `/api/components/cpus/filtered?${query}` : `/api/components/cpus`; 

        console.log("CPU Page: Final API call URL:", url);
        const res = await api.get(url);
        const data = res.data;
        console.log("CPU Page: Data received:", data.length, "items");
        setCPUs(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching CPUs:", error);
        setLoading(false);
      }
    };

    fetchCPUs();
  }, [filters, selectedComponents]);

  const handleApplyFilters = (newFilters) => {
    console.log("Received some new filters:", newFilters);
    setFilters(newFilters);
    console.log("Now my filters are:", filters);
  };

  const handleSelectCPU = (cpu) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: cpu,
          componentType: componentType
        }
      });
    }
  };

  // Filter and sort CPUs based on search term and sort option
  const filteredAndSortedCPUs = useMemo(() => {
    const filtered = filterComponentsBySearch(CPUs, searchTerm);
    return sortComponents(filtered, sortBy);
  }, [CPUs, searchTerm, sortBy]);

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      <CpuSidebar onApply={handleApplyFilters} />
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
              Available CPUs
            </h1>
            <SearchAndSort
              searchTerm={searchTerm}
              onSearchChange={setSearchTerm}
              sortBy={sortBy}
              onSortChange={setSortBy}
              placeholder="Search CPUs by name, brand, or series..."
            />
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-4 border-slate-700 border-t-blue-500"></div>
              </div>
            ) : filteredAndSortedCPUs.length === 0 ? (
              <p className="text-center text-gray-400">
                {CPUs.length === 0 ? "No CPUs found." : "No CPUs match your search."}
              </p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredAndSortedCPUs.map((cpu) => (
                  <CPUItem 
                    key={cpu.id || cpu._id} 
                    cpu={cpu} 
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectCPU(cpu)}
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
