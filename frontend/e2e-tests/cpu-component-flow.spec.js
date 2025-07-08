import { test, expect } from '@playwright/test';

test.describe('CPU Component Flow - Docker Environment', () => {
  
  test.beforeEach(async ({ page }) => {
    // Mock backend API responses for consistent testing
    await page.route('**/api/components/cpus', async route => {
      const mockCpus = [
        {
          id: 1,
          name: 'Intel Core i7-12700K',
          brand: 'Intel',
          socket: 'LGA1700',
          cores: 12,
          threads: 20,
          price: 399.99,
          features: ['Integrated Graphics', 'Overclockable']
        },
        {
          id: 2,
          name: 'AMD Ryzen 7 5800X',
          brand: 'AMD',
          socket: 'AM4',
          cores: 8,
          threads: 16,
          price: 349.99,
          features: ['High Performance', 'Energy Efficient']
        },
        {
          id: 3,
          name: 'AMD Ryzen 5 3600',
          brand: 'AMD',
          socket: 'AM4',
          cores: 6,
          threads: 12,
          price: 199.99,
          features: ['Budget Friendly', 'Good Performance']
        }
      ];
      await route.fulfill({ json: mockCpus });
    });

    // Mock filtered CPU responses
    await page.route('**/api/components/cpus/filtered**', async route => {
      const url = route.request().url();
      const urlParams = new URLSearchParams(url.split('?')[1]);
      
      let filteredCpus = [
        {
          id: 1,
          name: 'Intel Core i7-12700K',
          brand: 'Intel',
          socket: 'LGA1700',
          cores: 12,
          threads: 20,
          price: 399.99
        },
        {
          id: 2,
          name: 'AMD Ryzen 7 5800X',
          brand: 'AMD',
          socket: 'AM4',
          cores: 8,
          threads: 16,
          price: 349.99
        }
      ];

      // Apply filters based on URL parameters
      if (urlParams.get('brand') === 'Intel') {
        filteredCpus = filteredCpus.filter(cpu => cpu.brand === 'Intel');
      }
      
      const priceGte = parseFloat(urlParams.get('price_gte'));
      const priceLte = parseFloat(urlParams.get('price_lte'));
      
      if (priceGte) {
        filteredCpus = filteredCpus.filter(cpu => cpu.price >= priceGte);
      }
      
      if (priceLte) {
        filteredCpus = filteredCpus.filter(cpu => cpu.price <= priceLte);
      }

      await route.fulfill({ json: filteredCpus });
    });

    // Mock authentication
    await page.route('**/auth/login', async route => {
      await route.fulfill({
        json: { 
          token: 'mock-jwt-token-e2e', 
          userId: '1',
          username: 'testuser@example.com'
        }
      });
    });

    // Mock logout
    await page.route('**/auth/logout', async route => {
      await route.fulfill({ json: { success: true } });
    });
  });

  test('should complete full authentication and CPU browsing flow', async ({ page }) => {
    // Start at the home page
    await page.goto('/');

    // Navigate to login
    await page.click('text=Login');
    await expect(page).toHaveURL('/login');

    // Fill login form
    await page.fill('input[type="email"]', 'testuser@example.com');
    await page.fill('input[type="password"]', 'password123');
    await page.click('button[type="submit"]');

    // Should be redirected to dashboard
    await expect(page).toHaveURL('/dashboard');

    // Navigate to CPU components
    await page.click('text=CPUs');
    await expect(page).toHaveURL('/components/cpu');

    // Verify CPU list is displayed
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();
    await expect(page.locator('text=AMD Ryzen 7 5800X')).toBeVisible();
    await expect(page.locator('text=AMD Ryzen 5 3600')).toBeVisible();

    // Verify price information is displayed
    await expect(page.locator('text=$399.99')).toBeVisible();
    await expect(page.locator('text=$349.99')).toBeVisible();
    await expect(page.locator('text=$199.99')).toBeVisible();
  });

  test('should filter CPUs by brand', async ({ page }) => {
    await page.goto('/components/cpu');

    // Wait for initial load
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();

    // Expand brands filter section
    await page.click('text=Brands');
    
    // Select Intel brand checkbox
    await page.check('input[type="checkbox"][value="Intel"]');
    
    // Apply filters
    await page.click('button:has-text("Apply Filters")');

    // Wait for filtered API call
    await page.waitForResponse('**/api/components/cpus/filtered?**brand=Intel**');

    // Verify only Intel CPUs are shown
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();
    
    // Verify AMD CPUs are not shown (should not be present in filtered results)
    await expect(page.locator('text=AMD Ryzen 7 5800X')).not.toBeVisible();
  });

  test('should filter CPUs by price range', async ({ page }) => {
    await page.goto('/components/cpu');

    // Wait for initial load
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();

    // Adjust price range filters
    await page.fill('input[placeholder="Min Price"]', '300');
    await page.fill('input[placeholder="Max Price"]', '400');

    // Apply filters
    await page.click('button:has-text("Apply Filters")');

    // Wait for filtered API call with price parameters
    await page.waitForResponse(response => 
      response.url().includes('/api/components/cpus/filtered') &&
      response.url().includes('price_gte=300') &&
      response.url().includes('price_lte=400')
    );

    // Verify only CPUs in price range are shown
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();
    await expect(page.locator('text=AMD Ryzen 7 5800X')).toBeVisible();
  });

  test('should filter CPUs by socket type', async ({ page }) => {
    await page.goto('/components/cpu');

    // Wait for initial load
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();

    // Expand socket filter section
    await page.click('text=Socket');
    
    // Select AM4 socket
    await page.check('input[type="checkbox"][value="AM4"]');
    
    // Apply filters
    await page.click('button:has-text("Apply Filters")');

    // Wait for filtered response
    await page.waitForResponse('**/api/components/cpus/filtered**');

    // Verify AMD CPUs with AM4 socket are shown
    await expect(page.locator('text=AMD Ryzen 7 5800X')).toBeVisible();
  });

  test('should clear all filters', async ({ page }) => {
    await page.goto('/components/cpu');

    // Set some filters first
    await page.click('text=Brands');
    await page.check('input[type="checkbox"][value="Intel"]');
    
    // Verify filter is applied
    await page.click('button:has-text("Apply Filters")');
    await page.waitForResponse('**/api/components/cpus/filtered**');

    // Clear all filters
    await page.click('button:has-text("Clear Filters")');

    // Verify all CPUs are shown again
    await page.waitForResponse('**/api/components/cpus');
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();
    await expect(page.locator('text=AMD Ryzen 7 5800X')).toBeVisible();
    await expect(page.locator('text=AMD Ryzen 5 3600')).toBeVisible();

    // Verify filters are reset
    const intelCheckbox = page.locator('input[type="checkbox"][value="Intel"]');
    await expect(intelCheckbox).not.toBeChecked();
  });

  test('should handle specifications filter', async ({ page }) => {
    await page.goto('/components/cpu');

    // Wait for initial load
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();

    // Expand specifications section
    await page.click('text=Specifications');
    
    // Set cores filter (minimum 8 cores)
    await page.fill('input[placeholder="Min Cores"]', '8');
    
    // Set threads filter (minimum 16 threads)
    await page.fill('input[placeholder="Min Threads"]', '16');

    // Apply filters
    await page.click('button:has-text("Apply Filters")');

    // Wait for filtered response
    await page.waitForResponse('**/api/components/cpus/filtered**');

    // Should show CPUs with 8+ cores and 16+ threads
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible(); // 12 cores, 20 threads
    await expect(page.locator('text=AMD Ryzen 7 5800X')).toBeVisible(); // 8 cores, 16 threads
  });

  test('should handle logout functionality', async ({ page }) => {
    await page.goto('/components/cpu');

    // Wait for page to load
    await expect(page.locator('text=Available CPUs')).toBeVisible();

    // Click logout button
    await page.click('button:has-text("Logout")');

    // Should redirect to login page
    await expect(page).toHaveURL('/login');

    // Verify user is logged out by trying to access protected route
    await page.goto('/components/cpu');
    await expect(page).toHaveURL('/login');
  });

  test('should be responsive on mobile devices', async ({ page }) => {
    // Set mobile viewport
    await page.setViewportSize({ width: 375, height: 667 });
    
    await page.goto('/components/cpu');

    // Wait for initial load
    await expect(page.locator('text=Available CPUs')).toBeVisible();

    // Verify mobile-friendly layout
    await expect(page.locator('text=Intel Core i7-12700K')).toBeVisible();
    
    // Test mobile filter menu (if implemented)
    const filterToggle = page.locator('button:has-text("Filters")');
    if (await filterToggle.isVisible()) {
      await filterToggle.click();
      await expect(page.locator('text=Brands')).toBeVisible();
    }
  });

  test('should handle network errors gracefully', async ({ page }) => {
    // Mock network error
    await page.route('**/api/components/cpus', async route => {
      await route.abort('failed');
    });

    await page.goto('/components/cpu');

    // Should show error state or loading state without crashing
    await expect(page.locator('text=Available CPUs')).toBeVisible();
    
    // Page should not crash and should handle error gracefully
    const errorMessage = page.locator('text=Error loading CPUs');
    const loadingMessage = page.locator('text=Loading...');
    
    // Either error message or loading state should be present
    await expect(errorMessage.or(loadingMessage)).toBeVisible();
  });
});