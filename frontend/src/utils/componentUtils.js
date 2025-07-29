// Utility functions for sorting and filtering components

export const sortComponents = (components, sortBy) => {
  const sorted = [...components];
  
  switch (sortBy) {
    case "name-asc":
      return sorted.sort((a, b) => (a.name || a.model || "").localeCompare(b.name || b.model || ""));
    case "name-desc":
      return sorted.sort((a, b) => (b.name || b.model || "").localeCompare(a.name || a.model || ""));
    case "price-asc":
      return sorted.sort((a, b) => (a.averagePrice || 0) - (b.averagePrice || 0));
    case "price-desc":
      return sorted.sort((a, b) => (b.averagePrice || 0) - (a.averagePrice || 0));
    default:
      return sorted;
  }
};

export const filterComponentsBySearch = (components, searchTerm) => {
  if (!searchTerm.trim()) return components;
  
  const term = searchTerm.toLowerCase();
  return components.filter(component => {
    const name = (component.name || component.model || "").toLowerCase();
    const brand = (component.brand || component.brandName || "").toLowerCase();
    const series = (component.series || "").toLowerCase();
    
    return name.includes(term) || brand.includes(term) || series.includes(term);
  });
};
