import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import SsdItem from "../../components/lists/items/SsdItem";
import SsdSidebar from "../../components/lists/sidebars/SsdSidebar";

export default function Ssd() {
  const [ssds, setSsds] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();

  // Fetch SSDs
  useEffect(() => {
    const fetchSsds = async () => {
      try {
        setLoading(true);
        const res = await fetch("/api/components/ssds");
        
        if (!res.ok) {
          throw new Error("Failed to fetch storage devices");
        }
        
        const data = await res.json();
        setSsds(data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching storage devices:", error);
        setError(error.message);
        setLoading(false);
      }
    };

    fetchSsds();
  }, []);

  // Apply filters from the sidebar
  const handleApplyFilters = (newFilters) => {
    console.log("Applying filters:", newFilters);
    setFilters(newFilters);
    // You would typically filter the SSDs here or make an API call with filters
  };

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

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
              Available Storage Devices
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
                {ssds.map((ssd) => (
                  <SsdItem 
                    key={ssd.id} 
                    ssd={ssd}
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