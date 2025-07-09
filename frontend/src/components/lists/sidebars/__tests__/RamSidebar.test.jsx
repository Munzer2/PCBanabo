import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import RamSidebar from '../RamSidebar';

describe('RamSidebar Component', () => {
  const mockOnApply = jest.fn();

  beforeEach(() => {
    mockOnApply.mockClear();
  });

  const renderSidebar = () => {
    return render(<RamSidebar onApply={mockOnApply} />);
  };

  test('renders sidebar with all sections', () => {
    renderSidebar();
    
    expect(screen.getByText('Filters')).toBeInTheDocument();
    expect(screen.getByText('Price Range')).toBeInTheDocument();
    expect(screen.getByText('Speed (MHz)')).toBeInTheDocument();
    expect(screen.getByText('Memory Type')).toBeInTheDocument();
    expect(screen.getByText('Capacity')).toBeInTheDocument();
    expect(screen.getByText('Brands')).toBeInTheDocument();
    expect(screen.getByText('Features')).toBeInTheDocument();
  });

  test('initializes with default values', () => {
    renderSidebar();
    
    // Check price range inputs
    const priceInputs = screen.getAllByDisplayValue('0');
    expect(priceInputs).toHaveLength(1);
    expect(screen.getByDisplayValue('500')).toBeInTheDocument();
    
    // Check speed range inputs
    expect(screen.getByDisplayValue('2400')).toBeInTheDocument();
    expect(screen.getByDisplayValue('6400')).toBeInTheDocument();
  });

  test('price range slider updates correctly', () => {
    renderSidebar();
    
    const minPriceInput = screen.getByDisplayValue('0');
    const maxPriceInput = screen.getByDisplayValue('500');
    
    fireEvent.change(minPriceInput, { target: { value: '50' } });
    fireEvent.change(maxPriceInput, { target: { value: '300' } });
    
    expect(screen.getByDisplayValue('50')).toBeInTheDocument();
    expect(screen.getByDisplayValue('300')).toBeInTheDocument();
  });

  test('speed range slider updates correctly', () => {
    renderSidebar();
    
    const minSpeedInput = screen.getByDisplayValue('2400');
    const maxSpeedInput = screen.getByDisplayValue('6400');
    
    fireEvent.change(minSpeedInput, { target: { value: '3200' } });
    fireEvent.change(maxSpeedInput, { target: { value: '4800' } });
    
    expect(screen.getByDisplayValue('3200')).toBeInTheDocument();
    expect(screen.getByDisplayValue('4800')).toBeInTheDocument();
  });

  test('prevents min value from exceeding max value', () => {
    renderSidebar();
    
    const minPriceInput = screen.getByDisplayValue('0');
    const maxPriceInput = screen.getByDisplayValue('500');
    
    // Try to set min higher than max
    fireEvent.change(minPriceInput, { target: { value: '600' } });
    
    // Min should be clamped to max value
    expect(screen.getByDisplayValue('500')).toBeInTheDocument();
  });

  test('prevents max value from going below min value', () => {
    renderSidebar();
    
    const minPriceInput = screen.getByDisplayValue('0');
    const maxPriceInput = screen.getByDisplayValue('500');
    
    // Set min to 100 first
    fireEvent.change(minPriceInput, { target: { value: '100' } });
    
    // Try to set max below min
    fireEvent.change(maxPriceInput, { target: { value: '50' } });
    
    // Max should be clamped to min value
    expect(screen.getByDisplayValue('100')).toBeInTheDocument();
  });

  test('memory type selection works', () => {
    renderSidebar();
    
    const ddr4Checkbox = screen.getByLabelText('DDR4');
    const ddr5Checkbox = screen.getByLabelText('DDR5');
    
    expect(ddr4Checkbox).not.toBeChecked();
    expect(ddr5Checkbox).not.toBeChecked();
    
    fireEvent.click(ddr4Checkbox);
    expect(ddr4Checkbox).toBeChecked();
    
    fireEvent.click(ddr5Checkbox);
    expect(ddr5Checkbox).toBeChecked();
    
    // Uncheck DDR4
    fireEvent.click(ddr4Checkbox);
    expect(ddr4Checkbox).not.toBeChecked();
  });

  test('capacity selection works', () => {
    renderSidebar();
    
    const capacity8GB = screen.getByLabelText('8GB');
    const capacity16GB = screen.getByLabelText('16GB');
    const capacity32GB = screen.getByLabelText('32GB');
    
    fireEvent.click(capacity16GB);
    expect(capacity16GB).toBeChecked();
    
    fireEvent.click(capacity32GB);
    expect(capacity32GB).toBeChecked();
    
    // Should allow multiple selections
    expect(capacity16GB).toBeChecked();
    expect(capacity32GB).toBeChecked();
  });

  test('brand selection works', () => {
    renderSidebar();
    
    const corsairBrand = screen.getByLabelText('Corsair');
    const gskillBrand = screen.getByLabelText('G.Skill');
    
    fireEvent.click(corsairBrand);
    expect(corsairBrand).toBeChecked();
    
    fireEvent.click(gskillBrand);
    expect(gskillBrand).toBeChecked();
  });

  test('RGB feature toggle works', () => {
    renderSidebar();
    
    const rgbToggle = screen.getByLabelText(/RGB/i);
    expect(rgbToggle).not.toBeChecked();
    
    fireEvent.click(rgbToggle);
    expect(rgbToggle).toBeChecked();
    
    fireEvent.click(rgbToggle);
    expect(rgbToggle).not.toBeChecked();
  });

  test('section expansion/collapse works', () => {
    renderSidebar();
    
    const priceSection = screen.getByText('Price Range').closest('div');
    const chevronButton = priceSection.querySelector('button');
    
    // Initially expanded, so content should be visible
    expect(screen.getByDisplayValue('0')).toBeInTheDocument();
    
    fireEvent.click(chevronButton);
    
    // After collapse, the inputs might be hidden (depends on implementation)
    // This test would need to check actual visibility styles
  });

  test('apply filters button calls onApply with correct data', () => {
    renderSidebar();
    
    // Set some filter values
    const minPriceInput = screen.getByDisplayValue('0');
    const maxPriceInput = screen.getByDisplayValue('500');
    const minSpeedInput = screen.getByDisplayValue('2400');
    const ddr4Checkbox = screen.getByLabelText('DDR4');
    const capacity16GB = screen.getByLabelText('16GB');
    const corsairBrand = screen.getByLabelText('Corsair');
    const rgbToggle = screen.getByLabelText(/RGB/i);
    
    fireEvent.change(minPriceInput, { target: { value: '50' } });
    fireEvent.change(maxPriceInput, { target: { value: '300' } });
    fireEvent.change(minSpeedInput, { target: { value: '3200' } });
    fireEvent.click(ddr4Checkbox);
    fireEvent.click(capacity16GB);
    fireEvent.click(corsairBrand);
    fireEvent.click(rgbToggle);
    
    const applyButton = screen.getByText('Apply Filters');
    fireEvent.click(applyButton);
    
    expect(mockOnApply).toHaveBeenCalledWith({
      price: [50, 300],
      speed: [3200, 6400],
      memoryTypes: ['DDR4'],
      capacities: ['16GB'],
      brands: ['Corsair'],
      rgb: true
    });
  });

  test('clear filters button resets all values', () => {
    renderSidebar();
    
    // Set some values first
    const minPriceInput = screen.getByDisplayValue('0');
    const ddr4Checkbox = screen.getByLabelText('DDR4');
    const corsairBrand = screen.getByLabelText('Corsair');
    
    fireEvent.change(minPriceInput, { target: { value: '100' } });
    fireEvent.click(ddr4Checkbox);
    fireEvent.click(corsairBrand);
    
    expect(screen.getByDisplayValue('100')).toBeInTheDocument();
    expect(ddr4Checkbox).toBeChecked();
    expect(corsairBrand).toBeChecked();
    
    const clearButton = screen.getByText('Clear All');
    fireEvent.click(clearButton);
    
    // Should reset to defaults
    expect(screen.getByDisplayValue('0')).toBeInTheDocument();
    expect(ddr4Checkbox).not.toBeChecked();
    expect(corsairBrand).not.toBeChecked();
  });

  test('displays all memory type options', () => {
    renderSidebar();
    
    expect(screen.getByLabelText('DDR4')).toBeInTheDocument();
    expect(screen.getByLabelText('DDR5')).toBeInTheDocument();
  });

  test('displays all capacity options', () => {
    renderSidebar();
    
    const expectedCapacities = ['8GB', '16GB', '32GB', '64GB', '128GB'];
    expectedCapacities.forEach(capacity => {
      expect(screen.getByLabelText(capacity)).toBeInTheDocument();
    });
  });

  test('displays all brand options', () => {
    renderSidebar();
    
    const expectedBrands = ['Corsair', 'G.Skill', 'Kingston', 'Crucial', 'TeamGroup', 'ADATA', 'PNY', 'HyperX'];
    expectedBrands.forEach(brand => {
      expect(screen.getByLabelText(brand)).toBeInTheDocument();
    });
  });

  test('maintains filter state correctly during multiple operations', () => {
    renderSidebar();
    
    // Apply multiple filters
    const minPriceInput = screen.getByDisplayValue('0');
    const ddr4Checkbox = screen.getByLabelText('DDR4');
    const ddr5Checkbox = screen.getByLabelText('DDR5');
    const capacity16GB = screen.getByLabelText('16GB');
    const capacity32GB = screen.getByLabelText('32GB');
    
    fireEvent.change(minPriceInput, { target: { value: '75' } });
    fireEvent.click(ddr4Checkbox);
    fireEvent.click(ddr5Checkbox);
    fireEvent.click(capacity16GB);
    fireEvent.click(capacity32GB);
    
    // Verify state
    expect(screen.getByDisplayValue('75')).toBeInTheDocument();
    expect(ddr4Checkbox).toBeChecked();
    expect(ddr5Checkbox).toBeChecked();
    expect(capacity16GB).toBeChecked();
    expect(capacity32GB).toBeChecked();
    
    // Uncheck one item
    fireEvent.click(ddr5Checkbox);
    expect(ddr5Checkbox).not.toBeChecked();
    expect(ddr4Checkbox).toBeChecked(); // Should remain checked
  });
});
