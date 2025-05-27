export default function CasingItem({ casing, showButton }) {
  return (
    <div className="border rounded-lg p-4 shadow-sm bg-white relative">
      <div className="flex flex-col space-y-1">
        <h3 className="text-lg font-semibold">{casing.brand_name} - {casing.model_name}</h3>
        <p className="text-sm text-gray-600"><a href={casing.official_product_url}>Link to Official Product</a></p>
        <ul className="text-xs text-gray-700 mt-2 space-y-1">
          <li>Motherboard: {casing.motherboardSupport}</li>
          <li>PSU Clearance: {casing.psuClearance} W</li>
          <li>GPU Clearance: {casing.gpuClearance} W</li>
          <li>CPU Clearance: {casing.cpuClearance} W</li>
          <li>Top Radiator Support: {casing.top_rad_support}</li>
          <li>Bottom Radiator Support: {casing.bottom_rad_support}</li>
          <li>Side Radiator Support: {casing.side_rad_support}</li>
          <li>Included Fan: {casing.included_fan}</li>
          <li>RGB: {casing.rgb ? 'Yes' : 'No'}</li>
          <li>Display: {casing.display ? 'Yes' : 'No'}</li>
          <li>Color: {casing.color}</li>
        </ul>
      </div>

      {/* Avg Price - top-right or bottom-right corner */}
      <div className="absolute top-4 right-4 text-right text-sm text-blue-700 font-semibold">
        ${casing.avg_price}
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-600 text-white px-4 py-1 rounded hover:bg-blue-700">
            Select Casing
          </button>
        </div>
      )}
    </div>
  );
}
