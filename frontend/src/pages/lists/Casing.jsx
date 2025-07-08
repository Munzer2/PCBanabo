import React, { useEffect, useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import CasingSidebar from "../../components/lists/sidebars/CasingSidebar";
import CasingItem from "../../components/lists/items/CasingItem";

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
    const fetchCasings = async () => {
      try {
        setLoading(true); 
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/api/components/casings/filtered?${query}`
          : `/api/components/casings`;

        console.log("Making API call to:", url);
        const res = await fetch(url);
        
        if (!res.ok) {
          const errorText = await res.text();
          console.error("API error:", res.status, errorText);
          throw new Error(`API error: ${res.status}`);
        }
        
        const data = await res.json();
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
            {loading ? (
              <p className="text-center text-gray-400">Loading casings...</p>
            ) : error ? (
              <p className="text-center text-red-400">{error}</p>
            ) : casings.length === 0 ? (
              <p className="text-center text-gray-400">No casings found.</p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {casings.map((casing) => (
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
