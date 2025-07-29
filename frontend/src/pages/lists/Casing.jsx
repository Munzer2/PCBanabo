import React, { useEffect, useState, useMemo } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import CasingSidebar from "../../components/lists/sidebars/CasingSidebar";
import CasingItem from "../../components/lists/items/CasingItem";
import SearchAndSort from "../../components/common/SearchAndSort";
import { sortComponents, filterComponentsBySearch } from "../../utils/componentUtils";
import api from "../../api";

const sliders = [
  "cpu",
  "gpu",
  "price",
  "psu"
];

export default function Casing() {
  const [casings, setCasings] = useState([]);
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
      price: [0, 4000],
      psu: [0, 300],
      cpu: [0, 200],
      gpu: [0, 400]
    };

    for (const key in filters) {
      const value = filters[key];
      if (sliders.includes(key)) {
        // Only add slider params if they differ from defaults
        const defaults = sliderDefaults[key];
        const isDefault = defaults && value[0] === defaults[0] && value[1] === defaults[1];
        
        if (!isDefault) {
          params.set(`${key}_gte`, value[0]);
          params.set(`${key}_lte`, value[1]);
        }
      } else if (Array.isArray(value) && value.length > 0) {
        if(key == "motherboard_support" && value.length > 0) {
          value.forEach(v => params.set("motherboard", v));
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
    const fetchCasings = async () => {
      try {
        setLoading(true); 
        // const query = buildQueryParams(filters);
        
        const effectiveFilters = {...filters}; 

        if(selectedComponents?.motherboard?.formFactor) {
          effectiveFilters.motherboard_support = [selectedComponents.motherboard.formFactor];
        }

        const query = buildQueryParams(effectiveFilters);
        
        const url = query.length > 0 
          ? `/api/components/casings/filtered?${query}`
          : `/api/components/casings`;


        console.log("Making API call to:", url);
        const res = await api.get(url);
        const data = res.data;
        console.log("Received data:", data.length, "items");
        setCasings(data);
        setLoading(false); // End loading state
      } catch (error) {
        console.error("Error fetching casings:", error);
        setError(error.message || "Failed to fetch casings");
        setLoading(false); // End loading even on error
      }
    };

    fetchCasings();
  }, [filters]);

  const handleApplyFilters = (newFilters) => {
    console.log("Applying new filters:", newFilters);
    setFilters(newFilters);
  };

  const handleSelectCasing = (casing) => {
    if (fromConfigurator) {
      navigate('/configurator', {
        state: {
          selectedComponent: casing,
          componentType: componentType
        }
      });
    }
  };

  // Filter and sort casings based on search term and sort option
  const filteredAndSortedCasings = useMemo(() => {
    const filtered = filterComponentsBySearch(casings, searchTerm);
    return sortComponents(filtered, sortBy);
  }, [casings, searchTerm, sortBy]);

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      <CasingSidebar onApply={handleApplyFilters} />
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
              Available Casings
            </h1>
            <SearchAndSort
              searchTerm={searchTerm}
              onSearchChange={setSearchTerm}
              sortBy={sortBy}
              onSortChange={setSortBy}
              placeholder="Search casings by name, brand, or series..."
            />
            {loading ? (
              <div className="flex justify-center items-center h-64">
                <div className="animate-spin rounded-full h-12 w-12 border-4 border-slate-700 border-t-blue-500"></div>
              </div>
            ) : error ? (
              <p className="text-center text-red-400">{error}</p>
            ) : filteredAndSortedCasings.length === 0 ? (
              <p className="text-center text-gray-400">
                {casings.length === 0 ? "No casings found." : "No casings match your search."}
              </p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {filteredAndSortedCasings.map((casing) => (
                  <CasingItem 
                    key={casing.id || casing._id} 
                    casing={casing} 
                    showButton={fromConfigurator}
                    onSelect={() => handleSelectCasing(casing)}
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
