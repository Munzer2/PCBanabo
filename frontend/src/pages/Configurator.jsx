// src/pages/Configurator.jsx
import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import api from '../api';
import { Computer, ArrowLeft, User, LogOut, Save, Sparkles, X, Globe, Lock } from 'lucide-react';
import ComponentSlot from '../components/configurator/ComponentSlot';
import ChatSidebar from '../components/ChatBot/ChatSidebar';
import CustomAlert from '../components/common/CustomAlert';

const initialComponents = {
  casing: null,
  cpu: null,
  cpuCooler: null,
  gpu: null,
  motherboard: null,
  psu: null,
  ram: null,
  storage: null
};

export default function Configurator() {
  const [components, setComponents] = useState(() => {
    // Load components from localStorage on initialization
    const saved = localStorage.getItem('configurator-components');
    return saved ? JSON.parse(saved) : initialComponents;
  });
  const [user, setUser] = useState(null);
  const [hasChanges, setHasChanges] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [buildName, setBuildName] = useState('');
  const [isPublic, setIsPublic] = useState(true);
  const [isChatOpen, setIsChatOpen] = useState(false);
  
  // AI Build states
  const [isAIModalOpen, setIsAIModalOpen] = useState(false);
  const [isGeneratingBuild, setIsGeneratingBuild] = useState(false);
  const [suggestedBuild, setSuggestedBuild] = useState(null);
  const [buildPreferences, setBuildPreferences] = useState({
    budget: '',
    useCase: '',
    preferences: ''
  });
  
  // Custom Alert states
  const [alert, setAlert] = useState({
    isOpen: false,
    type: 'info',
    message: '',
    onConfirm: null,
    showCancel: false,
    confirmText: 'OK',
    cancelText: 'Cancel'
  });
  
  // Alert helper functions
  const showAlert = (type, message, options = {}) => {
    setAlert({
      isOpen: true,
      type,
      message,
      onConfirm: options.onConfirm || null,
      showCancel: options.showCancel || false,
      confirmText: options.confirmText || 'OK',
      cancelText: options.cancelText || 'Cancel'
    });
  };

  const closeAlert = () => {
    setAlert(prev => ({ ...prev, isOpen: false }));
  };

  const showConfirm = (message, onConfirm, options = {}) => {
    showAlert('confirm', message, {
      onConfirm,
      showCancel: true,
      confirmText: options.confirmText || 'Yes',
      cancelText: options.cancelText || 'No'
    });
  };

  const showSuccess = (message) => showAlert('success', message);
  const showError = (message) => showAlert('error', message);
  const showWarning = (message) => showAlert('warning', message);
  const showInfo = (message) => showAlert('info', message);
  
  const navigate = useNavigate();
  const location = useLocation();

  // Save components to localStorage whenever they change
  useEffect(() => {
    localStorage.setItem('configurator-components', JSON.stringify(components));
    console.log(components);
  }, [components]);

  // Debug localStorage on component mount
  useEffect(() => {
    
    // For development: if no userId exists, create a mock one
    if (!localStorage.getItem('userId')) {
      console.log('No userId found, setting mock userId for development');
      localStorage.setItem('userId', 'dev-user-123');
    }
  }, []);

  useEffect(() => {
    // Set a timeout to prevent infinite loading
    const timeout = setTimeout(() => {
      if (isLoading) {
        console.log('Loading timeout reached, setting fallback user');
        setUser({ id: localStorage.getItem('userId') || 'unknown', name: 'User' });
        setIsLoading(false);
      }
    }, 3000); // Reduced to 3 second timeout
    
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
        
        // Try to fetch user data, but don't fail if it doesn't work
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
  }, [navigate, isLoading]);

  useEffect(() => {
    // Handle component selection from component pages
    if (location.state?.selectedComponent && location.state?.componentType) {
      const { selectedComponent, componentType } = location.state;
      setComponents(prev => ({
        ...prev,
        [componentType]: {
          id: selectedComponent.id || selectedComponent._id,
          name: `${selectedComponent.brand_name} - ${selectedComponent.model_name}`,
          price: selectedComponent.avg_price || selectedComponent.average_price || selectedComponent.avgPrice,
          ...selectedComponent
        }
      }));
      setHasChanges(true);
      // Clear the state to prevent re-selection on page refresh
      window.history.replaceState({}, document.title);
    }
  }, [location.state]);

  // Handle component selection
  const handleSelectComponent = (type) => {
    const componentRoutes = {
      casing: '/casing',
      cpu: '/cpu',
      cpuCooler: '/cpu-cooler',
      gpu: '/gpu',
      motherboard: '/motherboard',
      psu: '/psu',
      ram: '/ram',
      storage: '/ssd'
    };
    
    const route = '/components' + componentRoutes[type];
    if (route) {
      navigate(route, { 
        state: { 
          fromConfigurator: true,
          componentType: type,
          selectedComponents: components 
        } 
      });
    }
  };

  // Handle component removal
  const handleRemoveComponent = (type) => {
    setComponents(prev => ({
      ...prev,
      [type]: null
    }));
    setHasChanges(true);
  };

  // Handle saving the build
  const handleSaveBuild = async () => {
    if (!buildName.trim()) {
      showWarning('Please enter a build name before saving.');
      return;
    }

    try {
      const buildData = {
        userId: localStorage.getItem('userId'),
        components: Object.fromEntries(
          Object.entries(components)
            .filter(([, component]) => component !== null)
            .map(([type, component]) => [type, component.id])
        ),
        totalPrice: Object.values(components)
          .filter(component => component !== null)
          .reduce((sum, component) => sum + parseFloat(component.price || 0), 0)
      };
      
      // console.log('Saving build:', buildData);
      
      // Make API call to save the build
      const dto = {
        cpuId:          buildData.components.cpu, 
        motherboardId:    buildData.components.motherboard,
        ramId:          buildData.components.ram, 
        ssdId:    buildData.components.ssd, 
        gpuId:    buildData.components.gpu, 
        psuId:    buildData.components.psu, 
        casingId:    buildData.components.casing, 
        cpuCoolerId:    buildData.components.cpuCooler,
        buildName:      buildName.trim(),
        public:       isPublic 
      };

      console.log(dto);

      const res = await api.post(`/api/shared-builds/${buildData.userId}`, dto);
      const saved = res.data;
      console.log('Build has been saved on server: ', saved); 
      
      setHasChanges(false);
      showSuccess('Build saved successfully!');
    } catch (error) {
      console.error('Error saving build:', error);
      showError('Failed to save build. Please try again.');
    }
  };

  // AI Build Generation Functions
  const handleAIBuildRequest = () => {
    setIsAIModalOpen(true);
    setSuggestedBuild(null);
  };

  const handleGenerateAIBuild = async () => {
    if (!buildPreferences.budget.trim()) {
      showWarning('Please specify a budget for the AI build suggestion.');
      return;
    }

    setIsGeneratingBuild(true);
    try {
      console.log('Sending AI build request:', buildPreferences);
      
      const response = await api.post('/api/chat/suggest-build', buildPreferences);
      console.log('AI API Response:', response.data);
      
      let buildData;
      try {
        // Check if response.data is already an object or needs parsing
        const responseText = typeof response.data === 'string' ? response.data : response.data.reply;
        
        if (!responseText) {
          throw new Error('Empty response from AI service');
        }
        
        // Clean the response and try to parse JSON
        const cleanResponse = responseText
          .replace(/```json\n?|\n?```/g, '') // Remove markdown code blocks
          .replace(/^[^{]*({[\s\S]*})[^}]*$/, '$1') // Extract JSON object
          .trim();
        
        console.log('Cleaned response:', cleanResponse);
        buildData = JSON.parse(cleanResponse);
        
        // Validate the parsed data structure
        if (!buildData.components || typeof buildData.components !== 'object') {
          throw new Error('Invalid build data structure - missing components');
        }
        
      } catch (parseError) {
        console.error('Failed to parse AI response:', parseError);
        console.error('Raw response:', response.data);
        
        // Fallback: try to extract JSON from the response text
        const responseText = typeof response.data === 'string' ? response.data : JSON.stringify(response.data);
        const jsonMatch = responseText.match(/\{[\s\S]*\}/);
        
        if (jsonMatch) {
          try {
            buildData = JSON.parse(jsonMatch[0]);
            if (!buildData.components) {
              throw new Error('Parsed data missing components');
            }
          } catch (fallbackError) {
            throw new Error('Could not parse AI response as valid JSON');
          }
        } else {
          throw new Error('No valid JSON found in AI response');
        }
      }

      setSuggestedBuild(buildData);
      console.log('AI suggested build:', buildData);
      
    } catch (error) {
      console.error('Error generating AI build:', error);
      
      // More specific error messages
      let errorMessage = 'Failed to generate AI build suggestion. ';
      if (error.response) {
        // Server responded with error status
        errorMessage += `Server error: ${error.response.status} - ${error.response.data?.message || 'Unknown error'}`;
      } else if (error.request) {
        // Request was made but no response received
        errorMessage += 'No response from server. Please check if the backend is running.';
      } else {
        // Something else happened
        errorMessage += error.message || 'Unknown error occurred.';
      }
      
      showError(errorMessage);
    } finally {
      setIsGeneratingBuild(false);
    }
  };

  const handleAcceptAIBuild = () => {
    if (!suggestedBuild || !suggestedBuild.components) return;

    const newComponents = { ...initialComponents };
    
    // Map AI suggestion to component format using REAL database IDs
    Object.entries(suggestedBuild.components).forEach(([type, component]) => {
      if (component && component.brand_name && component.model_name && component.id) {
        newComponents[type] = {
          id: component.id, // Use the REAL database ID from AI response
          name: `${component.brand_name} - ${component.model_name}`,
          price: component.avg_price || 0,
          brand_name: component.brand_name,
          model_name: component.model_name,
          avg_price: component.avg_price
        };
      }
    });

    setComponents(newComponents);
    setBuildName(suggestedBuild.buildName || 'AI Suggested Build');
    setHasChanges(true);
    setIsAIModalOpen(false);
    setSuggestedBuild(null);
  };

  const handleCloseAIModal = () => {
    setIsAIModalOpen(false);
    setSuggestedBuild(null);
    setBuildPreferences({ budget: '', useCase: '', preferences: '' });
  };

  // Handle navigation with unsaved changes warning
  const handleNavigation = (path) => {
    if (hasChanges) {
      showConfirm(
        'You have unsaved changes. Are you sure you want to leave?',
        () => navigate(path),
        { confirmText: 'Leave', cancelText: 'Stay' }
      );
    } else {
      navigate(path);
    }
  };

  // Handle logout with unsaved changes warning
  const handleLogout = () => {
    if (hasChanges) {
      showConfirm(
        'You have unsaved changes. Are you sure you want to logout?',
        () => {
          localStorage.clear();
          navigate('/login', { replace: true });
        },
        { confirmText: 'Logout', cancelText: 'Stay' }
      );
    } else {
      localStorage.clear();
      navigate('/login', { replace: true });
    }
  };

  // Handle clearing all selections
  const handleClearAllSelections = () => {
    const hasAnyComponent = Object.values(components).some(component => component !== null);
    
    if (!hasAnyComponent) {
      showInfo('No components to clear.');
      return;
    }

    showConfirm(
      'Are you sure you want to clear all selected components? This action cannot be undone.',
      () => {
        setComponents(initialComponents);
        setBuildName('');
        setIsPublic(true);
        setHasChanges(false);
        showSuccess('All components have been cleared successfully.');
      },
      { confirmText: 'Clear All', cancelText: 'Cancel' }
    );
  };

  // Invalid user data or still loading
  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-screen bg-slate-900">
        <p className="text-gray-400 text-lg">Loading configurator…</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen flex bg-slate-900 text-gray-100">

      {/* Main Content Area */}
      <div className={`flex-1 flex flex-col transition-all duration-300 ${isChatOpen ? 'lg:mr-80' : 'mr-0'}`}>
        {/* Top Navigation Bar */}
        <header className="flex justify-between items-center bg-slate-800 border-b border-gray-700 px-4 sm:px-8 h-16 shadow-md">
          <div className="flex items-center gap-2">
            <button 
              onClick={() => handleNavigation('/dashboard')} 
              className="flex items-center text-blue-400 hover:text-blue-300 transition"
            >
              <ArrowLeft size={18} className="mr-1" />
              <span className="hidden sm:inline">Dashboard</span>
            </button>
          </div>
          <div className="flex items-center gap-3 sm:gap-6">
            {/* Chat toggle button */}
            <button
              onClick={() => setIsChatOpen(!isChatOpen)}
              className="flex items-center text-gray-300 hover:text-blue-400 transition"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="18"
                height="18"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
                className="sm:mr-2"
              >
                <path d="M21 15a2 2 0 0 1-2 2H7l-4 4V5a2 2 0 0 1 2-2h14a2 2 0 0 1 2 2z"></path>
              </svg>
              <span className="hidden sm:inline">Chat</span>
            </button>
            <button 
              onClick={() => handleNavigation('/profile')} 
              className="flex items-center text-gray-300 hover:text-blue-400 transition"
            >
              <User size={18} className="sm:mr-2" />
              <span className="hidden sm:inline">Profile</span>
            </button>
            <button 
              onClick={handleLogout} 
              className="flex items-center text-gray-300 hover:text-red-400 transition"
            >
              <LogOut size={18} className="sm:mr-2" />
              <span className="hidden sm:inline">Logout</span>
            </button>
          </div>
        </header>

        {/* Configurator Content */}
        <main className="flex-1 overflow-y-auto p-3 sm:p-6 bg-slate-900">
          <div className="max-w-5xl mx-auto">
            {/* Header */}
            <div className="mb-6 sm:mb-8 text-center">
              <h1 className="text-2xl sm:text-3xl font-bold text-white">Configurator</h1>
              <p className="text-gray-400 mt-2 text-sm sm:text-base">Select compatible components for your custom build</p>
            </div>

            {/* Component Selection Grid */}
            <div className="grid grid-cols-1 sm:grid-cols-2 gap-3 sm:gap-4 mb-6 sm:mb-8">
              {Object.entries(components).map(([type, component]) => (
                <ComponentSlot 
                  key={type}
                  type={type}
                  component={component}
                  onClick={() => handleSelectComponent(type)}
                  onRemove={() => handleRemoveComponent(type)}
                />
              ))}
            </div>

            {/* Build Name Input */}
            <div className="mb-6 sm:mb-8">
              <div className="max-w-md mx-auto">
                <label htmlFor="buildName" className="block text-sm font-medium text-gray-300 mb-2">
                  Build Name
                </label>
                <input
                  type="text"
                  id="buildName"
                  value={buildName}
                  onChange={(e) => setBuildName(e.target.value)}
                  placeholder="Enter a name for your build..."
                  className="w-full px-3 sm:px-4 py-2 bg-slate-800 border border-gray-600 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent text-sm sm:text-base"
                  maxLength={100}
                />
                <p className="text-xs text-gray-500 mt-1">
                  {buildName.length}/100 characters
                </p>
              </div>
            </div>

            {/* Public/Private Setting */}
            <div className="mb-6 sm:mb-8">
              <div className="max-w-md mx-auto">
                <div className="flex items-center justify-center p-4 bg-slate-800 rounded-lg border border-gray-600">
                  <div className="flex items-center">
                    <input
                      type="checkbox"
                      id="isPublic"
                      checked={isPublic}
                      onChange={(e) => setIsPublic(e.target.checked)}
                      className="h-4 w-4 text-blue-600 bg-slate-700 border-gray-500 rounded focus:ring-blue-500 focus:ring-2"
                    />
                    <label htmlFor="isPublic" className="ml-3 flex items-center text-sm font-medium text-gray-300">
                      {isPublic ? (
                        <Globe size={16} className="mr-2 text-green-400" />
                      ) : (
                        <Lock size={16} className="mr-2 text-gray-400" />
                      )}
                      Make this build public
                    </label>
                  </div>
                </div>
                <p className="text-xs text-gray-500 mt-2 text-center">
                  {isPublic ? (
                    <span className="text-green-400">
                      ✓ Other users can view and discover your build
                    </span>
                  ) : (
                    <span className="text-gray-400">
                      🔒 Only you can see this build
                    </span>
                  )}
                </p>
              </div>
            </div>

            {/* Build Actions */}
            <div className="flex justify-center gap-4 mt-6 sm:mt-8">
              <button
                onClick={handleAIBuildRequest}
                className="flex items-center bg-purple-600 hover:bg-purple-700 text-white px-4 sm:px-6 py-2 sm:py-3 rounded-lg shadow-md transition transform hover:scale-105 text-sm sm:text-base"
              >
                <Sparkles size={18} className="mr-2" />
                Build with AI
              </button>
              <button
                onClick={handleClearAllSelections}
                className="flex items-center bg-red-600 hover:bg-red-700 text-white px-4 sm:px-6 py-2 sm:py-3 rounded-lg shadow-md transition transform hover:scale-105 text-sm sm:text-base"
              >
                <X size={18} className="mr-2" />
                Clear All Selections
              </button>
              <button
                onClick={handleSaveBuild}
                className="flex items-center bg-blue-600 hover:bg-blue-700 text-white px-4 sm:px-6 py-2 sm:py-3 rounded-lg shadow-md transition transform hover:scale-105 text-sm sm:text-base"
              >
                <Save size={18} className="mr-2" />
                Save Build
              </button>
            </div>
          </div>
        </main>
      </div>
      
      {/* AI Build Modal */}
      {isAIModalOpen && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 p-4">
          <div className="bg-gray-800 rounded-xl max-w-4xl w-full max-h-[90vh] overflow-hidden flex flex-col">
            {/* Modal Header */}
            <div className="flex items-center justify-between p-6 border-b border-gray-700">
              <h2 className="text-xl font-semibold text-gray-100 flex items-center">
                <Sparkles className="mr-2 h-5 w-5 text-purple-400" />
                Build with AI
              </h2>
              <button
                onClick={handleCloseAIModal}
                className="text-gray-400 hover:text-gray-200 transition-colors"
              >
                <X className="h-6 w-6" />
              </button>
            </div>

            {/* Modal Content */}
            <div className="flex-1 overflow-y-auto p-6">
              {!suggestedBuild ? (
                // Input Form
                <div className="space-y-6">
                  <div className="text-center mb-6">
                    <h3 className="text-lg font-medium text-gray-200 mb-2">
                      Let AI suggest a perfect build for you!
                    </h3>
                    <p className="text-gray-400">
                      Provide your requirements and preferences, and our AI will create a custom PC build suggestion.
                    </p>
                  </div>

                  <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                    <div>
                      <label className="block text-sm font-medium text-gray-300 mb-2">
                        Budget (Required)
                      </label>
                      <input
                        type="text"
                        value={buildPreferences.budget}
                        onChange={(e) => setBuildPreferences(prev => ({ ...prev, budget: e.target.value }))}
                        placeholder="e.g., 80,000 BDT, 1 lakh, under 50k"
                        className="w-full px-4 py-2 bg-gray-700 border border-gray-600 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500"
                      />
                    </div>

                    <div>
                      <label className="block text-sm font-medium text-gray-300 mb-2">
                        Use Case
                      </label>
                      <select
                        value={buildPreferences.useCase}
                        onChange={(e) => setBuildPreferences(prev => ({ ...prev, useCase: e.target.value }))}
                        className="w-full px-4 py-2 bg-gray-700 border border-gray-600 rounded-lg text-white focus:outline-none focus:ring-2 focus:ring-purple-500"
                      >
                        <option value="">Select use case</option>
                        <option value="gaming">Gaming</option>
                        <option value="content creation">Content Creation</option>
                        <option value="programming">Programming & Development</option>
                        <option value="office work">Office Work</option>
                        <option value="streaming">Streaming</option>
                        <option value="general purpose">General Purpose</option>
                        <option value="workstation">Professional Workstation</option>
                      </select>
                    </div>

                    <div className="md:col-span-2">
                      <label className="block text-sm font-medium text-gray-300 mb-2">
                        Additional Preferences
                      </label>
                      <textarea
                        value={buildPreferences.preferences}
                        onChange={(e) => setBuildPreferences(prev => ({ ...prev, preferences: e.target.value }))}
                        placeholder="Any specific requirements? e.g., prefer Intel/AMD, RGB lighting, quiet operation, future-proof, etc."
                        rows={3}
                        className="w-full px-4 py-2 bg-gray-700 border border-gray-600 rounded-lg text-white placeholder-gray-400 focus:outline-none focus:ring-2 focus:ring-purple-500"
                      />
                    </div>
                  </div>

                  <div className="flex justify-center">
                    <button
                      onClick={handleGenerateAIBuild}
                      disabled={isGeneratingBuild || !buildPreferences.budget.trim()}
                      className="flex items-center bg-purple-600 hover:bg-purple-700 disabled:bg-gray-600 disabled:cursor-not-allowed text-white px-6 py-3 rounded-lg shadow-md transition transform hover:scale-105"
                    >
                      {isGeneratingBuild ? (
                        <>
                          <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white mr-2"></div>
                          Generating...
                        </>
                      ) : (
                        <>
                          <Sparkles size={18} className="mr-2" />
                          Generate AI Build
                        </>
                      )}
                    </button>
                  </div>
                </div>
              ) : (
                // Suggested Build Display
                <div className="space-y-6">
                  <div className="text-center mb-6">
                    <h3 className="text-xl font-bold text-gray-100 mb-2">
                      {suggestedBuild.buildName || 'AI Suggested Build'}
                    </h3>
                    <p className="text-green-400 text-lg font-semibold">
                      Total Price: ৳{suggestedBuild.totalPrice?.toLocaleString() || 'N/A'}
                    </p>
                    {suggestedBuild.explanation && (
                      <p className="text-gray-400 mt-2 max-w-2xl mx-auto">
                        {suggestedBuild.explanation}
                      </p>
                    )}
                  </div>

                  {/* Components Grid */}
                  <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                    {suggestedBuild.components && Object.entries(suggestedBuild.components).map(([type, component]) => (
                      <div key={type} className="bg-gray-700 rounded-lg p-4 border border-gray-600">
                        <h4 className="text-purple-400 font-medium mb-2 capitalize">
                          {type === 'cpuCooler' ? 'CPU Cooler' : type === 'casing' ? 'Case' : type.toUpperCase()}
                        </h4>
                        <p className="text-gray-200 text-sm">
                          <span className="font-medium">Brand:</span> {component.brand_name || 'N/A'}
                        </p>
                        <p className="text-gray-200 text-sm">
                          <span className="font-medium">Model:</span> {component.model_name || 'N/A'}
                        </p>
                        <p className="text-green-400 text-sm font-semibold">
                          Price: ৳{component.avg_price?.toLocaleString() || 'N/A'}
                        </p>
                      </div>
                    ))}
                  </div>

                  <div className="flex justify-center gap-4 mt-6">
                    <button
                      onClick={handleCloseAIModal}
                      className="px-6 py-2 bg-gray-600 hover:bg-gray-700 text-white rounded-lg transition-colors"
                    >
                      Cancel
                    </button>
                    <button
                      onClick={handleGenerateAIBuild}
                      className="px-6 py-2 bg-blue-600 hover:bg-blue-700 text-white rounded-lg transition-colors"
                    >
                      Generate New
                    </button>
                    <button
                      onClick={handleAcceptAIBuild}
                      className="px-6 py-2 bg-green-600 hover:bg-green-700 text-white rounded-lg transition-colors"
                    >
                      Accept & Use This Build
                    </button>
                  </div>
                </div>
              )}
            </div>
          </div>
        </div>
      )}
      
      {/* ChatSidebar - Only show on larger screens when open, overlay on mobile */}
      <ChatSidebar 
        isOpen={isChatOpen} 
        onClose={() => setIsChatOpen(false)}
        buildContext={components}
        currentPage="/configurator"
      />

      {/* Custom Alert */}
      <CustomAlert
        isOpen={alert.isOpen}
        onClose={closeAlert}
        onConfirm={alert.onConfirm}
        message={alert.message}
        type={alert.type}
        showCancel={alert.showCancel}
        confirmText={alert.confirmText}
        cancelText={alert.cancelText}
      />
    </div>
  );
}