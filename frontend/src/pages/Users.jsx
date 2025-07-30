import React, { useEffect, useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { ChevronDown, LogOut, RefreshCw, User, Mail, Calendar, Users as UsersIcon, X, Eye, Home, FolderOpen, Settings, Wrench } from "lucide-react";
import api from "../api";
import ChatSidebar from "../components/ChatBot/ChatSidebar";

const Users = () => {
    const [users, setUsers] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [currentUser, setCurrentUser] = useState(null);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [isChatOpen, setIsChatOpen] = useState(false);
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredUsers, setFilteredUsers] = useState([]);
    const [selectedUser, setSelectedUser] = useState(null);
    const [userBuilds, setUserBuilds] = useState([]);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [isLoadingBuilds, setIsLoadingBuilds] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        // Set a timeout to prevent infinite loading
        const timeout = setTimeout(() => {
            if (isLoading) {
                console.log('Loading timeout reached, setting fallback user');
                setCurrentUser({ id: localStorage.getItem('userId') || 'unknown', userName: 'User' });
                setIsLoading(false);
            }
        }, 3000);
        
        // Check if user is logged in
        (async () => {
            try {
                const userId = localStorage.getItem('userId');
                if (!userId) {
                    console.log('No userId found, redirecting to login');
                    clearTimeout(timeout);
                    setIsLoading(false);
                    return navigate('/login', { replace: true });
                }
                
                console.log('Fetching user data for userId:', userId);
                
                try {
                    const res = await api.get(`/api/users/${userId}`);
                    clearTimeout(timeout);
                    setCurrentUser(res.data);
                    setIsLoading(false);
                } catch (apiError) {
                    console.log('API call failed, using fallback user data');
                    clearTimeout(timeout);
                    setCurrentUser({ id: userId, userName: 'User', email: 'user@example.com' });
                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error in user setup:', error);
                clearTimeout(timeout);
                setCurrentUser({ id: 'fallback-user', userName: 'User' });
                setIsLoading(false);
            }
        })();
        
        return () => clearTimeout(timeout);
    }, [navigate]);

    const fetchUsers = async () => {
        try {
            setIsLoading(true);
            setError(null);
            const res = await api.get('/api/users');
            console.log('Fetched users:', res.data);
            setUsers(res.data || []);
            setFilteredUsers(res.data || []);
        } catch (error) {
            console.error('Error fetching users:', error);
            setError('Failed to load users. Please try again.');
            setUsers([]);
            setFilteredUsers([]);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        if (currentUser) {
            fetchUsers();
        }
    }, [currentUser]);

    useEffect(() => {
        if (searchTerm.trim() === '') {
            setFilteredUsers(users);
        } else {
            const filtered = users.filter(user =>
                user.userName?.toLowerCase().includes(searchTerm.toLowerCase()) ||
                user.email?.toLowerCase().includes(searchTerm.toLowerCase())
            );
            setFilteredUsers(filtered);
        }
    }, [searchTerm, users]);

    const handleLogout = () => {
        localStorage.clear();
        navigate('/login', { replace: true });
    };

    const handleRefresh = () => {
        fetchUsers();
    };

    const fetchUserBuilds = async (userId, userName) => {
        try {
            setIsLoadingBuilds(true);
            setSelectedUser({ id: userId, userName });
            setIsModalOpen(true);
            
            const response = await api.get(`/api/shared-builds/${userId}`);
            const builds = response.data;
            const buildsArray = Array.isArray(builds) ? builds : [];
                
                // Calculate total price for each build
                const buildsWithPrices = await Promise.all(
                    buildsArray.map(async (build) => {
                        let totalPrice = 0;
                        
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
                                    totalPrice += parseFloat(price);
                                } catch (error) {
                                    console.error(`Error fetching ${component.type} price:`, error);
                                }
                            }
                        }
                        
                        return {
                            ...build,
                            totalPrice: Math.round(totalPrice)
                        };
                    })
                );
                
                setUserBuilds(buildsWithPrices);
        } catch (error) {
            console.error('Error fetching user builds:', error);
            setUserBuilds([]);
        } finally {
            setIsLoadingBuilds(false);
        }
    };

    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedUser(null);
        setUserBuilds([]);
    };

    if (isLoading && !currentUser) {
        return (
            <div className="flex items-center justify-center h-screen bg-gray-900">
                <div className="text-center">
                    <RefreshCw className="mx-auto h-12 w-12 text-purple-500 animate-spin mb-4" />
                    <p className="text-gray-400 text-lg">Loading users...</p>
                </div>
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
                                    text-gray-200 hover:bg-gray-700 hover:text-white 
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
                                    text-purple-400 bg-gray-700
                                    transition-colors duration-200 rounded-r-full
                                "
                            >
                                <UsersIcon size={18} className="mr-3" />
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
                    <div className="flex items-center space-x-4">
                        <h2 className="text-lg sm:text-xl font-semibold text-gray-100 flex items-center">
                            <UsersIcon className="mr-2 h-6 w-6" />
                            Users
                        </h2>
                        <div className="text-sm text-gray-400">
                            ({filteredUsers.length} users)
                        </div>
                    </div>
                    <div className="flex items-center space-x-2 sm:space-x-4">
                        {/* Refresh button */}
                        <button
                            onClick={handleRefresh}
                            disabled={isLoading}
                            className="flex items-center space-x-1 text-gray-200 hover:text-purple-400 transition-colors duration-200 disabled:opacity-50"
                        >
                            <RefreshCw className={`h-5 w-5 ${isLoading ? 'animate-spin' : ''}`} />
                            <span className="font-medium hidden sm:inline">Refresh</span>
                        </button>

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

                        {/* Logout button */}
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
                    {/* Search Bar */}
                    <div className="mb-6">
                        <div className="relative max-w-md">
                            <input
                                type="text"
                                placeholder="Search users by name or email..."
                                value={searchTerm}
                                onChange={(e) => setSearchTerm(e.target.value)}
                                className="w-full px-4 py-2 pl-10 bg-gray-800 border border-gray-700 rounded-lg text-gray-100 placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent"
                            />
                            <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <svg
                                    className="h-5 w-5 text-gray-400"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth={2}
                                        d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                                    />
                                </svg>
                            </div>
                        </div>
                    </div>

                    {/* Error Message */}
                    {error && (
                        <div className="mb-6 p-4 bg-red-900 border border-red-700 rounded-lg">
                            <div className="flex items-center">
                                <svg
                                    className="h-5 w-5 text-red-400 mr-2"
                                    fill="none"
                                    stroke="currentColor"
                                    viewBox="0 0 24 24"
                                >
                                    <path
                                        strokeLinecap="round"
                                        strokeLinejoin="round"
                                        strokeWidth={2}
                                        d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
                                    />
                                </svg>
                                <p className="text-red-200">{error}</p>
                            </div>
                        </div>
                    )}

                    {/* Loading State */}
                    {isLoading && (
                        <div className="text-center py-12">
                            <RefreshCw className="mx-auto h-12 w-12 text-purple-500 animate-spin mb-4" />
                            <p className="text-gray-400 text-lg">Loading users...</p>
                        </div>
                    )}

                    {/* Users Grid */}
                    {!isLoading && !error && (
                        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
                            {filteredUsers.map((user) => (
                                <div
                                    key={user.id}
                                    className="bg-gray-800 rounded-xl p-6 border border-gray-700 hover:border-purple-500 transition-all duration-200 transform hover:scale-105"
                                >
                                    {/* User Avatar */}
                                    <div className="flex items-center justify-center mb-4">
                                        <div className="w-16 h-16 bg-gradient-to-br from-purple-500 to-purple-700 rounded-full flex items-center justify-center">
                                            <User className="h-8 w-8 text-white" />
                                        </div>
                                    </div>

                                    {/* User Info */}
                                    <div className="text-center space-y-2">
                                        <h3 className="text-lg font-semibold text-gray-100">
                                            {user.userName || 'Unknown User'}
                                        </h3>
                                        
                                        <div className="flex items-center justify-center text-sm text-gray-400">
                                            <Mail className="h-4 w-4 mr-1" />
                                            <span className="truncate">{user.email || 'No email'}</span>
                                        </div>

                                        {user.userType && (
                                            <div className="flex items-center justify-center">
                                                <span className="text-xs bg-purple-600 text-purple-100 px-2 py-1 rounded">
                                                    {user.userType}
                                                </span>
                                            </div>
                                        )}
                                    </div>

                                    {/* Actions */}
                                    <div className="mt-4 flex space-x-2">
                                        <button
                                            onClick={() => fetchUserBuilds(user.id, user.userName)}
                                            className="flex-1 px-3 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-lg text-sm transition-colors duration-200 flex items-center justify-center"
                                        >
                                            <Eye className="h-4 w-4 mr-1" />
                                            View Builds
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}

                    {/* Empty State */}
                    {!isLoading && !error && filteredUsers.length === 0 && (
                        <div className="text-center py-12">
                            <UsersIcon className="mx-auto h-16 w-16 text-gray-600 mb-4" />
                            <h3 className="text-xl font-semibold text-gray-400 mb-2">
                                {searchTerm ? 'No users found' : 'No users available'}
                            </h3>
                            <p className="text-gray-500">
                                {searchTerm 
                                    ? 'Try adjusting your search criteria' 
                                    : 'Users will appear here when they join the platform'
                                }
                            </p>
                            {searchTerm && (
                                <button
                                    onClick={() => setSearchTerm('')}
                                    className="mt-4 px-4 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded-lg transition-colors duration-200"
                                >
                                    Clear Search
                                </button>
                            )}
                        </div>
                    )}
                </main>

                {/* User Builds Modal */}
                {isModalOpen && (
                    <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
                        <div className="bg-gray-800 rounded-xl max-w-4xl w-full max-h-[80vh] overflow-hidden flex flex-col">
                            {/* Modal Header */}
                            <div className="flex items-center justify-between p-6 border-b border-gray-700">
                                <h2 className="text-xl font-semibold text-gray-100 flex items-center">
                                    <User className="mr-2 h-5 w-5" />
                                    {selectedUser?.userName}'s Builds
                                </h2>
                                <button
                                    onClick={closeModal}
                                    className="text-gray-400 hover:text-gray-200 transition-colors"
                                >
                                    <X className="h-6 w-6" />
                                </button>
                            </div>

                            {/* Modal Content */}
                            <div className="flex-1 overflow-y-auto p-6">
                                {isLoadingBuilds ? (
                                    <div className="text-center py-12">
                                        <RefreshCw className="mx-auto h-12 w-12 text-purple-500 animate-spin mb-4" />
                                        <p className="text-gray-400 text-lg">Loading builds...</p>
                                    </div>
                                ) : userBuilds.length > 0 ? (
                                    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                                        {userBuilds.map((build) => (
                                            <div key={build.id} className="bg-gray-700 rounded-lg p-4 border border-gray-600 hover:border-purple-500 transition-colors">
                                                <div className="flex justify-between items-start mb-3">
                                                    <h4 className="font-medium text-gray-100 truncate pr-2">
                                                        {build.buildName || 'Unnamed Build'}
                                                    </h4>
                                                    <span className={`text-xs px-2 py-1 rounded flex-shrink-0 ${
                                                        build.public || build.isPublic 
                                                            ? 'bg-green-600 text-green-100' 
                                                            : 'bg-gray-600 text-gray-300'
                                                    }`}>
                                                        {build.public || build.isPublic ? 'Public' : 'Private'}
                                                    </span>
                                                </div>
                                                
                                                <div className="space-y-2 text-sm text-gray-400">
                                                    <p>Created: {new Date(build.savedAt || Date.now()).toLocaleDateString()}</p>
                                                    <p className="text-green-400 font-semibold">
                                                        Total: ${build.totalPrice || 0}
                                                    </p>
                                                    <div className="text-xs space-y-1">
                                                        {build.cpuId && <p>• Has CPU</p>}
                                                        {build.gpuId && <p>• Has GPU</p>}
                                                        {build.motherboardId && <p>• Has Motherboard</p>}
                                                        {build.ramId && <p>• Has RAM</p>}
                                                        {build.ssdId && <p>• Has Storage</p>}
                                                        {build.psuId && <p>• Has PSU</p>}
                                                        {build.casingId && <p>• Has Case</p>}
                                                        {build.cpuCoolerId && <p>• Has CPU Cooler</p>}
                                                    </div>
                                                </div>

                                                <div className="mt-4 flex gap-2">
                                                    <Link
                                                        to="/builds"
                                                        onClick={closeModal}
                                                        className="flex-1 text-center px-3 py-2 bg-purple-600 hover:bg-purple-700 text-white rounded text-xs transition-colors"
                                                    >
                                                        View Details
                                                    </Link>
                                                    {build.public || build.isPublic ? (
                                                        <Link
                                                            to="/configurator"
                                                            state={{ loadBuild: build }}
                                                            onClick={closeModal}
                                                            className="flex-1 text-center px-3 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded text-xs transition-colors"
                                                        >
                                                            Clone Build
                                                        </Link>
                                                    ) : (
                                                        <span className="flex-1 text-center px-3 py-2 bg-gray-600 text-gray-400 rounded text-xs cursor-not-allowed">
                                                            Private
                                                        </span>
                                                    )}
                                                </div>
                                            </div>
                                        ))}
                                    </div>
                                ) : (
                                    <div className="text-center py-12">
                                        <div className="text-4xl mb-4">🖥️</div>
                                        <h4 className="text-lg font-medium text-gray-100 mb-2">No builds found</h4>
                                        <p className="text-gray-400">
                                            {selectedUser?.userName} hasn't created any builds yet.
                                        </p>
                                    </div>
                                )}
                            </div>

                            {/* Modal Footer */}
                            {userBuilds.length > 0 && (
                                <div className="border-t border-gray-700 p-4 text-center">
                                    <div className="flex justify-between items-center text-sm text-gray-400">
                                        <span>
                                            Showing {userBuilds.length} build{userBuilds.length !== 1 ? 's' : ''} by {selectedUser?.userName}
                                        </span>
                                        <span className="text-green-400 font-semibold">
                                            Total Portfolio Value: ${userBuilds.reduce((sum, build) => sum + (build.totalPrice || 0), 0).toLocaleString()}
                                        </span>
                                    </div>
                                </div>
                            )}
                        </div>
                    </div>
                )}

                {/* Chat Sidebar */}
                <ChatSidebar
                    isOpen={isChatOpen}
                    onClose={() => setIsChatOpen(false)}
                    currentPage="/users"
                    userContext={currentUser}
                />
            </div>
        </div>
    );
};

export default Users;
