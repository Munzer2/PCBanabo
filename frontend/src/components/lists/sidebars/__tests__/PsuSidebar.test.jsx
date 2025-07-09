import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import PsuSidebar from '../PsuSidebar';

describe('PsuSidebar', () => {
  const mockOnApply = vi.fn();

  beforeEach(() => {
    mockOnApply.mockClear();
  });

  it('renders PSU sidebar with all filter sections', () => {
    render(<PsuSidebar onApply={mockOnApply} />);

    expect(screen.getByText('Filter Power Supplies')).toBeInTheDocument();
    expect(screen.getByText('Price Range')).toBeInTheDocument();
    expect(screen.getByText('Specifications')).toBeInTheDocument();
    expect(screen.getByText('Form Factor')).toBeInTheDocument();
    expect(screen.getByText('Certification')).toBeInTheDocument();
    expect(screen.getByText('Brands')).toBeInTheDocument();
    expect(screen.getByText('Features')).toBeInTheDocument();
  });

  it('initializes with correct default slider values', () => {
    render(<PsuSidebar onApply={mockOnApply} />);

    // Check price range defaults (0-500)
    const priceMinInput = screen.getAllByDisplayValue('0')[0];
    const priceMaxInput = screen.getByDisplayValue('500');
    
    expect(priceMinInput).toBeInTheDocument();
    expect(priceMaxInput).toBeInTheDocument();

    // Check wattage defaults (300-1600)
    expect(screen.getByDisplayValue('300')).toBeInTheDocument();
    expect(screen.getByDisplayValue('1600')).toBeInTheDocument();
  });

  it('toggles filter sections when clicked', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    const specificationsButton = screen.getByText('Specifications');
    
    // Specifications should be expanded by default
    expect(screen.getByText('Wattage')).toBeInTheDocument();
    
    // Click to collapse
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.queryByText('Wattage')).not.toBeInTheDocument();
    });

    // Click to expand again
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('Wattage')).toBeInTheDocument();
      expect(screen.getByText('PSU Length')).toBeInTheDocument();
    });
  });

  it('updates price range filters correctly', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getByDisplayValue('0');
    const priceMaxInput = screen.getByDisplayValue('500');

    await user.clear(priceMinInput);
    await user.type(priceMinInput, '100');

    await user.clear(priceMaxInput);
    await user.type(priceMaxInput, '300');

    expect(priceMinInput.value).toBe('100');
    expect(priceMaxInput.value).toBe('300');
  });

  it('handles wattage range filters correctly', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Wattage inputs should be visible by default
    const wattageMinInput = screen.getByDisplayValue('300');
    const wattageMaxInput = screen.getByDisplayValue('1600');

    await user.clear(wattageMinInput);
    await user.type(wattageMinInput, '650');

    await user.clear(wattageMaxInput);
    await user.type(wattageMaxInput, '1000');

    expect(wattageMinInput.value).toBe('650');
    expect(wattageMaxInput.value).toBe('1000');
  });

  it('handles form factor filter selections', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Expand form factor section
    const formFactorButton = screen.getByText('Form Factor');
    await user.click(formFactorButton);

    await waitFor(() => {
      const atxCheckbox = screen.getByLabelText('ATX');
      const sfxCheckbox = screen.getByLabelText('SFX');
      expect(atxCheckbox).toBeInTheDocument();
      expect(sfxCheckbox).toBeInTheDocument();
    });

    const atxCheckbox = screen.getByLabelText('ATX');
    await user.click(atxCheckbox);

    expect(atxCheckbox).toBeChecked();

    // Test multiple selections
    const sfxCheckbox = screen.getByLabelText('SFX');
    await user.click(sfxCheckbox);

    expect(atxCheckbox).toBeChecked();
    expect(sfxCheckbox).toBeChecked();
  });

  it('handles certification filter selections', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Expand certification section
    const certificationButton = screen.getByText('Certification');
    await user.click(certificationButton);

    await waitFor(() => {
      const goldCheckbox = screen.getByLabelText('80+ Gold');
      expect(goldCheckbox).toBeInTheDocument();
    });

    const goldCheckbox = screen.getByLabelText('80+ Gold');
    await user.click(goldCheckbox);

    expect(goldCheckbox).toBeChecked();

    // Test another certification
    const platinumCheckbox = screen.getByLabelText('80+ Platinum');
    await user.click(platinumCheckbox);

    expect(goldCheckbox).toBeChecked();
    expect(platinumCheckbox).toBeChecked();
  });

  it('handles brand filter selections', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Expand brands section
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const corsairCheckbox = screen.getByLabelText('Corsair');
      const seasonicCheckbox = screen.getByLabelText('Seasonic');
      expect(corsairCheckbox).toBeInTheDocument();
      expect(seasonicCheckbox).toBeInTheDocument();
    });

    const corsairCheckbox = screen.getByLabelText('Corsair');
    await user.click(corsairCheckbox);

    expect(corsairCheckbox).toBeChecked();
  });

  it('handles RGB feature filter', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Expand features section
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const rgbCheckbox = screen.getByLabelText('RGB Lighting');
      expect(rgbCheckbox).toBeInTheDocument();
    });

    const rgbCheckbox = screen.getByLabelText('RGB Lighting');
    await user.click(rgbCheckbox);

    expect(rgbCheckbox).toBeChecked();
  });

  it('logs debug information when Apply Filters is clicked', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(consoleSpy).toHaveBeenCalledWith(
      'PSU Sidebar: Apply Filters clicked with filters:',
      expect.any(Object)
    );
    
    expect(mockOnApply).toHaveBeenCalledTimes(1);
  });

  it('applies filters with correct data structure including all slider defaults', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Set some filters
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const corsairCheckbox = screen.getByLabelText('Corsair');
      expect(corsairCheckbox).toBeInTheDocument();
    });

    const corsairCheckbox = screen.getByLabelText('Corsair');
    await user.click(corsairCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledTimes(1);
    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        // Should include all slider defaults
        price: [0, 500],
        wattage: [300, 1600],
        psuLength: [100, 200],
        // Array filters
        brands: ['Corsair'],
        formFactors: [],
        certifications: [],
        // Boolean filters
        rgb: false,
      })
    );
  });

  it('applies filters with modified slider values', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Modify wattage range
    const wattageMinInput = screen.getByDisplayValue('300');
    const wattageMaxInput = screen.getByDisplayValue('1600');

    await user.clear(wattageMinInput);
    await user.type(wattageMinInput, '750');

    await user.clear(wattageMaxInput);
    await user.type(wattageMaxInput, '850');

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        price: [0, 500], // Default values
        wattage: [750, 850], // Modified values
        psuLength: [100, 200], // Default values
      })
    );
  });

  it('clears all filters when Clear Filters is clicked', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Set some filters first
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const corsairCheckbox = screen.getByLabelText('Corsair');
      expect(corsairCheckbox).toBeInTheDocument();
    });

    const corsairCheckbox = screen.getByLabelText('Corsair');
    await user.click(corsairCheckbox);

    expect(corsairCheckbox).toBeChecked();

    // Modify wattage
    const wattageMinInput = screen.getByDisplayValue('300');
    await user.clear(wattageMinInput);
    await user.type(wattageMinInput, '650');

    // Clear filters
    const clearButton = screen.getByText('Clear Filters');
    await user.click(clearButton);

    await waitFor(() => {
      expect(corsairCheckbox).not.toBeChecked();
      // Wattage should be reset to default
      expect(screen.getByDisplayValue('300')).toBeInTheDocument();
      expect(screen.getByDisplayValue('1600')).toBeInTheDocument();
    });
  });

  it('enforces min <= max constraint on slider inputs', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    const priceMinInput = screen.getByDisplayValue('0');
    const priceMaxInput = screen.getByDisplayValue('500');

    // Try to set min higher than max
    await user.clear(priceMinInput);
    await user.type(priceMinInput, '600');

    // The max should be adjusted to match min
    expect(priceMinInput.value).toBe('600');
    expect(priceMaxInput.value).toBe('600');
  });

  it('maintains filter state correctly across multiple interactions', async () => {
    const user = userEvent.setup();
    render(<PsuSidebar onApply={mockOnApply} />);

    // Set brand filter
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const corsairCheckbox = screen.getByLabelText('Corsair');
      expect(corsairCheckbox).toBeInTheDocument();
    });

    const corsairCheckbox = screen.getByLabelText('Corsair');
    await user.click(corsairCheckbox);

    // Set wattage filter
    const wattageMinInput = screen.getByDisplayValue('300');
    await user.clear(wattageMinInput);
    await user.type(wattageMinInput, '750');

    // Set RGB
    const featuresButton = screen.getByText('Features');
    await user.click(featuresButton);

    await waitFor(() => {
      const rgbCheckbox = screen.getByLabelText('RGB Lighting');
      expect(rgbCheckbox).toBeInTheDocument();
    });

    const rgbCheckbox = screen.getByLabelText('RGB Lighting');
    await user.click(rgbCheckbox);

    // Apply filters
    const applyButton = screen.getByText('Apply Filters');
    await user.click(applyButton);

    expect(mockOnApply).toHaveBeenCalledWith(
      expect.objectContaining({
        brands: ['Corsair'],
        wattage: [750, 1600],
        rgb: true,
      })
    );
  });
});
