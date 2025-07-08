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
  });

  it('toggles filter sections when clicked', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    // Verify section content is visible
    await waitFor(() => {
      expect(screen.getByText('Cores')).toBeInTheDocument();
      expect(screen.getByText('Threads')).toBeInTheDocument();
    });
  });

  it('updates price range filters', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Find price range inputs
    const priceInputs = screen.getAllByDisplayValue('0');
    const minPriceInput = priceInputs[0];

    await user.clear(minPriceInput);
    await user.type(minPriceInput, '100');

    expect(minPriceInput.value).toBe('100');
  });

  it('handles brand filter selections', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand brands section
    const brandsButton = screen.getByText('Brands');
    await user.click(brandsButton);

    await waitFor(() => {
      const intelCheckbox = screen.getByLabelText('Intel');
      expect(intelCheckbox).toBeInTheDocument();
    });

    const intelCheckbox = screen.getByLabelText('Intel');
    await user.click(intelCheckbox);

    expect(intelCheckbox).toBeChecked();
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
  });

  it('applies filters with correct data structure', async () => {
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
        brand: expect.arrayContaining(['Intel']),
        price: expect.any(Array),
        cores: expect.any(Array),
        threads: expect.any(Array),
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

    // Clear filters
    const clearButton = screen.getByText('Clear Filters');
    await user.click(clearButton);

    await waitFor(() => {
      expect(intelCheckbox).not.toBeChecked();
    });
  });

  it('handles specifications range filters', async () => {
    const user = userEvent.setup();
    render(<CpuSidebar onApply={mockOnApply} />);

    // Expand specifications
    const specificationsButton = screen.getByText('Specifications');
    await user.click(specificationsButton);

    await waitFor(() => {
      expect(screen.getByText('Cores')).toBeInTheDocument();
    });

    // Test cores range slider (assuming it exists)
    const coresSlider = screen.getByDisplayValue('1'); // Assuming min cores value
    fireEvent.change(coresSlider, { target: { value: '8' } });

    expect(coresSlider.value).toBe('8');
  });
});