// Template code to add to all component list pages

// 1. Add to imports:
import { useMemo } from "react"; // if not already imported
import SearchAndSort from "../../components/common/SearchAndSort";
import { sortComponents, filterComponentsBySearch } from "../../utils/componentUtils";

// 2. Add to state variables:
const [searchTerm, setSearchTerm] = useState("");
const [sortBy, setSortBy] = useState("name-asc");

// 3. Add before return statement:
// Filter and sort components based on search term and sort option
const filteredAndSortedComponents = useMemo(() => {
  const filtered = filterComponentsBySearch(components, searchTerm);
  return sortComponents(filtered, sortBy);
}, [components, searchTerm, sortBy]);

// 4. Add SearchAndSort component after the title:
<SearchAndSort
  searchTerm={searchTerm}
  onSearchChange={setSearchTerm}
  sortBy={sortBy}
  onSortChange={setSortBy}
  placeholder="Search components by name, brand, or series..."
/>

// 5. Update the conditional rendering:
{filteredAndSortedComponents.length === 0 ? (
  <p className="text-center text-gray-400">
    {components.length === 0 ? "No components found." : "No components match your search."}
  </p>
) : (
  <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
    {filteredAndSortedComponents.map((component) => (
      // existing component mapping
    ))}
  </div>
)}
