import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import CpuSidebar from '../CpuSidebar';

describe('CpuSidebar', () => {
  const mockOnApply = vi.fn();

  beforeEach(() => {
    mockOnApply.mockClear();
  });

  it('renders CPU sidebar with all filter sections', () => {
    render(<CpuSidebar onApply={mockOnApply} />);

    expect(screen.getByText('Filter CPUs')).toBeInTheDocument();
    expect(screen.getByText('Average Price')).toBeInTheDocument();
    expect(screen.getByText('Specifications')).toBeInTheDocument();
    expect(screen.getByText('Brands')).toBeInTheDocument();
    expect(screen.getByText('Socket')).toBeInTheDocument();
    expect(screen.getByText('Series')).toBeInTheDocument();
    expect(screen.getByText('Features')).toBeInTheDocument();
  });

  it('initializes with correct default slider values', () => {
    render(<CpuSidebar onApply={mockOnApply} />);

    // Check price range defaults (0-1500)
    const priceInputs = screen.getAllByDisplayValue('0');
    expect(priceInputs.length).toBeGreaterThan(0);
    
    const maxPriceInput = screen.getByDisplayValue('1500');
    expect(maxPriceInput).toBeInTheDocument();

    // Check cores range defaults (1-64)
    expect(screen.getByDisplayValue('1')).toBeInTheDocument();
    expect(screen.getByDisplayValue('64')).toBeInTheDocument();

    // Check threads range defaults (2-128)
    expect(screen.getByDisplayValue('2')).toBeInTheDocument();
    expect(screen.getByDisplayValue('128')).toBeInTheDocument();

    // Check TDP range defaults (10-300)
    expect(screen.getByDisplayValue('10')).toBeInTheDocument();
    expect(screen.getByDisplayValue('300')).toBeInTheDocument();
  });

  it('toggles filter sections when clicked', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    const specificationsButton = screen.getByText('Specifications');
    
    // Specifications should be expanded by default
    expect(screen.getByText('Cores')).toBeInTheDocument();
    
    // Click to collapse
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.queryByText('Cores')).not.toBeInTheDocument();
    });

    // Click to expand again
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('Cores')).toBeInTheDocument();
      expect(screen.getByText('Threads')).toBeInTheDocument();
      expect(screen.getByText('Base Clock')).toBeInTheDocument();
      expect(screen.getByText('Boost Clock')).toBeInTheDocument();
      expect(screen.getByText('TDP')).toBeInTheDocument();
    });
  });

  it('updates price range filters correctly', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Find the price section inputs
    const priceMinInput = screen.getAllByDisplayValue('0')[0]; // First occurrence should be price min
    const priceMaxInput = screen.getByDisplayValue('1500');

    await user.clear(priceMinInput);
    await user.type(priceMinInput, '100');

    await user.clear(priceMaxInput);
    await user.type(priceMaxInput, '800');

    expect(priceMinInput.value).toBe('100');
    expect(priceMaxInput.value).toBe('800');
  });

  it('handles core range filters correctly', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand specifications section if needed
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      const coresSection = screen.getByText('Cores');
      expect(coresSection).toBeInTheDocument();
    });

    // Find cores range inputs (should be 1 and 64 by default)
    const coreMinInput = screen.getByDisplayValue('1');
    const coreMaxInput = screen.getByDisplayValue('64');

    await user.clear(coreMinInput);
    await user.type(coreMinInput, '4');

    await user.clear(coreMaxInput);
    await user.type(coreMaxInput, '16');

    expect(coreMinInput.value).toBe('4');
    expect(coreMaxInput.value).toBe('16');
  });

  it('handles brand filter selections', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand brands section
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const intelCheckbox = screen.getByLabelText('Intel');
      const amdCheckbox = screen.getByLabelText('AMD');
      expect(intelCheckbox).toBeInTheDocument();
      expect(amdCheckbox).toBeInTheDocument();
    });

    const intelCheckbox = screen.getByLabelText('Intel');
    await user.click(intelCheckbox);

    expect(intelCheckbox).toBeChecked();

    // Test multiple selections
    const amdCheckbox = screen.getByLabelText('AMD');
    await user.click(amdCheckbox);

    expect(intelCheckbox).toBeChecked();
    expect(amdCheckbox).toBeChecked();
  });

  it('handles socket filter selections', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand socket section
    const socketButton = screen.getByText('Socket');
    await user.click(socketButton);

    await waitFor(() => {
      const lga1700Checkbox = screen.getByLabelText('LGA1700');
      expect(lga1700Checkbox).toBeInTheDocument();
    });

    const lga1700Checkbox = screen.getByLabelText('LGA1700');
    await user.click(lga1700Checkbox);

    expect(lga1700Checkbox).toBeChecked();

    // Test AM4 socket as well
    const am4Checkbox = screen.getByLabelText('AM4');
    await user.click(am4Checkbox);

    expect(am4Checkbox).toBeChecked();
  });

  it('handles series filter selections', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand series section
    const seriesButton = screen.getByText('Series');
    await user.click(seriesButton);

    await waitFor(() => {
      const coreI7Checkbox = screen.getByLabelText('Core i7');
      expect(coreI7Checkbox).toBeInTheDocument();
    });

    const coreI7Checkbox = screen.getByLabelText('Core i7');
    await user.click(coreI7Checkbox);

    expect(coreI7Checkbox).toBeChecked();
  });

  it('handles boolean feature filters', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand features section
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const integratedGraphicsCheckbox = screen.getByLabelText('Integrated Graphics');
      const unlockedCheckbox = screen.getByLabelText('Unlocked');
      expect(integratedGraphicsCheckbox).toBeInTheDocument();
      expect(unlockedCheckbox).toBeInTheDocument();
    });

    const integratedGraphicsCheckbox = screen.getByLabelText('Integrated Graphics');
    const unlockedCheckbox = screen.getByLabelText('Unlocked');

    await user.click(integratedGraphicsCheckbox);
    await user.click(unlockedCheckbox);

    expect(integratedGraphicsCheckbox).toBeChecked();
    expect(unlockedCheckbox).toBeChecked();
  });

  it('applies filters with correct data structure including all slider defaults', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Set some filters
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const intelCheckbox = screen.getByLabelText('Intel');
      expect(intelCheckbox).toBeInTheDocument();
    });

    const intelCheckbox = screen.getByLabelText('Intel');
    await user.click(intelCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledTimes(1);
    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        // Should include all slider defaults
        price: [0, 1500],
        cores: [1, 64],
        threads: [2, 128],
        baseClock: [1, 5],
        boostClock: [1, 6],
        tdp: [10, 300],
        // Array filters
        brands: ['Intel'],
        socket: [],
        series: [],
        // Boolean filters
        integratedGraphics: false,
        unlocked: false,
      })
    );
  });

  it('applies filters with modified slider values', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Modify price range
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('1500');

    await user.clear(priceMinInput);
    await user.type(priceMinInput, '200');

    await user.clear(priceMaxInput);
    await user.type(priceMaxInput, '800');

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        price: [200, 800], // Modified values
        cores: [1, 64], // Default values
        threads: [2, 128], // Default values
      })
    );
  });

  it('clears all filters when Clear Filters is clicked', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Set some filters first
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const intelCheckbox = screen.getByLabelText('Intel');
      expect(intelCheckbox).toBeInTheDocument();
    });

    const intelCheckbox = screen.getByLabelText('Intel');
    await user.click(intelCheckbox);

    expect(intelCheckbox).toBeChecked();

    // Modify price
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '100');

    // Clear filters
    const clearButton = screen.getByText('Clear Filters');
    await user.click(clearButton);

    await waitFor(() => {
      expect(intelCheckbox).not.toBeChecked();
      // Price should be reset to default
      expect(screen.getAllByDisplayValue('0')[0]).toBeInTheDocument();
      expect(screen.getByDisplayValue('1500')).toBeInTheDocument();
    });
  });

  it('enforces min <= max constraint on slider inputs', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('1500');

    // Try to set min higher than max
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '2000');

    // The max should be adjusted to match min
    expect(priceMinInput.value).toBe('2000');
    expect(priceMaxInput.value).toBe('2000');
  });

  it('updates range sliders when input values change', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '300');

    // Find the corresponding range slider
    const priceSliders = screen.getAllByRole('slider');
    const minSlider = priceSliders.find(slider => slider.value === '300');
    
    expect(minSlider).toBeInTheDocument();
  });

  it('maintains filter state correctly across multiple interactions', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Set brand filter
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const intelCheckbox = screen.getByLabelText('Intel');
      expect(intelCheckbox).toBeInTheDocument();
    });

    const intelCheckbox = screen.getByLabelText('Intel');
    await user.click(intelCheckbox);

    // Set price filter
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '100');

    // Set features
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const unlockedCheckbox = screen.getByLabelText('Unlocked');
      expect(unlockedCheckbox).toBeInTheDocument();
    });

    const unlockedCheckbox = screen.getByLabelText('Unlocked');
    await user.click(unlockedCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        brands: ['Intel'],
        price: [100, 1500],
        unlocked: true,
        integratedGraphics: false,
      })
    );
  });

  it('handles TDP filter range correctly', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand specifications section
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('TDP')).toBeInTheDocument();
    });

    // Find TDP range inputs (should be 10 and 300 by default)
    const tdpMinInput = screen.getByDisplayValue('10');
    const tdpMaxInput = screen.getByDisplayValue('300');

    await user.clear(tdpMinInput);
    await user.type(tdpMinInput, '50');

    await user.clear(tdpMaxInput);
    await user.type(tdpMaxInput, '150');

    expect(tdpMinInput.value).toBe('50');
    expect(tdpMaxInput.value).toBe('150');

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        tdp: [50, 150],
      })
    );
  });

  it('handles base clock and boost clock filters correctly', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand specifications section
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('Base Clock')).toBeInTheDocument();
      expect(screen.getByText('Boost Clock')).toBeInTheDocument();
    });

    // Test base clock filter
    const baseClockMinInput = screen.getAllByDisplayValue('1').find(input => 
      input.closest('.mb-4')?.querySelector('label')?.textContent === 'Base Clock'
    );
    
    if (baseClockMinInput) {
      await user.clear(baseClockMinInput);
      await user.type(baseClockMinInput, '2');
      expect(baseClockMinInput.value).toBe('2');
    }

    // Apply filters and verify base clock is included
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        baseClock: expect.any(Array),
        boostClock: expect.any(Array),
      })
    );
  });
});