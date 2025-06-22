export default function CPUItem({ cpu, showButton }) {
  return (
    <div className="border rounded-lg p-4 shadow-sm bg-white relative">
      <div className="flex flex-col space-y-1">
        <h3 className="text-lg font-semibold">{cpu.brand_name} - {cpu.model_name}</h3>
        <p className="text-sm text-gray-600"><a href={cpu.official_product_url}>Link to Official Product</a></p>
        <ul className="text-xs text-gray-700 mt-2 space-y-1">
          <li>SKU: {cpu.sku_number}</li>
          <li>Socket: {cpu.socket}</li>
          <li>TDP: {cpu.tdp}</li>
          <li>Cache Size: {cpu.cache_size} W</li>
          <li>Overclockable: {cpu.overclockable ? 'Yes' : 'No'}</li>
        </ul>
      </div>

      {/* Avg Price - top-right or bottom-right corner */}
      <div className="absolute top-4 right-4 text-right text-sm text-blue-700 font-semibold">
        ${cpu.average_price}
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700">
            Select CPU
          </button>
        </div>
      )}
    </div>
  );
}
