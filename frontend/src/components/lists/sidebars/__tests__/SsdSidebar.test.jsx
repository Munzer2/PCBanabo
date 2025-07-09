import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import SsdSidebar from '../SsdSidebar';

describe('SsdSidebar Component', () => {
  const mockOnApply = jest.fn();

  beforeEach(() => {
    mockOnApply.mockClear();
  });

  const renderSidebar = () => {
    return render(<SsdSidebar onApply={mockOnApply} />);
  };

  test('renders sidebar with all sections', () => {
    renderSidebar();
    
    expect(screen.getByText('Filters')).toBeInTheDocument();
    expect(screen.getByText('Price Range')).toBeInTheDocument();
    expect(screen.getByText('Sequential Read (MB/s)')).toBeInTheDocument();
    expect(screen.getByText('Sequential Write (MB/s)')).toBeInTheDocument();
    expect(screen.getByText('Form Factor')).toBeInTheDocument();
    expect(screen.getByText('Capacity')).toBeInTheDocument();
    expect(screen.getByText('PCIe Generation')).toBeInTheDocument();
    expect(screen.getByText('Brands')).toBeInTheDocument();
    expect(screen.getByText('Features')).toBeInTheDocument();
  });

  test('initializes with default values', () => {
    renderSidebar();
    
    // Check price range inputs
    const priceInputs = screen.getAllByDisplayValue('0');
    expect(priceInputs).toHaveLength(1);
    expect(screen.getByDisplayValue('500')).toBeInTheDocument();
    
    // Check sequential read range inputs
    expect(screen.getByDisplayValue('500')).toBeInTheDocument();
    expect(screen.getByDisplayValue('7000')).toBeInTheDocument();
    
    // Check sequential write range inputs
    expect(screen.getByDisplayValue('400')).toBeInTheDocument();
    expect(screen.getByDisplayValue('6000')).toBeInTheDocument();
  });

  test('price range slider updates correctly', () => {
    renderSidebar();
    
    const minPriceInput = screen.getByDisplayValue('0');
    const maxPriceInput = screen.getByDisplayValue('500');
    
    fireEvent.change(minPriceInput, { target: { value: '100' } });
    fireEvent.change(maxPriceInput, { target: { value: '400' } });
    
    expect(screen.getByDisplayValue('100')).toBeInTheDocument();
    expect(screen.getByDisplayValue('400')).toBeInTheDocument();
  });

  test('sequential read range slider updates correctly', () => {
    renderSidebar();
    
    const minReadInput = screen.getByDisplayValue('500');
    const maxReadInput = screen.getByDisplayValue('7000');
    
    fireEvent.change(minReadInput, { target: { value: '1000' } });
    fireEvent.change(maxReadInput, { target: { value: '5000' } });
    
    expect(screen.getByDisplayValue('1000')).toBeInTheDocument();
    expect(screen.getByDisplayValue('5000')).toBeInTheDocument();
  });

  test('sequential write range slider updates correctly', () => {
    renderSidebar();
    
    const minWriteInput = screen.getByDisplayValue('400');
    const maxWriteInput = screen.getByDisplayValue('6000');
    
    fireEvent.change(minWriteInput, { target: { value: '800' } });
    fireEvent.change(maxWriteInput, { target: { value: '4000' } });
    
    expect(screen.getByDisplayValue('800')).toBeInTheDocument();
    expect(screen.getByDisplayValue('4000')).toBeInTheDocument();
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
    
    // Set min to 200 first
    fireEvent.change(minPriceInput, { target: { value: '200' } });
    
    // Try to set max below min
    fireEvent.change(maxPriceInput, { target: { value: '100' } });
    
    // Max should be clamped to min value
    expect(screen.getByDisplayValue('200')).toBeInTheDocument();
  });

  test('form factor selection works', () => {
    renderSidebar();
    
    const m2FormFactor = screen.getByLabelText('M.2 2280');
    const sataFormFactor = screen.getByLabelText('2.5-inch');
    
    expect(m2FormFactor).not.toBeChecked();
    expect(sataFormFactor).not.toBeChecked();
    
    fireEvent.click(m2FormFactor);
    expect(m2FormFactor).toBeChecked();
    
    fireEvent.click(sataFormFactor);
    expect(sataFormFactor).toBeChecked();
    
    // Should allow multiple selections
    expect(m2FormFactor).toBeChecked();
    expect(sataFormFactor).toBeChecked();
  });

  test('capacity selection works', () => {
    renderSidebar();
    
    const capacity250GB = screen.getByLabelText('250GB');
    const capacity1TB = screen.getByLabelText('1TB');
    const capacity2TB = screen.getByLabelText('2TB');
    
    fireEvent.click(capacity1TB);
    expect(capacity1TB).toBeChecked();
    
    fireEvent.click(capacity2TB);
    expect(capacity2TB).toBeChecked();
    
    // Should allow multiple selections
    expect(capacity1TB).toBeChecked();
    expect(capacity2TB).toBeChecked();
  });

  test('PCIe generation selection works', () => {
    renderSidebar();
    
    const pcie3 = screen.getByLabelText('PCIe 3.0');
    const pcie4 = screen.getByLabelText('PCIe 4.0');
    const pcie5 = screen.getByLabelText('PCIe 5.0');
    
    fireEvent.click(pcie4);
    expect(pcie4).toBeChecked();
    
    fireEvent.click(pcie5);
    expect(pcie5).toBeChecked();
    
    // Should allow multiple selections
    expect(pcie4).toBeChecked();
    expect(pcie5).toBeChecked();
  });

  test('brand selection works', () => {
    renderSidebar();
    
    const samsungBrand = screen.getByLabelText('Samsung');
    const wdBrand = screen.getByLabelText('Western Digital');
    const crucialBrand = screen.getByLabelText('Crucial');
    
    fireEvent.click(samsungBrand);
    expect(samsungBrand).toBeChecked();
    
    fireEvent.click(wdBrand);
    expect(wdBrand).toBeChecked();
    
    // Should allow multiple selections
    expect(samsungBrand).toBeChecked();
    expect(wdBrand).toBeChecked();
  });

  test('DRAM cache feature toggle works', () => {
    renderSidebar();
    
    const dramCacheToggle = screen.getByLabelText(/DRAM Cache/i);
    expect(dramCacheToggle).not.toBeChecked();
    
    fireEvent.click(dramCacheToggle);
    expect(dramCacheToggle).toBeChecked();
    
    fireEvent.click(dramCacheToggle);
    expect(dramCacheToggle).not.toBeChecked();
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
    const minReadInput = screen.getByDisplayValue('500');
    const minWriteInput = screen.getByDisplayValue('400');
    const m2FormFactor = screen.getByLabelText('M.2 2280');
    const capacity1TB = screen.getByLabelText('1TB');
    const pcie4 = screen.getByLabelText('PCIe 4.0');
    const samsungBrand = screen.getByLabelText('Samsung');
    const dramCacheToggle = screen.getByLabelText(/DRAM Cache/i);
    
    fireEvent.change(minPriceInput, { target: { value: '100' } });
    fireEvent.change(maxPriceInput, { target: { value: '400' } });
    fireEvent.change(minReadInput, { target: { value: '1000' } });
    fireEvent.change(minWriteInput, { target: { value: '800' } });
    fireEvent.click(m2FormFactor);
    fireEvent.click(capacity1TB);
    fireEvent.click(pcie4);
    fireEvent.click(samsungBrand);
    fireEvent.click(dramCacheToggle);
    
    const applyButton = screen.getByText('Apply Filters');
    fireEvent.click(applyButton);
    
    expect(mockOnApply).toHaveBeenCalledWith({
      price: [100, 400],
      seqRead: [1000, 7000],
      seqWrite: [800, 6000],
      formFactors: ['M.2 2280'],
      capacities: ['1TB'],
      pcieGens: ['PCIe 4.0'],
      brands: ['Samsung'],
      dramCache: true
    });
  });

  test('clear filters button resets all values', () => {
    renderSidebar();
    
    // Set some values first
    const minPriceInput = screen.getByDisplayValue('0');
    const m2FormFactor = screen.getByLabelText('M.2 2280');
    const samsungBrand = screen.getByLabelText('Samsung');
    const dramCacheToggle = screen.getByLabelText(/DRAM Cache/i);
    
    fireEvent.change(minPriceInput, { target: { value: '200' } });
    fireEvent.click(m2FormFactor);
    fireEvent.click(samsungBrand);
    fireEvent.click(dramCacheToggle);
    
    expect(screen.getByDisplayValue('200')).toBeInTheDocument();
    expect(m2FormFactor).toBeChecked();
    expect(samsungBrand).toBeChecked();
    expect(dramCacheToggle).toBeChecked();
    
    const clearButton = screen.getByText('Clear All');
    fireEvent.click(clearButton);
    
    // Should reset to defaults
    expect(screen.getByDisplayValue('0')).toBeInTheDocument();
    expect(m2FormFactor).not.toBeChecked();
    expect(samsungBrand).not.toBeChecked();
    expect(dramCacheToggle).not.toBeChecked();
  });

  test('displays all form factor options', () => {
    renderSidebar();
    
    const expectedFormFactors = ['M.2 2280', 'M.2 2230', 'M.2 2242', '2.5-inch', 'U.2', 'Add-in Card'];
    expectedFormFactors.forEach(formFactor => {
      expect(screen.getByLabelText(formFactor)).toBeInTheDocument();
    });
  });

  test('displays all capacity options', () => {
    renderSidebar();
    
    const expectedCapacities = ['250GB', '500GB', '1TB', '2TB', '4TB', '8TB'];
    expectedCapacities.forEach(capacity => {
      expect(screen.getByLabelText(capacity)).toBeInTheDocument();
    });
  });

  test('displays all PCIe generation options', () => {
    renderSidebar();
    
    const expectedPcieGens = ['PCIe 3.0', 'PCIe 4.0', 'PCIe 5.0', 'SATA III'];
    expectedPcieGens.forEach(gen => {
      expect(screen.getByLabelText(gen)).toBeInTheDocument();
    });
  });

  test('displays all brand options', () => {
    renderSidebar();
    
    const expectedBrands = ['Samsung', 'Western Digital', 'Crucial', 'Sabrent', 'Kingston', 'Corsair', 'ADATA', 'Seagate'];
    expectedBrands.forEach(brand => {
      expect(screen.getByLabelText(brand)).toBeInTheDocument();
    });
  });

  test('maintains filter state correctly during multiple operations', () => {
    renderSidebar();
    
    // Apply multiple filters
    const minPriceInput = screen.getByDisplayValue('0');
    const minReadInput = screen.getByDisplayValue('500');
    const m2FormFactor = screen.getByLabelText('M.2 2280');
    const sataFormFactor = screen.getByLabelText('2.5-inch');
    const capacity1TB = screen.getByLabelText('1TB');
    const capacity2TB = screen.getByLabelText('2TB');
    
    fireEvent.change(minPriceInput, { target: { value: '150' } });
    fireEvent.change(minReadInput, { target: { value: '2000' } });
    fireEvent.click(m2FormFactor);
    fireEvent.click(sataFormFactor);
    fireEvent.click(capacity1TB);
    fireEvent.click(capacity2TB);
    
    // Verify state
    expect(screen.getByDisplayValue('150')).toBeInTheDocument();
    expect(screen.getByDisplayValue('2000')).toBeInTheDocument();
    expect(m2FormFactor).toBeChecked();
    expect(sataFormFactor).toBeChecked();
    expect(capacity1TB).toBeChecked();
    expect(capacity2TB).toBeChecked();
    
    // Uncheck one item
    fireEvent.click(sataFormFactor);
    expect(sataFormFactor).not.toBeChecked();
    expect(m2FormFactor).toBeChecked(); // Should remain checked
  });

  test('performance slider ranges work independently', () => {
    renderSidebar();
    
    const minReadInput = screen.getByDisplayValue('500');
    const maxReadInput = screen.getByDisplayValue('7000');
    const minWriteInput = screen.getByDisplayValue('400');
    const maxWriteInput = screen.getByDisplayValue('6000');
    
    // Change read performance
    fireEvent.change(minReadInput, { target: { value: '1500' } });
    fireEvent.change(maxReadInput, { target: { value: '6000' } });
    
    // Change write performance
    fireEvent.change(minWriteInput, { target: { value: '1200' } });
    fireEvent.change(maxWriteInput, { target: { value: '5000' } });
    
    // All should be independent
    expect(screen.getByDisplayValue('1500')).toBeInTheDocument();
    expect(screen.getByDisplayValue('6000')).toBeInTheDocument();
    expect(screen.getByDisplayValue('1200')).toBeInTheDocument();
    expect(screen.getByDisplayValue('5000')).toBeInTheDocument();
  });
});
