// scr/components/configurator/ComponentSlot.jsx
import React from 'react';
import { Cpu, Fan, HardDrive, Layers, Monitor, Power, Rat as Ram, Server } from 'lucide-react';

export default function ComponentSlot({ type, component, onClick }) {
  // Component type to icon mapping
  const iconMap = {
    casing: Layers,
    cpu: Cpu,
    cpuCooler: Fan,
    gpu: Monitor,
    motherboard: Server,
    psu: Power,
    ram: Ram,
    storage: HardDrive
  };

  // Get the appropriate icon
  const Icon = iconMap[type] || Cpu;
  
  // Format the component type for display
  const formatType = (type) => {
    const formattedTypes = {
      casing: 'Casing',
      cpu: 'CPU',
      cpuCooler: 'CPU Cooler',
      gpu: 'Graphics Card (GPU)',
      motherboard: 'Motherboard',
      psu: 'Power Supply (PSU)',
      ram: 'Memory (RAM)',
      storage: 'Storage (SSD/HDD)'
    };
    
    return formattedTypes[type] || type;
  };

  // Determine if the slot is empty
  const isEmpty = !component || !component.name;

  return (
    <button
      onClick={onClick}
      className={`
        flex items-stretch overflow-hidden
        rounded-xl transition-all duration-300
        ${isEmpty 
          ? 'bg-white border-2 border-dashed border-gray-300 hover:border-blue-400 hover:bg-blue-50' 
          : 'bg-white border border-gray-200 shadow-md hover:shadow-lg'
        }
      `}
    >
      {/* Icon Container */}
      <div className={`
        flex-shrink-0 flex items-center justify-center w-20 
        ${isEmpty 
          ? 'text-gray-400' 
          : 'bg-blue-600 text-white'
        }
      `}>
        <Icon size={24} />
      </div>
      
      {/* Content */}
      <div className="flex-1 p-4 text-left">
        <h3 className="font-medium text-gray-900">
          {formatType(type)}
        </h3>
        
        {isEmpty ? (
          <p className="text-sm text-gray-500 mt-1">Click to select a component</p>
        ) : (
          <>
            <p className="text-sm font-medium text-gray-800 mt-1">{component.name}</p>
            <p className="text-xs text-gray-500 mt-1">${component.price}</p>
          </>
        )}
      </div>
      
      {/* Status Indicator - only show for selected components */}
      {!isEmpty && (
        <div className="flex-shrink-0 w-1.5 bg-green-500" title="Compatible with current build" />
      )}
    </button>
  );
}