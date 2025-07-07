export default function PsuItem({ psu, showButton }) {
  return (
    <div className="border border-gray-700 rounded-lg p-4 shadow-md bg-slate-800 relative">
      <div className="flex flex-col space-y-1">
        {/* Header with title and price stacked on small screens */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
          <h3 className="text-lg font-semibold text-white mb-1 sm:mb-0 pr-16 sm:pr-4">
            {psu.brand_name} - {psu.model_name}
          </h3>
          <div className="text-sm text-blue-400 font-semibold whitespace-nowrap">
            ${psu.avg_price}
          </div>
        </div>
        
        <p className="text-sm text-blue-400">
          <a href={psu.official_product_url} className="hover:text-blue-300">
            Link to Official Product
          </a>
        </p>
        
        <ul className="text-xs text-gray-300 mt-2 space-y-1">
          <li>Form Factor: {psu.form_factor}</li>
          <li>Wattage: {psu.wattage}W</li>
          <li>Certification: {psu.certification}</li>
          <li>Length: {psu.psuLength} mm</li>
          <li>EPS Connectors: {psu.eps_connectors}</li>
          <li>PCIe 8-pin Connectors: {psu.pcie_8pin_connectors}</li>
          <li>PCIe 16-pin Connectors: {psu.pcie_16pin_connectors}</li>
          <li>RGB: {psu.rgb ? 'Yes' : 'No'}</li>
        </ul>
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-700 text-white px-4 py-1 rounded hover:bg-blue-600 transition-colors">
            Select PSU
          </button>
        </div>
      )}
    </div>
  );
}