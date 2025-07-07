// src/pages/Dashboard.jsx
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ChevronDown, LogOut } from "lucide-react";
import api from "../api";
import ChatSidebar from "../components/ChatBot/ChatSidebar";

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isChatOpen, setIsChatOpen] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      const userId = localStorage.getItem("userId");
      if (!userId) return navigate("/login", { replace: true });
      const res = await api.get(`/api/users/${userId}`);
      setUser(res.data);
    })();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login", { replace: true });
  };

  if (!user) {
    return (
      <div className="flex items-center justify-center h-screen bg-gray-900">
        <p className="text-gray-400 text-lg">Loading your dashboard…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex bg-gray-900 text-gray-100 font-sans">
      {/* ======================= */}
      {/*       SIDEBAR         */}
      {/* ======================= */}
      <aside className="w-64 bg-gray-800 text-gray-200 flex-shrink-0">
        <div className="px-6 py-8 border-b border-gray-700">
          <h1 className="text-2xl font-extrabold tracking-wide text-white">
            PCBanabo
          </h1>
        </div>
        <nav className="mt-6">
          <ul>
            <li className="mb-1">
              <Link
                to="/builds/my"
                className="
                  flex items-center px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white 
                  transition-colors duration-200 rounded-r-full
                "
              >
                My Builds
              </Link>
            </li>
            <li className="mb-1">
              <Link
                to="/builds"
                className="
                  flex items-center px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white
                  transition-colors duration-200 rounded-r-full
                "
              >
                All Builds
              </Link>
            </li>

            {/* Dropdown: All Components */}
            <li className="mb-1">
              <button
                onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                className="
                  flex justify-between items-center w-full px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white 
                  transition-colors duration-200 rounded-r-full
                "
              >
                <span className="font-medium">All Components</span>
                <ChevronDown
                  className={`
                    ml-2 h-4 w-4 transform 
                    transition-transform duration-200 
                    ${isDropdownOpen ? "rotate-180" : "rotate-0"}
                  `}
                />
              </button>
              {isDropdownOpen && (
                <ul className="mt-1 bg-gray-900">
                  <li>
                    <Link
                      to="/components/casing"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white 
                        rounded-md transition-colors duration-200
                      "
                    >
                      Casings
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/cpu"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white
                        rounded-md transition-colors duration-200
                      "
                    >
                      CPUs
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/cpu-coolers"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white
                        rounded-md transition-colors duration-200
                      "
                    >
                      CPU Coolers
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/gpu"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white
                        rounded-md transition-colors duration-200
                      "
                    >
                      Graphics Cards (GPUs)
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/motherboard"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white 
                        rounded-md transition-colors duration-200
                      "
                    >
                      Motherboards
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/psu"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white 
                        rounded-md transition-colors duration-200
                      "
                    >
                      Power Supplies (PSUs)
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/ram"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white 
                        rounded-md transition-colors duration-200
                      "
                    >
                      Memories (RAMs)
                    </Link>
                  </li>
                  <li>
                    <Link
                      to="/components/ssd"
                      className="
                        block px-8 py-2 text-sm text-gray-300 
                        hover:bg-gray-700 hover:text-white 
                        rounded-md transition-colors duration-200
                      "
                    >
                      Storages (SSDs/HDDs)
                    </Link>
                  </li>
                </ul>
              )}
            </li>

            <li className="mb-1">
              <Link
                to="/configurator"
                className="
                  flex items-center px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white 
                  transition-colors duration-200 rounded-r-full
                "
              >
                Launch Configurator
              </Link>
            </li>
          </ul>
        </nav>
      </aside>

      {/* ======================= */}
      {/*     RIGHT PANEL       */}
      {/* ======================= */}
      <div className="flex-1 flex flex-col">
        {/* TOP BAR */}
        <header className="flex justify-between items-center bg-gray-800 border-b border-gray-700 px-8 h-16 shadow-sm">
          <div>
            <h2 className="text-xl font-semibold text-gray-100">Dashboard</h2>
          </div>
          <div className="flex items-center space-x-4">
            {/* Chat toggle button */}
            <button
              onClick={() => setIsChatOpen(!isChatOpen)}
              className="flex items-center space-x-1 text-gray-200 hover:text-purple-400 transition-colors duration-200"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="h-5 w-5"
              >
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
              </svg>
              <span className="font-medium">Chat</span>
            </button>

            {/* Logout button - moved inside the flex container */}
            <button
              onClick={handleLogout}
              className="flex items-center space-x-1 text-gray-200 hover:text-red-400 transition-colors duration-200"
            >
              <LogOut className="h-5 w-5" />
              <span className="font-medium">Logout</span>
            </button>
          </div>
        </header>

        {/* MAIN CONTENT */}
        <main className="flex-1 overflow-y-auto p-8 bg-gray-900">
          <div className="max-w-3xl mx-auto">
            <section className="bg-gray-800 rounded-2xl shadow-md p-8">
              <h3 className="text-3xl font-bold text-gray-100 mb-6">
                Welcome,&nbsp;
                <span className="text-purple-400">{user.userName}</span>
              </h3>

              <div className="grid grid-cols-1 gap-6 text-gray-300">
                <div className="flex items-center">
                  <dt className="w-32 font-medium">Email:</dt>
                  <dd className="break-all">{user.email}</dd>
                </div>
                <div className="flex items-center">
                  <dt className="w-32 font-medium">Joined On:</dt>
                  <dd>{new Date(user.createdAt).toLocaleDateString()}</dd>
                </div>
                {/* Add more fields if desired */}
              </div>
            </section>

            {/* Quick‐Link Cards */}
            {/* <section className="mt-8 grid grid-cols-1 md:grid-cols-2 gap-6">
              <Link
                to="/builds/my"
                className="
                  block bg-gray-800 rounded-2xl shadow-sm 
                  p-6 hover:bg-gray-700 focus:bg-gray-700 
                  transition-colors duration-200
                "
              >
                <h4 className="text-lg font-semibold text-gray-100 mb-2">
                  My Builds
                </h4>
                <p className="text-gray-400">
                  View and manage the PCs you’ve configured so far.
                </p>
              </Link>

              <Link
                to="/configurator"
                className="
                  block bg-gray-800 rounded-2xl shadow-sm 
                  p-6 hover:bg-gray-700 focus:bg-gray-700 
                  transition-colors duration-200
                "
              >
                <h4 className="text-lg font-semibold text-gray-100 mb-2">
                  Configurator
                </h4>
                <p className="text-gray-400">
                  Launch the PC configurator to build a custom rig.
                </p>
              </Link>
            </section> */}
          </div>
        </main>
        {/* Chat Sidebar */}
        {isChatOpen && (
          <div className="fixed right-0 top-0 bottom-0 z-40 h-full">
            <ChatSidebar
              isOpen={isChatOpen}
              onClose={() => setIsChatOpen(false)}
              userId={user.id}
            />
          </div>
        )}
      </div>
    </div>
  );
}
