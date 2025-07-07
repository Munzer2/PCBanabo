export default function CpuCoolerItem({ cpuCooler, showButton }) {
  return (
    <div className="border border-gray-700 rounded-lg p-4 shadow-md bg-slate-800 relative">
      <div className="flex flex-col space-y-1">
        {/* Header with title and price stacked on small screens */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
          <h3 className="text-lg font-semibold text-white mb-1 sm:mb-0 pr-16 sm:pr-4">
            {cpuCooler.brand_name} - {cpuCooler.model_name}
          </h3>
          <div className="text-sm text-blue-400 font-semibold whitespace-nowrap">
            ${cpuCooler.avg_price}
          </div>
        </div>
        
        <p className="text-sm text-blue-400">
          <a href={cpuCooler.official_product_url} className="hover:text-blue-300">
            Link to Official Product
          </a>
        </p>
        
        <ul className="text-xs text-gray-300 mt-2 space-y-1">
          <li>Type: {cpuCooler.cooler_type}</li>
          <li>Socket Support: {cpuCooler.socket_support}</li>
          {cpuCooler.towerHeight && <li>Tower Height: {cpuCooler.towerHeight} mm</li>}
          {cpuCooler.radiator_size && <li>Radiator Size: {cpuCooler.radiator_size} mm</li>}
          <li>Cooling Capacity: {cpuCooler.coolingCapacity} W</li>
          {cpuCooler.ram_clearance && <li>RAM Clearance: {cpuCooler.ram_clearance} mm</li>}
          <li>Color: {cpuCooler.color}</li>
          <li>RGB: {cpuCooler.rgb ? 'Yes' : 'No'}</li>
          {cpuCooler.display && <li>Display: Yes</li>}
        </ul>
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-700 text-white px-4 py-1 rounded hover:bg-blue-600 transition-colors">
            Select CPU Cooler
          </button>
        </div>
      )}
    </div>
  );
}