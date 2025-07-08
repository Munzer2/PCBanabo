import { test, expect } from '@playwright/test';

test.describe('Authentication Flow - Docker Environment', () => {
  
  test.beforeEach(async ({ page }) => {
    // Mock authentication endpoints
    await page.route('**/auth/login', async route => {
      const request = route.request();
      const postData = JSON.parse(request.postData());
      
      if (postData.email === 'testuser@example.com' && postData.password === 'password123') {
        await route.fulfill({
          json: { 
            token: 'mock-jwt-token', 
            userId: '1',
            username: 'testuser@example.com'
          }
        });
      } else {
        await route.fulfill({
          status: 401,
          json: { error: 'Invalid credentials' }
        });
      }
    });

    await page.route('**/auth/register', async route => {
      await route.fulfill({
        json: { 
          message: 'Registration successful',
          userId: '2'
        }
      });
    });
  });

  test('should login with valid credentials', async ({ page }) => {
    await page.goto('/login');

    await page.fill('input[type="email"]', 'testuser@example.com');
    await page.fill('input[type="password"]', 'password123');
    await page.click('button[type="submit"]');

    // Should redirect to dashboard
    await expect(page).toHaveURL('/dashboard');
    
    // Should store token in localStorage
    const token = await page.evaluate(() => localStorage.getItem('token'));
    expect(token).toBe('mock-jwt-token');
  });

  test('should show error with invalid credentials', async ({ page }) => {
    await page.goto('/login');

    await page.fill('input[type="email"]', 'invalid@example.com');
    await page.fill('input[type="password"]', 'wrongpassword');
    await page.click('button[type="submit"]');

    // Should show error message
    await expect(page.locator('text=Invalid credentials')).toBeVisible();
    
    // Should remain on login page
    await expect(page).toHaveURL('/login');
  });

  test('should register new user', async ({ page }) => {
    await page.goto('/register');

    await page.fill('input[name="firstName"]', 'Test');
    await page.fill('input[name="lastName"]', 'User');
    await page.fill('input[type="email"]', 'newuser@example.com');
    await page.fill('input[type="password"]', 'newpassword123');
    await page.fill('input[name="confirmPassword"]', 'newpassword123');
    await page.click('button[type="submit"]');

    // Should show success message
    await expect(page.locator('text=Registration successful')).toBeVisible();
  });

  test('should logout user', async ({ page }) => {
    // Login first
    await page.goto('/login');
    await page.fill('input[type="email"]', 'testuser@example.com');
    await page.fill('input[type="password"]', 'password123');
    await page.click('button[type="submit"]');
    
    await expect(page).toHaveURL('/dashboard');

    // Logout
    await page.click('button:has-text("Logout")');
    
    // Should redirect to login
    await expect(page).toHaveURL('/login');
    
    // Token should be cleared
    const token = await page.evaluate(() => localStorage.getItem('token'));
    expect(token).toBeNull();
  });
});