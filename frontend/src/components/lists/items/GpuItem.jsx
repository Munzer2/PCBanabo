export default function GpuItem({ gpu, showButton }) {
  return (
    <div className="border border-gray-700 rounded-lg p-4 shadow-md bg-slate-800 relative">
      <div className="flex flex-col space-y-1">
        {/* Header with title and price stacked on small screens */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
          <h3 className="text-lg font-semibold text-white mb-1 sm:mb-0 pr-16 sm:pr-4">{gpu.brand_name} - {gpu.model_name}</h3>
          <div className="text-sm text-blue-400 font-semibold whitespace-nowrap">
            ${gpu.avg_price}
          </div>
        </div>
        
        <p className="text-sm text-blue-400"><a href={gpu.official_product_url} className="hover:text-blue-300">Link to Official Product</a></p>
        <ul className="text-xs text-gray-300 mt-2 space-y-1">
          <li>GPU Core: {gpu.gpu_core}</li>
          <li>VRAM: {gpu.vram} GB</li>
          <li>TDP: {gpu.tdp} W</li>
          <li>Power Connector Type: {gpu.power_connector_type}</li>
          <li>Power Connector Count: {gpu.power_connector_count}</li>
          <li>Card Length: {gpu.card_length} mm</li>
          <li>Card Height: {gpu.card_height} mm</li>
          <li>Card Thickness: {gpu.card_thickness} mm</li>
          <li>RGB: {gpu.rgb ? 'Yes' : 'No'}</li>
        </ul>
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-700 text-white px-4 py-1 rounded hover:bg-blue-600 transition-colors">
            Select GPU
          </button>
        </div>
      )}
    </div>
  );
}