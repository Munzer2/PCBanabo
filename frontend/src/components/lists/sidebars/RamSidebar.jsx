import React, { useState } from "react";
import { ChevronDown, ChevronUp, Filter } from "lucide-react";

const sliderDefaults = {
  price: [0, 500],
  speed: [2400, 6400],
};

const memoryTypes = ["DDR4", "DDR5"];
const capacities = ["8GB", "16GB", "32GB", "64GB", "128GB"];
const brands = [
  "Corsair",
  "G.Skill",
  "Kingston",
  "Crucial",
  "TeamGroup",
  "ADATA",
  "PNY",
  "HyperX",
];

export default function RamSidebar({ onApply }) {
  const [expanded, setExpanded] = useState({
    price: true,
    speed: true,
    memoryType: true,
    capacity: true,
    brands: true,
    features: true,
  });

  const [filters, setFilters] = useState({
    ...sliderDefaults,
    memoryTypes: [],
    capacities: [],
    brands: [],
    rgb: false,
  });

  const toggleSection = (section) => {
    setExpanded({ ...expanded, [section]: !expanded[section] });
  };

  const handleMinMaxChange = (key, index, value) => {
    const num = Number(value);
    setFilters((prev) => {
      const updated = [...prev[key]];
      updated[index] = num;

      // Ensure min <= max
      if (index === 0 && updated[0] > updated[1]) updated[1] = updated[0];
      if (index === 1 && updated[1] < updated[0]) updated[0] = updated[1];

      return { ...prev, [key]: updated };
    });
  };

  const handleCheckboxChange = (key, value) => {
    setFilters((prev) => {
      const list = new Set(prev[key]);
      list.has(value) ? list.delete(value) : list.add(value);
      return { ...prev, [key]: [...list] };
    });
  };

  const handleSingleCheckbox = (key) => {
    setFilters((prev) => ({ ...prev, [key]: !prev[key] }));
  };

  const sliderWithInputs = (id, label, min, max, unit = "") => {
    const [valMin, valMax] = filters[id];
    return (
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-300 mb-1">
          {label}
        </label>
        <div className="flex items-center gap-2 mb-2">
          <input
            type="number"
            value={valMin}
            min={min}
            max={valMax}
            onChange={(e) => handleMinMaxChange(id, 0, e.target.value)}
            className="w-20 px-2 py-1 border border-gray-700 bg-gray-800 text-gray-300 rounded text-sm focus:border-blue-500 focus:outline-none"
          />
          <span className="text-gray-400">-</span>
          <input
            type="number"
            value={valMax}
            min={valMin}
            max={max}
            onChange={(e) => handleMinMaxChange(id, 1, e.target.value)}
            className="w-20 px-2 py-1 border border-gray-700 bg-gray-800 text-gray-300 rounded text-sm focus:border-blue-500 focus:outline-none"
          />
          <span className="text-gray-400">{unit}</span>
        </div>
        <input
          type="range"
          min={min}
          max={max}
          value={valMin}
          onChange={(e) => handleMinMaxChange(id, 0, e.target.value)}
          className="w-full accent-blue-500"
        />
        <input
          type="range"
          min={min}
          max={max}
          value={valMax}
          onChange={(e) => handleMinMaxChange(id, 1, e.target.value)}
          className="w-full mt-1 accent-blue-500"
        />
      </div>
    );
  };

  const checkboxList = (id, items) => (
    <div className="space-y-1 max-h-36 overflow-y-auto pr-2">
      {items.map((item) => (
        <label key={item} className="flex items-center space-x-2">
          <input
            type="checkbox"
            checked={filters[id].includes(item)}
            onChange={() => handleCheckboxChange(id, item)}
            className="h-4 w-4 text-blue-500 bg-gray-800 border-gray-600 rounded focus:ring-blue-500 focus:ring-offset-gray-800"
          />
          <span className="text-sm text-gray-300">{item}</span>
        </label>
      ))}
    </div>
  );

  const filterSections = [
    {
      id: "price",
      title: "Price Range",
      content: sliderWithInputs("price", "Price", 0, 500, "$"),
    },
    {
      id: "speed",
      title: "Memory Speed",
      content: sliderWithInputs("speed", "Speed", 2400, 6400, "MHz"),
    },
    {
      id: "memoryType",
      title: "Memory Type",
      content: checkboxList("memoryTypes", memoryTypes),
    },
    {
      id: "capacity",
      title: "Capacity",
      content: checkboxList("capacities", capacities),
    },
    {
      id: "brands",
      title: "Brands",
      content: checkboxList("brands", brands),
    },
    {
      id: "features",
      title: "Features",
      content: (
        <div className="space-y-2">
          <label className="flex items-center space-x-2">
            <input
              type="checkbox"
              checked={filters.rgb}
              onChange={() => handleSingleCheckbox("rgb")}
              className="h-4 w-4 text-blue-500 bg-gray-800 border-gray-600 rounded focus:ring-blue-500 focus:ring-offset-gray-800"
            />
            <span className="text-sm text-gray-300">RGB Lighting</span>
          </label>
        </div>
      ),
    },
  ];

  return (
    <aside className="w-80 bg-slate-800 border-r border-gray-700 p-4 overflow-y-auto">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-lg font-semibold text-gray-100 flex items-center">
          <Filter className="mr-2" size={18} />
          Filter RAM
        </h2>
        <button
          onClick={() =>
            setFilters({
              ...sliderDefaults,
              memoryTypes: [],
              capacities: [],
              brands: [],
              rgb: false,
            })
          }
          className="text-sm text-blue-400 hover:text-blue-300"
        >
          Clear Filters
        </button>
      </div>

      {filterSections.map((section) => (
        <div key={section.id} className="mb-6">
          <button
            onClick={() => toggleSection(section.id)}
            className="flex justify-between w-full text-left font-medium text-gray-300"
          >
            <span>{section.title}</span>
            {expanded[section.id] ? (
              <ChevronUp size={16} />
            ) : (
              <ChevronDown size={16} />
            )}
          </button>
          {expanded[section.id] && (
            <div className="mt-2">{section.content}</div>
          )}
        </div>
      ))}

      <button
        onClick={() => onApply(filters)}
        className="w-full mt-6 bg-blue-700 text-white py-2 px-4 rounded hover:bg-blue-600 transition-colors"
      >
        Apply Filters
      </button>
    </aside>
  );
}