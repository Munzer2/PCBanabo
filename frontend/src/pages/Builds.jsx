import React, { useEffect, useState } from 'react'
import { useNavigate, Link } from 'react-router-dom';
import { ChevronDown, LogOut, RefreshCw, X, Trash2 } from "lucide-react"; // Added X
import api from "../api";
import ChatSidebar from "../components/ChatBot/ChatSidebar";

const Builds = () => {
    const [builds, setBuilds] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const [user, setUser] = useState(null);
    const [isDropdownOpen, setIsDropdownOpen] = useState(false);
    const [isChatOpen, setIsChatOpen] = useState(false);
    const [selectedBuild, setSelectedBuild] = useState(null); 
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [buildDetails, setBuildDetails] = useState(null);
    const [isLoadingDetails, setIsLoadingDetails] = useState(false); 
    const navigate = useNavigate();


    const handleDeleteBuild = (buildId) => {
        setBuilds(prevBuilds => prevBuilds.filter(build => build.id !== buildId));
    };

    useEffect(() => {
        // Set a timeout to prevent infinite loading
        const timeout = setTimeout(() => {
            if (isLoading) {
                console.log('Loading timeout reached, setting fallback user');
                setUser({ id: localStorage.getItem('userId') || 'unknown', name: 'User' });
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
                    setUser(res.data);
                    setIsLoading(false);
                } catch (apiError) {
                    console.log('API call failed, using fallback user data');
                    clearTimeout(timeout);
                    setUser({ id: userId, name: 'User', email: 'user@example.com' });
                    setIsLoading(false);
                }
            } catch (error) {
                console.error('Error in user setup:', error);
                clearTimeout(timeout);
                setUser({ id: 'fallback-user', name: 'User' });
                setIsLoading(false);
            }
        })();
        
        return () => clearTimeout(timeout);
    }, [navigate]);

    const fetchComponentDetails = async (componentType, componentId) => {
        if (!componentId) return null;
        
        try {
            // console.log("Calling : " + `/api/components/${componentType}s/${componentId}`);
            const res = await api.get(`/api/components/${componentType}s/${componentId}`);
            console.log(res.data); 
            return res.data;
        } catch (error) {
            console.error(`Error fetching ${componentType} details:`, error);
            return null;
        }
    };

    const fetchBuildDetails = async (build) => {
    setIsLoadingDetails(true);
    
    try {
        const detailPromises = [];
        const componentTypes = ['cpu', 'gpu', 'motherboard', 'ram', 'ssd', 'psu', 'cpuCooler', 'casing'];
        
        componentTypes.forEach(type => {
            if (build[type + 'Id']) {
                detailPromises.push(
                    fetchComponentDetails(type === 'cpuCooler' ? 'cpu-cooler' : type, build[type + 'Id'])
                        .then(details => ({ type, details }))
                );
            }
        });

        const results = await Promise.all(detailPromises);
        // console.log(results);
        
        // This is where the issue is - you need to properly attach the details
        const buildWithDetails = { ...build };
        results.forEach(({ type, details }) => {
            if (details) {
                buildWithDetails[type] = details; // This should work
            }
        });

        // console.log('Build with details:', buildWithDetails); // Add this to debug
        setBuildDetails(buildWithDetails);
    } catch (error) {
        console.error('Error fetching build details:', error);
        setError('Failed to load build details');
    } finally {
        setIsLoadingDetails(false);
    }
};

    const fetchBuilds = async () => {
        try {
            setIsLoading(true);
            setError(null);
            
            const res = await fetch(`/api/shared-builds`);
            if (!res.ok) { 
                throw new Error(`Failed to fetch builds: ${res.status} ${res.statusText}`); 
            } 

            const data = await res.json(); 
            setBuilds(data);
            console.log("Received data:", data.length, "items");
            // console.log(data); 
        } catch (error) {
            console.error("Error fetching builds:", error);
            setError(error.message);
        } finally {
            setIsLoading(false);
        }
    };

    useEffect(() => {
        fetchBuilds();
    }, []);

    

    const handleLogout = () => {
        localStorage.clear();
        navigate("/login", { replace: true });
    };

    // Modal component
    const BuildDetailsModal = ({ build, onClose }) => {
        if (!build) return null;

        return (
            <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
                <div className="bg-gray-800 rounded-xl max-w-2xl w-full max-h-[80vh] overflow-y-auto">
                    <div className="flex justify-between items-center p-6 border-b border-gray-700">
                        <h2 className="text-xl font-semibold text-gray-100">
                            {build.buildName} - Components
                        </h2>
                        <button
                            onClick={onClose}
                            className="text-gray-400 hover:text-gray-200 transition-colors"
                        >
                            <X className="h-6 w-6" />
                        </button>
                    </div>

                    <div className="p-6">
                        {isLoadingDetails ? (
                            <div className="flex flex-col items-center justify-center py-8">
                                <div className="w-8 h-8 border-4 border-gray-600 border-t-purple-400 rounded-full animate-spin mb-4"></div>
                                <p className="text-gray-400">Loading component details...</p>
                            </div>
                        ) : (
                            <div className="space-y-4">
                                {/* CPU */}
                                {build.cpu && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">CPU</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.cpu.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.cpu.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* GPU */}
                                {build.gpu && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Graphics Card</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.gpu.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.gpu.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* Motherboard */}
                                {build.motherboard && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Motherboard</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.motherboard.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.motherboard.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* RAM */}
                                {build.ram && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Memory (RAM)</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.ram.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.ram.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* Storage */}
                                {build.ssd && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Storage</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.ssd.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.ssd.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* PSU */}
                                {build.psu && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Power Supply</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.psu.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.psu.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* CPU Cooler */}
                                {build.cpuCooler && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">CPU Cooler</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.cpuCooler.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.cpuCooler.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* Casing */}
                                {build.casing && (
                                    <div className="bg-gray-700 rounded-lg p-4">
                                        <h3 className="text-lg font-medium text-purple-400 mb-2">Case</h3>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Brand:</span> {build.casing.brand_name || 'N/A'}
                                        </p>
                                        <p className="text-gray-200">
                                            <span className="font-medium">Model:</span> {build.casing.model_name || 'N/A'}
                                        </p>
                                    </div>
                                )}

                                {/* Build Info */}
                                <div className="bg-gray-700 rounded-lg p-4 mt-6">
                                    <h3 className="text-lg font-medium text-purple-400 mb-2">Build Information</h3>
                                    <p className="text-gray-200">
                                        <span className="font-medium">Created:</span> {build.savedAt ? new Date(build.savedAt).toLocaleDateString() : 'N/A'}
                                    </p>
                                    <p className="text-gray-200">
                                        <span className="font-medium">Build ID:</span> {build.id}
                                    </p>
                                </div>
                            </div>
                        )}
                    </div>

                    <div className="flex justify-end gap-3 p-6 border-t border-gray-700">
                        <button
                            onClick={onClose}
                            className="px-4 py-2 bg-gray-600 text-gray-200 rounded-lg hover:bg-gray-500 transition-colors"
                        >
                            Close
                        </button>
                    </div>
                </div>
            </div>
        );
    };

    const LoadingSpinner = () => (
        <div className="flex flex-col items-center justify-center min-h-96 space-y-4">
            <div className="w-10 h-10 border-4 border-gray-600 border-t-purple-400 rounded-full animate-spin"></div>
            <p className="text-gray-400 text-lg">Loading builds...</p>
        </div>
    );

    const ErrorMessage = ({ message }) => (
        <div className="flex flex-col items-center justify-center min-h-96 space-y-4 text-center max-w-md mx-auto">
            <div className="text-6xl">⚠️</div>
            <h3 className="text-xl font-semibold text-red-400">Something went wrong</h3>
            <p className="text-gray-400">{message}</p>
            <button 
                className="px-6 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
                onClick={() => window.location.reload()}
            >
                Try Again
            </button>
        </div>
    );

    // Fixed BuildCard with working View Details button
    const BuildCard = ({ build, onDelete }) => {
        const handleViewDetails = async () => {
            setSelectedBuild(build);
            setIsModalOpen(true);
            await fetchBuildDetails(build);
        };

        const handleDelete = async (e) => {
        e.stopPropagation(); // Prevent triggering other click events
        
        if (!window.confirm(`Are you sure you want to delete "${build.buildName}"? This action cannot be undone.`)) {
            return;
        }

        try {
            const res = await fetch(`/api/shared-builds/${build.id}`, {
                method: 'DELETE'
            });

            if (res.ok) {
                onDelete(build.id);
                alert('Build deleted successfully!');
            } else {
                throw new Error(`Failed to delete build: ${res.status}`);
            }
        } catch (error) {
            console.error('Error deleting build:', error);
            alert('Failed to delete build. Please try again.');
        }
    };

        return (
        <div className="bg-gray-800 border border-gray-700 rounded-xl p-6 shadow-sm hover:shadow-lg hover:-translate-y-1 transition-all duration-200">
            <div className="flex justify-between items-start mb-4">
                <h3 className="text-lg font-semibold text-gray-100 flex-1">{build.buildName}</h3>
                <div className="flex items-center gap-2">
                    <span className="text-xs text-gray-300 bg-gray-700 px-2 py-1 rounded">
                        ID: {build.id}
                    </span>
                    <button
                        onClick={handleDelete}
                        className="p-1 text-gray-400 hover:text-red-400 transition-colors"
                        title="Delete build"
                    >
                        <Trash2 size={16} />
                    </button>
                </div>
            </div>
            
            <div className="mb-6">
                <div className="space-y-1 text-sm text-gray-400">
                    {build.savedAt && (
                        <span className="block">
                            Created: {new Date(build.savedAt).toLocaleDateString()}
                        </span>
                    )}
                </div>
            </div>
            
            <div className="flex gap-3">
                <button 
                    onClick={handleViewDetails}
                    className="flex-1 px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors font-medium"
                >
                    View Details
                </button>
                <button className="flex-1 px-4 py-2 bg-gray-700 text-gray-200 rounded-lg hover:bg-gray-600 transition-colors font-medium border border-gray-600">
                    Copy Build
                </button>
            </div>
        </div>
    );
    };

    // Close modal function
    const closeModal = () => {
        setIsModalOpen(false);
        setSelectedBuild(null);
        setBuildDetails(null);
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
            {/* SIDEBAR */}
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
                                to="/dashboard"
                                className="flex items-center px-6 py-3 text-gray-200 hover:bg-gray-700 hover:text-white transition-colors duration-200 rounded-r-full"
                            >
                                Dashboard
                            </Link>
                        </li>
                        <li className="mb-1">
                            <Link
                                to="/builds/my"
                                className="flex items-center px-6 py-3 text-gray-200 hover:bg-gray-700 hover:text-white transition-colors duration-200 rounded-r-full"
                            >
                                My Builds
                            </Link>
                        </li>
                        <li className="mb-1">
                            <Link
                                to="/builds"
                                className="flex items-center px-6 py-3 text-white bg-gray-700 transition-colors duration-200 rounded-r-full"
                            >
                                All Builds
                            </Link>
                        </li>

                        {/* Dropdown: All Components */}
                        <li className="mb-1">
                            <button
                                onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                                className="flex justify-between items-center w-full px-6 py-3 text-gray-200 hover:bg-gray-700 hover:text-white transition-colors duration-200 rounded-r-full"
                            >
                                <span className="font-medium">All Components</span>
                                <ChevronDown
                                    className={`ml-2 h-4 w-4 transform transition-transform duration-200 ${isDropdownOpen ? "rotate-180" : "rotate-0"}`}
                                />
                            </button>
                            {isDropdownOpen && (
                                <ul className="mt-1 bg-gray-900">
                                    <li>
                                        <Link
                                            to="/components/casing"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            Casings
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/cpu"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            CPUs
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/cpu-cooler"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            CPU Coolers
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/gpu"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            Graphics Cards (GPUs)
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/motherboard"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            Motherboards
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/psu"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            Power Supplies (PSUs)
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/ram"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
                                        >
                                            Memories (RAMs)
                                        </Link>
                                    </li>
                                    <li>
                                        <Link
                                            to="/components/ssd"
                                            className="block px-8 py-2 text-sm text-gray-300 hover:bg-gray-700 hover:text-white rounded-md transition-colors duration-200"
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
                                className="flex items-center px-6 py-3 text-gray-200 hover:bg-gray-700 hover:text-white transition-colors duration-200 rounded-r-full"
                            >
                                Launch Configurator
                            </Link>
                        </li>
                    </ul>
                </nav>
            </aside>

            {/* RIGHT PANEL */}
            <div className="flex-1 flex flex-col">
                {/* TOP BAR */}
                <header className="flex justify-between items-center bg-gray-800 border-b border-gray-700 px-8 h-16 shadow-sm">
                    <div>
                        <h2 className="text-xl font-semibold text-gray-100">Community Builds</h2>
                    </div>
                    <div className="flex items-center space-x-4">
                        {/* Refresh button */}
                        <button
                            onClick={fetchBuilds}
                            disabled={isLoading}
                            className="flex items-center space-x-1 text-gray-200 hover:text-purple-400 transition-colors duration-200 disabled:opacity-50"
                        >
                            <RefreshCw className={`h-5 w-5 ${isLoading ? 'animate-spin' : ''}`} />
                            <span className="font-medium">Refresh</span>
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
                            <span className="font-medium">Chat</span>
                        </button>

                        {/* Logout button */}
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
                    {isLoading ? (
                        <LoadingSpinner />
                    ) : error ? (
                        <ErrorMessage message={error} />
                    ) : (
                        <div className="max-w-7xl mx-auto">
                            {/* Stats */}
                            <div className="flex justify-center mb-8">
                                <div className="bg-gray-800 border border-gray-700 rounded-2xl px-8 py-6 text-center shadow-md">
                                    <div className="text-3xl font-bold text-purple-400 mb-1">
                                        {builds.length}
                                    </div>
                                    <div className="text-sm text-gray-400">
                                        Total Builds
                                    </div>
                                </div>
                            </div>

                            {/* Content */}
                            {builds.length === 0 ? (
                                <div className="text-center py-16">
                                    <div className="text-6xl mb-6">🖥️</div>
                                    <h3 className="text-xl font-semibold text-gray-100 mb-3">
                                        No builds found
                                    </h3>
                                    <p className="text-gray-400 mb-8">
                                        Be the first to share a build with the community!
                                    </p>
                                    <Link
                                        to="/configurator"
                                        className="inline-block px-6 py-3 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors font-medium"
                                    >
                                        Create Build
                                    </Link>
                                </div>
                            ) : (
                                <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                    {builds.map(build => (
                                        <BuildCard 
                                            key={build.id} 
                                            build={build} 
                                            onDelete={handleDeleteBuild}
                                        />
                                    ))}
                                </div>
                            )}
                        </div>
                    )}
                </main>

                {/* Chat Sidebar */}
                {isChatOpen && (
                    <div className="fixed right-0 top-0 bottom-0 z-40 h-full">
                        <ChatSidebar
                            onClose={() => setIsChatOpen(false)}
                            currentPage="/builds"
                        />
                    </div>
                )}

                {/* Add the modal at the end before closing div */}
                {isModalOpen && (
                    <BuildDetailsModal 
                        build={buildDetails || selectedBuild} 
                        onClose={closeModal}
                    />
                )}
            </div>
        </div>
    );
};

export default Builds;