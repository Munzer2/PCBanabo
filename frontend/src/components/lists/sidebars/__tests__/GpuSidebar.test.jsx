import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import GpuSidebar from '../GpuSidebar';

describe('GpuSidebar', () => {
  const mockOnApply = vi.fn();

  beforeEach(() => {
    mockOnApply.mockClear();
  });

  it('renders GPU sidebar with all filter sections', () => {
    render(<GpuSidebar onApply={mockOnApply} />);

    expect(screen.getByText('Filter Graphics Cards')).toBeInTheDocument();
    expect(screen.getByText('Price Range')).toBeInTheDocument();
    expect(screen.getByText('Specifications')).toBeInTheDocument();
    expect(screen.getByText('Brands')).toBeInTheDocument();
    expect(screen.getByText('GPU Core')).toBeInTheDocument();
    expect(screen.getByText('Features')).toBeInTheDocument();
  });

  it('initializes with correct default slider values', () => {
    render(<GpuSidebar onApply={mockOnApply} />);

    // Check price range defaults (0-2500)
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('2500');
    
    expect(priceMinInput).toBeInTheDocument();
    expect(priceMaxInput).toBeInTheDocument();

    // Check VRAM range defaults (2-32)
    expect(screen.getByDisplayValue('2')).toBeInTheDocument();
    expect(screen.getByDisplayValue('32')).toBeInTheDocument();

    // Check TDP range defaults (50-500)
    expect(screen.getByDisplayValue('50')).toBeInTheDocument();
    expect(screen.getByDisplayValue('500')).toBeInTheDocument();

    // Check card length range defaults (100-400)
    expect(screen.getByDisplayValue('100')).toBeInTheDocument();
    expect(screen.getByDisplayValue('400')).toBeInTheDocument();
  });

  it('toggles filter sections when clicked', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

  it('toggles filter sections when clicked', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    const specificationsButton = screen.getByText('Specifications');
    
    // Specifications should be expanded by default
    expect(screen.getByText('VRAM')).toBeInTheDocument();
    
    // Click to collapse
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.queryByText('VRAM')).not.toBeInTheDocument();
    });

    // Click to expand again
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('VRAM')).toBeInTheDocument();
      expect(screen.getByText('TDP')).toBeInTheDocument();
      expect(screen.getByText('Card Length')).toBeInTheDocument();
    });
  });

  it('updates price range filters correctly', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('2500');

    await user.clear(priceMinInput);
    await user.type(priceMinInput, '500');

    await user.clear(priceMaxInput);
    await user.type(priceMaxInput, '1500');

    expect(priceMinInput.value).toBe('500');
    expect(priceMaxInput.value).toBe('1500');
  });

  it('handles VRAM range filters correctly', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Expand specifications section if needed
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('VRAM')).toBeInTheDocument();
    });

    // Find VRAM range inputs (should be 2 and 32 by default)
    const vramMinInput = screen.getByDisplayValue('2');
    const vramMaxInput = screen.getByDisplayValue('32');

    await user.clear(vramMinInput);
    await user.type(vramMinInput, '8');

    await user.clear(vramMaxInput);
    await user.type(vramMaxInput, '16');

    expect(vramMinInput.value).toBe('8');
    expect(vramMaxInput.value).toBe('16');
  });

  it('handles brand filter selections', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Expand brands section
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
      const amdCheckbox = screen.getByLabelText('AMD');
      expect(nvidiaCheckbox).toBeInTheDocument();
      expect(amdCheckbox).toBeInTheDocument();
    });

    const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
    await user.click(nvidiaCheckbox);

    expect(nvidiaCheckbox).toBeChecked();

    // Test multiple selections
    const amdCheckbox = screen.getByLabelText('AMD');
    await user.click(amdCheckbox);

    expect(nvidiaCheckbox).toBeChecked();
    expect(amdCheckbox).toBeChecked();
  });

  it('handles GPU core filter selections', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Expand GPU core section
    const gpuCoreButton = screen.getByText('GPU Core');
    await user.click(gpuCoreButton);

    await waitFor(() => {
      const rtx4080Checkbox = screen.getByLabelText('RTX 4080');
      expect(rtx4080Checkbox).toBeInTheDocument();
    });

    const rtx4080Checkbox = screen.getByLabelText('RTX 4080');
    await user.click(rtx4080Checkbox);

    expect(rtx4080Checkbox).toBeChecked();
  });

  it('handles boolean feature filters', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Expand features section
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const rayTracingCheckbox = screen.getByLabelText('Ray Tracing');
      const dlssCheckbox = screen.getByLabelText('DLSS');
      expect(rayTracingCheckbox).toBeInTheDocument();
      expect(dlssCheckbox).toBeInTheDocument();
    });

    const rayTracingCheckbox = screen.getByLabelText('Ray Tracing');
    const dlssCheckbox = screen.getByLabelText('DLSS');

    await user.click(rayTracingCheckbox);
    await user.click(dlssCheckbox);

    expect(rayTracingCheckbox).toBeChecked();
    expect(dlssCheckbox).toBeChecked();
  });

  it('applies filters with correct data structure including all slider defaults', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Set some filters
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
      expect(nvidiaCheckbox).toBeInTheDocument();
    });

    const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
    await user.click(nvidiaCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledTimes(1);
    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        // Should include all slider defaults
        price: [0, 2500],
        vram: [2, 32],
        tdp: [50, 500],
        cardLength: [100, 400],
        cardHeight: [20, 200],
        cardThickness: [20, 100],
        // Array filters
        brands: ['NVIDIA'],
        gpuCore: [],
        // Boolean filters
        rayTracing: false,
        dlss: false,
      })
    );
  });

  it('applies filters with modified slider values', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Modify price range
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('2500');

    await user.clear(priceMinInput);
    await user.type(priceMinInput, '800');

    await user.clear(priceMaxInput);
    await user.type(priceMaxInput, '1200');

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        price: [800, 1200], // Modified values
        vram: [2, 32], // Default values
        tdp: [50, 500], // Default values
      })
    );
  });

  it('clears all filters when Clear Filters is clicked', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Set some filters first
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
      expect(nvidiaCheckbox).toBeInTheDocument();
    });

    const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
    await user.click(nvidiaCheckbox);

    expect(nvidiaCheckbox).toBeChecked();

    // Modify price
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '500');

    // Clear filters
    const clearButton = screen.getByText('Clear Filters');
    await user.click(clearButton);

    await waitFor(() => {
      expect(nvidiaCheckbox).not.toBeChecked();
      // Price should be reset to default
      expect(screen.getAllByDisplayValue('0')[0]).toBeInTheDocument();
      expect(screen.getByDisplayValue('2500')).toBeInTheDocument();
    });
  });

  it('enforces min <= max constraint on slider inputs', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('2500');

    // Try to set min higher than max
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '3000');

    // The max should be adjusted to match min
    expect(priceMinInput.value).toBe('3000');
    expect(priceMaxInput.value).toBe('3000');
  });

  it('handles TDP and card length range filters correctly', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Expand specifications section
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('TDP')).toBeInTheDocument();
      expect(screen.getByText('Card Length')).toBeInTheDocument();
    });

    // Find TDP range inputs (should be 50 and 500 by default)
    const tdpMinInput = screen.getByDisplayValue('50');
    const tdpMaxInput = screen.getByDisplayValue('500');

    await user.clear(tdpMinInput);
    await user.type(tdpMinInput, '200');

    await user.clear(tdpMaxInput);
    await user.type(tdpMaxInput, '350');

    expect(tdpMinInput.value).toBe('200');
    expect(tdpMaxInput.value).toBe('350');

    // Test card length
    const cardLengthMinInput = screen.getByDisplayValue('100');
    const cardLengthMaxInput = screen.getByDisplayValue('400');

    await user.clear(cardLengthMinInput);
    await user.type(cardLengthMinInput, '250');

    expect(cardLengthMinInput.value).toBe('250');
  });

  it('maintains filter state correctly across multiple interactions', async () => {
    const user = userEvent.setup();
    render(<GpuSidebar onApply={mockOnApply} />);

    // Set brand filter
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
      expect(nvidiaCheckbox).toBeInTheDocument();
    });

    const nvidiaCheckbox = screen.getByLabelText('NVIDIA');
    await user.click(nvidiaCheckbox);

    // Set price filter
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '600');

    // Set features
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const rayTracingCheckbox = screen.getByLabelText('Ray Tracing');
      expect(rayTracingCheckbox).toBeInTheDocument();
    });

    const rayTracingCheckbox = screen.getByLabelText('Ray Tracing');
    await user.click(rayTracingCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        brands: ['NVIDIA'],
        price: [600, 2500],
        rayTracing: true,
        dlss: false,
      })
    );
  });
});
