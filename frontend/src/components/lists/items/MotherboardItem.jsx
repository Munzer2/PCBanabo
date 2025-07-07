export default function MotherboardItem({ motherboard, showButton }) {
  return (
    <div className="border border-gray-700 rounded-lg p-4 shadow-md bg-slate-800 relative">
      <div className="flex flex-col space-y-1">
        {/* Header with title and price stacked on small screens */}
        <div className="flex flex-col sm:flex-row sm:justify-between sm:items-start mb-2">
          <h3 className="text-lg font-semibold text-white mb-1 sm:mb-0 pr-16 sm:pr-4">{motherboard.brand_name} - {motherboard.model_name}</h3>
          <div className="text-sm text-blue-400 font-semibold whitespace-nowrap">
            ${motherboard.avg_price}
          </div>
        </div>
        
        <p className="text-sm text-blue-400"><a href={motherboard.official_product_url} className="hover:text-blue-300">Link to Official Product</a></p>
        <ul className="text-xs text-gray-300 mt-2 space-y-1">
          <li>Socket: {motherboard.socket}</li>
          <li>Chipset: {motherboard.chipset}</li>
          <li>Form Factor: {motherboard.form_factor}</li>
          <li>Memory Type: {motherboard.mem_type}</li>
          <li>Memory Slots: {motherboard.mem_slot}</li>
          <li>Max Memory Speed: {motherboard.max_mem_speed} MHz</li>
          <li>PCIe x16 Slots: {motherboard.pcie_slot_x16}</li>
          <li>PCIe x4 Slots: {motherboard.pcie_slot_x4}</li>
          <li>NVMe Slots: {motherboard.nvme_slots}</li>
          <li>NVMe Version: {motherboard.nvme_version}</li>
          <li>SATA Ports: {motherboard.sata_ports}</li>
          <li>LAN Speed: {motherboard.lan_speed}</li>
          {motherboard.wifi_version && <li>WiFi: {motherboard.wifi_version}</li>}
          {motherboard.bluetooth_version && <li>Bluetooth: {motherboard.bluetooth_version}</li>}
          <li>USB 2.0 Ports: {motherboard.usb_2_ports}</li>
          <li>USB 3.0 Ports: {motherboard.usb_3_ports}</li>
          <li>USB-C Ports: {motherboard.usb_c_ports}</li>
          {motherboard.thunderbolt_ports > 0 && <li>Thunderbolt Ports: {motherboard.thunderbolt_ports}</li>}
          {motherboard.opt_feature && <li>Special Features: {motherboard.opt_feature}</li>}
        </ul>
      </div>

      {/* Optional Button */}
      {showButton && (
        <div className="mt-4">
          <button className="bg-blue-700 text-white px-4 py-1 rounded hover:bg-blue-600 transition-colors">
            Select Motherboard
          </button>
        </div>
      )}
    </div>
  );
}