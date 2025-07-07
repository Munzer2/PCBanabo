import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import PsuItem from "../../components/lists/items/PsuItem";
import PsuSidebar from "../../components/lists/sidebars/PsuSidebar";

export default function Psu() {
  const [psus, setPsus] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();

  // Fetch PSUs
  useEffect(() => {
    const fetchPsus = async () => {
      try {
        setLoading(true);
        const res = await fetch("/api/components/psus");
        
        if (!res.ok) {
          throw new Error("Failed to fetch PSUs");
        }
        
        const data = await res.json();
        setPsus(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching PSUs:", error);
        setError(error.message);
        setLoading(false);
      }
    };

    fetchPsus();
  }, []);

  // Apply filters from the sidebar
  const handleApplyFilters = (newFilters) => {
    console.log("Applying filters:", newFilters);
    setFilters(newFilters);
    // You would typically filter the PSUs here or make an API call with filters
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">
      {/* Sidebar */}
      <PsuSidebar onApply={handleApplyFilters} />
      
      {/* Main content */}
      <div className="flex-1 flex flex-col">
        {/* Top header */}
        <header className="flex justify-between items-center bg-slate-800 border-b border-gray-700 px-8 h-16 shadow-md">
          <div>
            <button
              onClick={() => navigate("/dashboard")}
              className="flex items-center text-blue-400 hover:text-blue-300"
            >
              <ArrowLeft size={18} className="mr-1" />
              Dashboard
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
              Available Power Supplies
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
                {psus.map((psu) => (
                  <PsuItem 
                    key={psu.id} 
                    psu={psu}
                    showButton={true}
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