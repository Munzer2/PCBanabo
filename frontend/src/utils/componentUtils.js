// Utility functions for sorting and filtering components

export const sortComponents = (components, sortBy) => {
  if (!components || !Array.isArray(components)) {
    return [];
  }
  
  const sorted = [...components];
  
  switch (sortBy) {
    case "name-asc":
      return sorted.sort((a, b) => {
        const nameA = (a.brand_name || "").toString();
        const nameB = (b.brand_name || "").toString();
        return nameA.localeCompare(nameB);
      });
    case "name-desc":
      return sorted.sort((a, b) => {
        const nameA = (a.brand_name || "").toString();
        const nameB = (b.brand_name || "").toString();
        return nameB.localeCompare(nameA);
      });
    case "price-asc":
      return sorted.sort((a, b) => {
        const priceA = parseFloat(a.averagePrice || a.average_price || a.avg_price || 0);
        const priceB = parseFloat(b.averagePrice || b.average_price || b.avg_price || 0);
        return priceA - priceB;
      });
    case "price-desc":
      return sorted.sort((a, b) => {
        const priceA = parseFloat(a.averagePrice || a.average_price || a.avg_price || 0);
        const priceB = parseFloat(b.averagePrice || b.average_price || b.avg_price || 0);
        return priceB - priceA;
      });
    default:
      return sorted;
  }
};

export const filterComponentsBySearch = (components, searchTerm) => {
  if (!components || !Array.isArray(components)) {
    return [];
  }
  
  if (!searchTerm || !searchTerm.trim()) {
    return components;
  }
  
  const term = searchTerm.toLowerCase().trim();
  
  return components.filter(component => {
    // Check multiple possible field names for compatibility
    const name = (component.name || component.model || component.model_name || "").toString().toLowerCase();
    const brand = (component.brand || component.brandName || component.brand_name || "").toString().toLowerCase();
    const series = (component.series || "").toString().toLowerCase();
    const sku = (component.sku || component.sku_number || "").toString().toLowerCase();
    const fullName = `${brand} ${name}`.toLowerCase();
    
    return name.includes(term) || 
           brand.includes(term) || 
           series.includes(term) || 
           sku.includes(term) ||
           fullName.includes(term);
  });
};
