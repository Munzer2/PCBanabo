export default function SsdItem({ ssd, showButton, onSelect }) {
  return (
    <div className="border border-gray-700 rounded-lg p-4 shadow-md bg-slate-800 relative">
      <div className="flex flex-col space-y-1">
        {/* Header with title and price stacked on small screens */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
          <h3 className="text-lg font-semibold text-white mb-1 sm:mb-0 pr-16 sm:pr-4">
            {ssd.brand_name} - {ssd.model_name}
          </h3>
          <div className="text-sm text-blue-400 font-semibold whitespace-nowrap">
            ${ssd.avg_price}
          </div>
        </div>
        
        <p className="text-sm text-blue-400">
          <a href={ssd.official_product_url} className="hover:text-blue-300">
            Link to Official Product
          </a>
        </p>
        
        <ul className="text-xs text-gray-300 mt-2 space-y-1">
          <li>Capacity: {ssd.capacity}</li>
          <li>Form Factor: {ssd.form_factor}</li>
          <li>PCIe Generation: {ssd.pcie_gen || 'N/A'}</li>
          <li>Sequential Read: {ssd.seq_read} MB/s</li>
          <li>Sequential Write: {ssd.seq_write} MB/s</li>
          <li>DRAM Cache: {ssd.dram_cache ? 'Yes' : 'No'}</li>
        </ul>
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button 
            onClick={onSelect}
            className="bg-blue-700 text-white px-4 py-1 rounded hover:bg-blue-600 transition-colors"
          >
            Select Storage
          </button>
        </div>
      )}
    </div>
  );
}