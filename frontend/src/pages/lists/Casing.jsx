import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { ArrowLeft, User, LogOut } from "lucide-react";
import CasingSidebar from "../../components/lists/sidebars/CasingSidebar";
import CasingItem from "../../components/lists/items/CasingItem";

const sliders = [
  "cpu",
  "gpu",
  "price",
  "psu",
  "radBottom",
  "radSide",
  "radTop",
];

export default function Casing() {
  const [casings, setCasings] = useState([]);
  const [filters, setFilters] = useState({});
  const navigate = useNavigate();

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
        const query = buildQueryParams(filters);
        const url = Object.keys(filters).length
          ? `/components/casings/filtered?${query}`
          : `/components/casings`;

        
        console.log("New API call:", url);
        const res = await fetch(url);
        const data = await res.json();
        console.log("Data received from new query:", data);
        setCasings(data);
      } catch (error) {
        console.error("Error fetching casings:", error);
      }
    };

    fetchCasings();
  }, [filters]);

  const handleApplyFilters = (newFilters) => {
    console.log("Received some new filters:", newFilters);
    setFilters(newFilters);
    console.log("Now my filters are:", filters);
  };

  return (
    <div className="min-h-screen flex bg-gray-50 text-gray-800">
      <CasingSidebar onApply={handleApplyFilters} />
      <div className="flex-1 flex flex-col">
        <header className="flex justify-between items-center bg-white border-b px-8 h-16 shadow-sm">
          <div>
            <button
              onClick={() => navigate("/dashboard")}
              className="flex items-center text-blue-600 hover:text-blue-800"
            >
              <ArrowLeft size={18} className="mr-1" />
              Dashboard
            </button>
          </div>
          <div className="flex gap-6">
            <button
              onClick={() => navigate("/profile")}
              className="text-gray-600 hover:text-blue-600"
            >
              <User size={18} className="mr-2" />
              Profile
            </button>
            <button
              onClick={handleLogout}
              className="text-gray-600 hover:text-red-600"
            >
              <LogOut size={18} className="mr-2" />
              Logout
            </button>
          </div>
        </header>
        <main className="flex-1 overflow-y-auto p-6">
          <div className="max-w-5xl mx-auto">
            <h1 className="text-3xl font-bold text-center mb-2">
              Available Casings
            </h1>
            {casings.length === 0 ? (
              <p className="text-center text-gray-600">No casings found.</p>
            ) : (
              <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {casings.map((casing) => (
                  <CasingItem key={casing.id || casing._id} casing={casing} />
                ))}
              </div>
            )}
          </div>
        </main>
      </div>
    </div>
  );
}
