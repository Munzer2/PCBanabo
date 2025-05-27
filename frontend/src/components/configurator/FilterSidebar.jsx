// src/components/configurator/FilterSidebar.jsx
import React, { useState } from 'react';
import { ChevronDown, ChevronUp, Filter, X } from 'lucide-react';

export default function FilterSidebar() {
  // State for filter sections expansion
  const [expanded, setExpanded] = useState({
    price: true,
    brands: true,
    performance: true,
    compatibility: true
  });
  
  // State for mobile sidebar visibility
  const [isMobileVisible, setIsMobileVisible] = useState(false);

  // Toggle expansion of filter sections
  const toggleSection = (section) => {
    setExpanded({
      ...expanded,
      [section]: !expanded[section]
    });
  };

  // Filter sections data
  const filterSections = [
    {
      id: 'price',
      title: 'Price Range',
      content: (
        <div className="mt-2">
          <div className="flex items-center justify-between mb-2">
            <span className="text-sm text-gray-600">$0</span>
            <span className="text-sm text-gray-600">$2000+</span>
          </div>
          <input 
            type="range" 
            min="0" 
            max="2000" 
            className="w-full h-2 bg-gray-200 rounded-lg appearance-none cursor-pointer" 
          />
        </div>
      )
    },
    {
      id: 'brands',
      title: 'Brands',
      content: (
        <div className="mt-2 space-y-2">
          {['Intel', 'AMD', 'Nvidia', 'ASUS', 'MSI', 'Gigabyte'].map(brand => (
            <label key={brand} className="flex items-center space-x-2">
              <input type="checkbox" className="h-4 w-4 text-blue-600 rounded" />
              <span className="text-sm text-gray-700">{brand}</span>
            </label>
          ))}
        </div>
      )
    },
    {
      id: 'performance',
      title: 'Performance',
      content: (
        <div className="mt-2 space-y-2">
          {['Entry-level', 'Mid-range', 'High-end', 'Enthusiast'].map(level => (
            <label key={level} className="flex items-center space-x-2">
              <input type="checkbox" className="h-4 w-4 text-blue-600 rounded" />
              <span className="text-sm text-gray-700">{level}</span>
            </label>
          ))}
        </div>
      )
    },
    {
      id: 'compatibility',
      title: 'Compatibility',
      content: (
        <div className="mt-2 space-y-2">
          <label className="flex items-center space-x-2">
            <input type="checkbox" className="h-4 w-4 text-blue-600 rounded" checked readOnly />
            <span className="text-sm text-gray-700">Show only compatible parts</span>
          </label>
        </div>
      )
    }
  ];

  return (
    <>
      {/* Mobile Filter Button */}
      <button 
        className="fixed bottom-4 left-4 z-20 md:hidden bg-blue-600 text-white p-3 rounded-full shadow-lg"
        onClick={() => setIsMobileVisible(true)}
      >
        <Filter size={24} />
      </button>

      {/* Mobile Overlay */}
      {isMobileVisible && (
        <div 
          className="fixed inset-0 bg-black bg-opacity-50 z-30 md:hidden"
          onClick={() => setIsMobileVisible(false)}
        />
      )}

      {/* Sidebar */}
      <aside className={`
        ${isMobileVisible ? 'translate-x-0' : '-translate-x-full'}
        md:translate-x-0
        fixed md:static inset-y-0 left-0 z-40
        w-72 bg-white border-r border-gray-200
        transition-transform duration-300 ease-in-out
        overflow-y-auto
      `}>
        <div className="px-6 py-6">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-semibold text-gray-900">Filters</h2>
            <button 
              className="md:hidden text-gray-500 hover:text-gray-700"
              onClick={() => setIsMobileVisible(false)}
            >
              <X size={20} />
            </button>
          </div>

          {/* Filter Sections */}
          <div className="space-y-4">
            {filterSections.map((section) => (
              <div key={section.id} className="border-b border-gray-200 pb-4">
                <button
                  className="flex items-center justify-between w-full py-2 text-left"
                  onClick={() => toggleSection(section.id)}
                >
                  <span className="font-medium text-gray-800">{section.title}</span>
                  {expanded[section.id] ? <ChevronUp size={18} /> : <ChevronDown size={18} />}
                </button>
                
                {expanded[section.id] && section.content}
              </div>
            ))}
          </div>

          {/* Filter Actions */}
          <div className="mt-6 flex flex-col space-y-2">
            <button className="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700 transition">
              Apply Filters
            </button>
            <button className="w-full bg-gray-200 text-gray-800 py-2 rounded-md hover:bg-gray-300 transition">
              Reset Filters
            </button>
          </div>
        </div>
      </aside>
    </>
  );
}