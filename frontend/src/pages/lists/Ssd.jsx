import React, { useState, useEffect, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import SsdItem from "../../components/lists/items/SsdItem";
import SsdSidebar from "../../components/lists/sidebars/SsdSidebar";
import SearchAndSort from "../../components/common/SearchAndSort";
import { sortComponents, filterComponentsBySearch } from "../../utils/componentUtils";
import api from "../../api";

const sliders = [
  "price",
  "seqRead",
  "seqWrite",
];

export default function Ssd() {
  const [ssds, setSsds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("name-asc");
  const navigate = useNavigate();
  const location = useLocation();
  
  // Check if we're coming from configurator
  const fromConfigurator = location.state?.fromConfigurator;
  const componentType = location.state?.componentType;

  const buildQueryParams = (filters) => {
    const params = new URLSearchParams();

    // Define default values to skip when they haven't been changed
    const sliderDefaults = {
      price: [0, 500],
      seqRead: [500, 7000],
      seqWrite: [400, 6000],
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
          } else if (key === 'seqRead') {
            params.set('seqReadMin', value[0]);
          } else if (key === 'seqWrite') {
            params.set('seqWriteMin', value[0]);
          }
        }
      } else if (Array.isArray(value) && value.length > 0) {
        // Map frontend array names to backend parameter names
        if (key === 'brands' && value.length > 0) {
          // Backend expects single brandName parameter
          params.set('brandName', value[0]);
        } else if (key === 'capacities' && value.length > 0) {
          // Backend expects single capacity parameter
          params.set('capacity', value[0]);
        } else if (key === 'formFactors' && value.length > 0) {
          // Backend expects single formFactor parameter
          params.set('formFactor', value[0]);
        } else if (key === 'pcieGens' && value.length > 0) {
          // Backend expects single pcieGen parameter
          params.set('pcieGen', value[0]);
        }
      } else if (typeof value === "boolean" && value === true) {
        // Only add boolean params if they're true (not default false)
        if (key === 'dramCache') {
          params.set('dramCache', "true");
        } else {
          params.set(key, "true");
        }
      }
    }

    return params.toString();
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  // Fetch SSDs
  useEffect(() => {
    const fetchSsds = async () => {
      try {
        setLoading(true);
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/api/components/ssds/filtered?${query}`
          : `/api/components/ssds`;

        console.log("Making API call to:", url);
        const res = await api.get(url);
        const data = res.data;
        console.log("Received data:", data.length, "items");
        setSsds(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching storage devices:", error);
        setError(error.message);
        setLoading(false);
      }
    };

    fetchSsds();
  }, [filters]);

  // Apply filters from the sidebar
  const handleApplyFilters = (newFilters) => {
    console.log("Applying filters:", newFilters);
    setFilters(newFilters);
  };

  const handleSelectSsd = (ssd) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: ssd,
          componentType: componentType
        }
      });
    }
  };

  // Filter and sort SSDs based on search term and sort option
  const filteredAndSortedSsds = useMemo(() => {
    const filtered = filterComponentsBySearch(ssds, searchTerm);
    return sortComponents(filtered, sortBy);
  }, [ssds, searchTerm, sortBy]);

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      {/* Sidebar */}
      <SsdSidebar onApply={handleApplyFilters} />
      
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
              Available Storage Devices
            </h1>
            <SearchAndSort
              searchTerm={searchTerm}
              onSearchChange={setSearchTerm}
              sortBy={sortBy}
              onSortChange={setSortBy}
              placeholder="Search SSDs by name, brand, or series..."
            />
            
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-4 border-slate-700 border-t-blue-500"></div>
              </div>
            ) : error ? (
              <div className="bg-red-900/30 border border-red-800 text-red-200 p-4 rounded-md">
                <p>{error}</p>
              </div>
            ) : filteredAndSortedSsds.length === 0 ? (
              <p className="text-center text-gray-400">
                {ssds.length === 0 ? "No storage devices found." : "No storage devices match your search."}
              </p>
            ) : (
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredAndSortedSsds.map((ssd) => (
                  <SsdItem 
                    key={ssd.id} 
                    ssd={ssd}
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectSsd(ssd)}
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