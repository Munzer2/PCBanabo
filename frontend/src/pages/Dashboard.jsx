// src/pages/Dashboard.jsx
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { ChevronDown, LogOut, Home, FolderOpen, Users, Settings, Wrench } from "lucide-react";
import api from "../api";
import ChatSidebar from "../components/ChatBot/ChatSidebar";

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const [isChatOpen, setIsChatOpen] = useState(false);
  const [userStats, setUserStats] = useState({
    totalBuilds: 0,
    totalValue: 0,
    publicBuilds: 0
  });
  const [recentBuilds, setRecentBuilds] = useState([]);
  const [trendingBuilds, setTrendingBuilds] = useState([]);
  const [userActivity, setUserActivity] = useState([]);
  const navigate = useNavigate();

  const fetchUserStats = async (userId) => {
    try {
      
      // Use the correct API endpoint from the backend
      const buildsRes = await api.get(`/api/shared-builds/${userId}`);
      const trendsRes = await api.get(`/api/shared-builds`);
      
      const builds = buildsRes.data;
      
      // Ensure builds is an array
      const buildsArray = Array.isArray(builds) ? builds : [];
      setRecentBuilds(buildsArray);
      
      // Calculate total value by fetching component prices
      let totalValue = 0;
      
      for (const build of buildsArray) {
        let buildPrice = 0;
        
        // Fetch prices for each component type
        const componentTypes = [
          { type: 'cpu', id: build.cpuId },
          { type: 'gpu', id: build.gpuId },
          { type: 'motherboard', id: build.motherboardId },
          { type: 'ram', id: build.ramId },
          { type: 'ssd', id: build.ssdId },
          { type: 'psu', id: build.psuId },
          { type: 'casing', id: build.casingId },
          { type: 'cpu-cooler', id: build.cpuCoolerId }
        ];
        
        for (const component of componentTypes) {
          if (component.id) {
            try {
              const componentRes = await api.get(`/api/components/${component.type}s/${component.id}`);
              const componentData = componentRes.data;
              // Different components use different field names:
              // CPU uses "average_price", others use "avg_price"
              const price = componentData.average_price || componentData.avg_price || componentData.avgPrice || componentData.price || 0;
              buildPrice += parseFloat(price);
            } catch (error) {
              console.error(`Error fetching ${component.type} price:`, error);
            }
          }
        }
        
        totalValue += buildPrice;
      }
      
      
      setUserStats({
        totalBuilds: buildsArray.length,
        totalValue: Math.round(totalValue),
        publicBuilds: buildsArray.filter(b => b.public === true || b.isPublic === true).length
      });
        
        // Generate realistic activity data based on actual builds
        const activityData = [];
        if (buildsArray.length > 0) {
          const latestBuild = buildsArray[0];
          activityData.push({ 
            action: `Created build '${latestBuild.buildName || 'Unnamed Build'}'`, 
            timestamp: new Date(latestBuild.savedAt || Date.now()).toLocaleDateString(), 
            type: "build" 
          });
          
          if (buildsArray.length > 1) {
            activityData.push({ 
              action: `You have ${buildsArray.length} total builds`, 
              timestamp: "Recent", 
              type: "info" 
            });
          }
          
          const publicBuilds = buildsArray.filter(b => b.public === true || b.isPublic === true);
          if (publicBuilds.length > 0) {
            activityData.push({ 
              action: `${publicBuilds.length} builds shared publicly`, 
              timestamp: "Recent", 
              type: "share" 
            });
          }
        } else {
          activityData.push({ 
            action: "Ready to build your first PC!", 
            timestamp: "Today", 
            type: "welcome" 
          });
        }
        
        setUserActivity(activityData);
      
      const allBuilds = trendsRes.data;
      const trendingBuildsArray = Array.isArray(allBuilds) ? allBuilds : [];
      setTrendingBuilds(trendingBuildsArray.slice(0, 5));
    } catch (error) {
      console.error('Error fetching user stats:', error);
      // Set fallback data
      setUserStats({
        totalBuilds: 0,
        totalValue: 0,
        publicBuilds: 0
      });
      setRecentBuilds([]);
      setUserActivity([{ 
        action: "Error loading data - please refresh", 
        timestamp: "Now", 
        type: "error" 
      }]);
    }
  };

  useEffect(() => {
    (async () => {
      const userId = localStorage.getItem("userId");
      
      if (!userId) return navigate("/login", { replace: true });
      
      try {
        const res = await api.get(`/api/users/${userId}`);
        console.log('User data loaded:', res.data);
        console.log('User createdAt field:', res.data.createdAt);
        setUser(res.data);
        
        // Fetch additional dashboard data
        await fetchUserStats(userId);
      } catch (error) {
        console.error('Error loading dashboard:', error);
        // Try to continue with basic user info even if API fails
        const fallbackUser = { 
          userName: 'User', 
          email: 'user@example.com', 
          createdAt: new Date().toISOString() 
        };
        console.log('Using fallback user:', fallbackUser);
        setUser(fallbackUser);
        await fetchUserStats(userId);
      }
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
      <aside className="hidden lg:block w-64 bg-gray-800 text-gray-200 flex-shrink-0">
        <div className="px-6 py-8 border-b border-gray-700">
          <h1 className="text-2xl font-extrabold tracking-wide text-white">
            PCBanabo
          </h1>
        </div>
        <nav className="mt-6">
          <ul>
            <li className="mb-1">
              <Link
                to="/dashboard"
                className="
                  flex items-center px-6 py-3 
                  text-white bg-gray-700
                  transition-colors duration-200 rounded-r-full
                "
              >
                <Home size={18} className="mr-3" />
                Dashboard
              </Link>
            </li>
            <li className="mb-1">
              <Link
                to="/builds/my"
                className="
                  flex items-center px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white 
                  transition-colors duration-200 rounded-r-full
                "
              >
                <FolderOpen size={18} className="mr-3" />
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
                <svg
                  className="mr-3 h-[18px] w-[18px]"
                  fill="none"
                  stroke="currentColor"
                  viewBox="0 0 24 24"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={2}
                    d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10"
                  />
                </svg>
                All Builds
              </Link>
            </li>
            <li className="mb-1">
              <Link
                to="/users"
                className="
                  flex items-center px-6 py-3 
                  text-gray-200 hover:bg-gray-700 hover:text-white
                  transition-colors duration-200 rounded-r-full
                "
              >
                <Users size={18} className="mr-3" />
                Users
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
                <div className="flex items-center">
                  <Settings size={18} className="mr-3" />
                  <span className="font-medium">All Components</span>
                </div>
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
                      to="/components/cpu-cooler"
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
                <Wrench size={18} className="mr-3" />
                Launch Configurator
              </Link>
            </li>
          </ul>
        </nav>
      </aside>

      {/* ======================= */}
      {/*     RIGHT PANEL       */}
      {/* ======================= */}
      <div
        className={`flex-1 flex flex-col transition-all duration-300 ${
          isChatOpen ? "lg:mr-80" : "mr-0"
        }`}
      >
        {/* TOP BAR */}
        <header className="flex justify-between items-center bg-gray-800 border-b border-gray-700 px-4 sm:px-8 h-16 shadow-sm">
          <div>
            <h2 className="text-lg sm:text-xl font-semibold text-gray-100">
              Dashboard
            </h2>
          </div>
          <div className="flex items-center space-x-2 sm:space-x-4">
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
              <span className="font-medium hidden sm:inline">Chat</span>
            </button>

            {/* Logout button - moved inside the flex container */}
            <button
              onClick={handleLogout}
              className="flex items-center space-x-1 text-gray-200 hover:text-red-400 transition-colors duration-200"
            >
              <LogOut className="h-5 w-5" />
              <span className="font-medium hidden sm:inline">Logout</span>
            </button>
          </div>
        </header>

        {/* MAIN CONTENT */}
        <main className="flex-1 overflow-y-auto p-4 sm:p-8 bg-gray-900">
          <div className="max-w-3xl mx-auto">
            <section className="bg-gray-800 rounded-2xl shadow-md p-4 sm:p-8">
              <h3 className="text-2xl sm:text-3xl font-bold text-gray-100 mb-4 sm:mb-6">
                Welcome,&nbsp;
                <span className="text-purple-400">{user.userName}</span>
              </h3>

              <div className="grid grid-cols-1 gap-6 text-gray-300">
                <div className="flex items-center">
                  <dt className="w-32 font-medium">Email:</dt>
                  <dd className="break-all">{user.email}</dd>
                </div>
                {/* Add more fields if desired */}
              </div>
            </section>

            <section className="mt-8 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              <div className="bg-gradient-to-br from-blue-600 to-blue-700 rounded-xl p-6 text-white">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-blue-100 text-sm">My Builds</p>
                    <p className="text-2xl font-bold">
                      {userStats.totalBuilds || 0}
                    </p>
                  </div>
                  <svg
                    className="h-8 w-8 text-blue-200"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M9 3v2m6-2v2M9 19v2m6-2v2M5 9H3m2 6H3m18-6h-2m2 6h-2M7 19h10a2 2 0 002-2V7a2 2 0 00-2-2H7a2 2 0 00-2 2v10a2 2 0 002 2zM9 9h6v6H9V9z"
                    />
                  </svg>
                </div>
              </div>

              <div className="bg-gradient-to-br from-green-600 to-green-700 rounded-xl p-6 text-white">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-green-100 text-sm">Total Value</p>
                    <p className="text-2xl font-bold">
                      ${userStats.totalValue || 0}
                    </p>
                  </div>
                  <span className="text-2xl">💰</span>
                </div>
              </div>

              <div className="bg-gradient-to-br from-purple-600 to-purple-700 rounded-xl p-6 text-white">
                <div className="flex items-center justify-between">
                  <div>
                    <p className="text-purple-100 text-sm">Public Builds</p>
                    <p className="text-2xl font-bold">
                      {userStats.publicBuilds || 0}
                    </p>
                  </div>
                  <svg
                    className="h-8 w-8 text-purple-200"
                    fill="none"
                    stroke="currentColor"
                    viewBox="0 0 24 24"
                  >
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                    />
                    <path
                      strokeLinecap="round"
                      strokeLinejoin="round"
                      strokeWidth={2}
                      d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"
                    />
                  </svg>
                </div>
              </div>
            </section>

            {/* Recent Builds Section */}
            <section className="mt-8">
              <div className="flex justify-between items-center mb-6">
                <h3 className="text-xl font-semibold text-gray-100">Recent Builds</h3>
                <Link 
                  to="/builds/my" 
                  className="text-purple-400 hover:text-purple-300 text-sm font-medium"
                >
                  View All →
                </Link>
              </div>
              
              {recentBuilds.length > 0 ? (
                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                  {recentBuilds.slice(0, 3).map((build) => (
                    <div key={build.id} className="bg-gray-800 rounded-xl p-4 hover:bg-gray-700 transition-colors border border-gray-700">
                      <div className="flex justify-between items-start mb-3">
                        <h4 className="font-medium text-gray-100">{build.buildName}</h4>
                        <span className={`text-xs px-2 py-1 rounded ${build.public ? 'bg-green-600 text-green-100' : 'bg-gray-600 text-gray-300'}`}>
                          {build.public ? 'Public' : 'Private'}
                        </span>
                      </div>
                      <p className="text-sm text-gray-400 mb-3">
                        Created: {new Date(build.savedAt).toLocaleDateString()}
                      </p>
                      <div className="flex gap-2">
                        <Link
                          to="/builds/my"
                          className="flex-1 text-center px-3 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-lg text-sm transition-colors"
                        >
                          View
                        </Link>
                        <Link
                          to="/configurator"
                          state={{ loadBuild: build }}
                          className="flex-1 text-center px-3 py-2 bg-gray-600 hover:bg-gray-500 text-gray-200 rounded-lg text-sm transition-colors"
                        >
                          Edit
                        </Link>
                      </div>
                    </div>
                  ))}
                </div>
              ) : (
                <div className="bg-gray-800 rounded-xl p-8 text-center border border-gray-700">
                  <div className="text-4xl mb-4">🖥️</div>
                  <h4 className="text-lg font-medium text-gray-100 mb-2">No builds yet</h4>
                  <p className="text-gray-400 mb-4">Start building your first PC configuration</p>
                  <Link
                    to="/configurator"
                    className="inline-block px-6 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-lg transition-colors"
                  >
                    Create First Build
                  </Link>
                </div>
              )}
            </section>

            {/* Activity Feed Section */}
            <section className="mt-8 grid grid-cols-1 lg:grid-cols-2 gap-8">
              {/* Your Activity */}
              <div className="bg-gray-800 rounded-xl p-6 border border-gray-700">
                <h3 className="text-lg font-semibold text-gray-100 mb-4 flex items-center">
                  <svg className="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                  Your Activity
                </h3>
                <div className="space-y-3">
                  {userActivity.map((activity, i) => (
                    <div key={i} className="flex items-center p-3 bg-gray-700 rounded-lg">
                      <div className="flex-1">
                        <p className="text-sm text-gray-200">{activity.action}</p>
                        <p className="text-xs text-gray-400">{activity.timestamp}</p>
                      </div>
                      {activity.type === 'build' && (
                        <span className="text-purple-400 text-xs">Build</span>
                      )}
                    </div>
                  ))}
                </div>
              </div>

              {/* Trending Builds */}
              <div className="bg-gray-800 rounded-xl p-6 border border-gray-700">
                <h3 className="text-lg font-semibold text-gray-100 mb-4 flex items-center">
                  <svg className="mr-2 h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                  </svg>
                  Trending Builds
                </h3>
                <div className="space-y-3">
                  {trendingBuilds.slice(0, 4).map((build, i) => (
                    <div key={i} className="flex items-center justify-between p-3 bg-gray-700 rounded-lg hover:bg-gray-600 transition-colors cursor-pointer">
                      <div>
                        <p className="text-sm font-medium text-gray-100">{build.buildName}</p>
                        <p className="text-xs text-gray-400">by {build.userName || 'Anonymous'}</p>
                      </div>
                      <div className="text-right">
                        <p className="text-sm font-bold text-green-400">${build.totalPrice || 'N/A'}</p>
                        <div className="flex items-center text-xs text-gray-400">
                          <span className="mr-1">👀</span>
                          {build.views || Math.floor(Math.random() * 100)}
                        </div>
                      </div>
                    </div>
                  ))}
                </div>
                <Link 
                  to="/builds" 
                  className="block text-center mt-4 text-purple-400 hover:text-purple-300 text-sm"
                >
                  View All Community Builds →
                </Link>
              </div>
            </section>

            {/* Enhanced Quick Actions Grid */}
            <section className="mt-8">
              <h3 className="text-xl font-semibold text-gray-100 mb-6">Quick Actions</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <Link
                  to="/configurator"
                  className="group bg-gradient-to-br from-purple-600 to-purple-700 rounded-xl p-6 text-white hover:from-purple-700 hover:to-purple-800 transition-all duration-200 transform hover:scale-105 border border-purple-500/20"
                >
                  <div className="flex items-center justify-between mb-4">
                    <div className="p-3 bg-white/10 rounded-lg">
                      <svg className="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6V4m0 2a2 2 0 100 4m0-4a2 2 0 110 4m-6 8a2 2 0 100-4m0 4a2 2 0 100 4m0-4v2m0-6V4m6 6v10m6-2a2 2 0 100-4m0 4a2 2 0 100 4m0-4v2m0-6V4" />
                      </svg>
                    </div>
                    <svg className="h-5 w-5 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 8l4 4m0 0l-4 4m4-4H3" />
                    </svg>
                  </div>
                  <h4 className="text-lg font-semibold mb-2">Build Configurator</h4>
                  <p className="text-purple-100 text-sm">Create and customize your perfect PC build</p>
                </Link>

                <Link
                  to="/components/cpu"
                  className="group bg-gradient-to-br from-blue-600 to-blue-700 rounded-xl p-6 text-white hover:from-blue-700 hover:to-blue-800 transition-all duration-200 transform hover:scale-105 border border-blue-500/20"
                >
                  <div className="flex items-center justify-between mb-4">
                    <div className="p-3 bg-white/10 rounded-lg">
                      <svg className="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 11H5m14 0a2 2 0 012 2v6a2 2 0 01-2 2H5a2 2 0 01-2-2v-6a2 2 0 012-2m14 0V9a2 2 0 00-2-2M5 11V9a2 2 0 012-2m0 0V5a2 2 0 012-2h6a2 2 0 012 2v2M7 7h10" />
                      </svg>
                    </div>
                    <svg className="h-5 w-5 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 8l4 4m0 0l-4 4m4-4H3" />
                    </svg>
                  </div>
                  <h4 className="text-lg font-semibold mb-2">Browse Components</h4>
                  <p className="text-blue-100 text-sm">Explore CPUs, GPUs, and more hardware</p>
                </Link>

                <Link
                  to="/builds"
                  className="group bg-gradient-to-br from-green-600 to-green-700 rounded-xl p-6 text-white hover:from-green-700 hover:to-green-800 transition-all duration-200 transform hover:scale-105 border border-green-500/20"
                >
                  <div className="flex items-center justify-between mb-4">
                    <div className="p-3 bg-white/10 rounded-lg">
                      <svg className="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                      </svg>
                    </div>
                    <svg className="h-5 w-5 group-hover:translate-x-1 transition-transform" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17 8l4 4m0 0l-4 4m4-4H3" />
                    </svg>
                  </div>
                  <h4 className="text-lg font-semibold mb-2">Community Builds</h4>
                  <p className="text-green-100 text-sm">Get inspired by other builders' creations</p>
                </Link>
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
        <ChatSidebar
          isOpen={isChatOpen}
          onClose={() => setIsChatOpen(false)}
          currentPage="/dashboard"
          userContext={user}
        />
      </div>
    </div>
  );
}
