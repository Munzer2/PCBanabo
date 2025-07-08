// scr/components/configurator/ComponentSlot.jsx
import React from 'react';
import { Cpu, Fan, HardDrive, Layers, Monitor, Power, Rat as Ram, Server, X } from 'lucide-react';

export default function ComponentSlot({ type, component, onClick, onRemove }) {
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
    <div className="relative">
      <button
        onClick={onClick}
        className={`
          w-full flex items-stretch overflow-hidden
          rounded-xl transition-all duration-300
          ${isEmpty 
            ? 'bg-slate-800 border-2 border-dashed border-gray-600 hover:border-blue-400 hover:bg-slate-700' 
            : 'bg-slate-800 border border-gray-600 shadow-md hover:shadow-lg hover:bg-slate-700'
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
          <h3 className="font-medium text-white">
            {formatType(type)}
          </h3>
          
          {isEmpty ? (
            <p className="text-sm text-gray-400 mt-1">Click to select a component</p>
          ) : (
            <>
              <p className="text-sm font-medium text-gray-200 mt-1">{component.name}</p>
              <p className="text-xs text-gray-400 mt-1">${component.price}</p>
            </>
          )}
        </div>
        
        {/* Status Indicator - only show for selected components */}
        {!isEmpty && (
          <div className="flex-shrink-0 w-1.5 bg-green-500" title="Compatible with current build" />
        )}
      </button>
      
      {/* Remove Button - only show for selected components */}
      {!isEmpty && onRemove && (
        <button
          onClick={(e) => {
            e.stopPropagation();
            onRemove();
          }}
          className="absolute -top-2 -right-2 w-6 h-6 bg-red-500 hover:bg-red-600 text-white rounded-full flex items-center justify-center transition-colors shadow-lg"
          title="Remove component"
        >
          <X size={14} />
        </button>
      )}
    </div>
  );
}