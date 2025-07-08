package com.software_project.pcbanabo.service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.Casing;

@Service
public class CasingScraper {
    private final WebDriver webDriver;
    private final CasingService casingService;
    private final String baseUrl = "https://www.startech.com.bd/component/casing";


    public CasingScraper(WebDriver webDriver, CasingService casingService) {
        this.webDriver = webDriver;
        this.casingService = casingService;
    }

    // @Scheduled(fixedDelayString = "${scrape.interval.ms:36000000}") // Default is 10 hours
    public void scrape() {
        scrapeCasings();
    }

    public void scrapeCasings() {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        
        // First wait for products to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".p-item")));
        
        int lastPage = 1;
        
        try {
            // Find pagination
            List<WebElement> pages = webDriver.findElements(By.cssSelector("ul.pagination li"));
            for (WebElement link : pages) {
                String txt = link.getText().trim();
                try {
                    int pageNum = Integer.parseInt(txt);
                    lastPage = Math.max(lastPage, pageNum);
                } catch (NumberFormatException e) {
                    // Ignore non-numeric text
                }
            }
        } catch (Exception e) {
            System.out.println("Error finding pagination: " + e.getMessage());
            System.out.println("Will process page 1 only");
        }
        
        // Process each page
        for (int pg = 1; pg <= lastPage; pg++) {
            try {
                String pageUrl = baseUrl + "?page=" + pg;
                System.out.println("Processing page " + pg + " of " + lastPage + ": " + pageUrl);
                
                webDriver.get(pageUrl);
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".p-item")));
                
                // Extract all product URLs first before processing
                List<String> productUrls = new ArrayList<>();
                List<WebElement> products = webDriver.findElements(By.cssSelector(".p-item"));
                
                for (WebElement product : products) {
                    try {
                        WebElement link = product.findElement(By.cssSelector("h4.p-item-name > a"));
                        String url = link.getAttribute("href");
                        productUrls.add(url);
                    } catch (Exception e) {
                        System.err.println("Error extracting product URL: " + e.getMessage());
                    }
                }
                
                // Process each URL separately
                for (String url : productUrls) {
                    try {
                        Casing casing = scrapeCasingDetail(url, wait);
                        
                        if (casingService.getCasingByModelName(casing.getModel_name()).isEmpty()) {
                            casingService.insertCasing(casing);
                            System.out.println("Saved new casing: " + casing.getModel_name());
                        } else {
                            Casing existingCasing = casingService.getCasingByModelName(casing.getModel_name()).get();
                            existingCasing.setAvg_price(casing.getAvg_price());
                            existingCasing.setMotherboardSupport(casing.getMotherboardSupport());
                            existingCasing.setColor(casing.getColor());
                            existingCasing.setRgb(casing.isRgb());
                            existingCasing.setDisplay(casing.isDisplay());
                            existingCasing.setPsuClearance(casing.getPsuClearance());
                            existingCasing.setGpuClearance(casing.getGpuClearance());
                            existingCasing.setCpuClearance(casing.getCpuClearance());
                            existingCasing.setTop_rad_support(casing.getTop_rad_support());
                            existingCasing.setBottom_rad_support(casing.getBottom_rad_support());
                            existingCasing.setSide_rad_support(casing.getSide_rad_support());
                            existingCasing.setIncluded_fan(casing.getIncluded_fan());
                            casingService.updateCasing(existingCasing, existingCasing.getId());
                            System.out.println("Updated existing casing: " + existingCasing.getModel_name());
                        }
                    } catch (Exception e) {
                        System.err.println("Failed to scrape " + url + ": " + e.getMessage());
                    }
                }
            } catch (Exception e) {
                System.err.println("Error processing page " + pg + ": " + e.getMessage());
            }
        }
    }

    private Casing scrapeCasingDetail(String url, WebDriverWait wait) {
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.pd-summary")));

        Casing casing = new Casing();
        casing.setOfficial_product_url(url);
        String fullname = webDriver.findElement(By.cssSelector("h1.product-name")).getText().trim();
        String[] nameParts = fullname.split("\\s+", 2);
        casing.setBrand_name(nameParts[0]);
        casing.setModel_name(nameParts.length > 1 ? nameParts[1] : fullname);

        try {
            WebElement priceEl = webDriver.findElement(By.cssSelector("td.product-info-data.product-regular-price"));
            String priceText = priceEl.getText().replaceAll("[^0-9]",
                    ""); /// this will remove all non-numeric characters
            casing.setAvg_price(Double.parseDouble(priceText));
        } catch (Exception e) {
            System.err.println("Price not found for " + casing.getModel_name() + ": " + e.getMessage());
            casing.setAvg_price(0.0);
        }

        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("table.data-table")));
            Map<String, String> specs = extractSpecifications();
            setSpecifications(casing, specs, fullname);
        } catch (Exception e) {
            System.err.println("Data table not found for " + casing.getModel_name() + ": " + e.getMessage());
        }

        return casing;
    }

    private Map<String, String> extractSpecifications() {
        Map<String, String> specs = new HashMap<>();
        try {
            // Get all table rows
            List<WebElement> rows = webDriver.findElements(By.cssSelector("table.data-table tr"));
            
            for (WebElement row : rows) {
                try {
                    // Look for td elements with name and value classes
                    List<WebElement> nameCells = row.findElements(By.cssSelector("td.name"));
                    List<WebElement> valueCells = row.findElements(By.cssSelector("td.value"));
                    
                    // Only process if we found both cells
                    if (!nameCells.isEmpty() && !valueCells.isEmpty()) {
                        String name = nameCells.get(0).getText().trim();
                        String value = valueCells.get(0).getText().trim();
                        
                        if (!name.isEmpty() && !value.isEmpty()) {
                            specs.put(name, value);
                        }
                    }
                } catch (Exception e) {
                    // Skip this row and continue with the next one
                    continue;
                }
            }
            if (specs.isEmpty()) {
                System.out.println("Nothing...");
            }
        } catch (Exception e) {
            System.err.println("Failed to extract specifications: " + e.getMessage());
        }
        
        return specs;
    }

    private void setSpecifications(Casing casing, Map<String, String> spec, String fullname) {
        String mbSup = spec.getOrDefault("Motherboard Support", "Unknown");
        casing.setMotherboardSupport(mbSup);

        String color = spec.getOrDefault("Color(s)", spec.getOrDefault("Color", "Unknown"));
        casing.setColor(color);

        casing.setCpuClearance(extractMeasurement(spec.getOrDefault("Maximum CPU Cooler Height", "0")));
        casing.setGpuClearance(extractMeasurement(spec.getOrDefault("Maximum Graphics Card Length", "0")));
        casing.setPsuClearance(extractMeasurement(spec.getOrDefault("PSU", "0")));

        casing.setBottom_rad_support(spec.getOrDefault("Bottom Radiator Support", ""));
        casing.setSide_rad_support(spec.getOrDefault("Side Radiator Support", ""));
        casing.setTop_rad_support(spec.getOrDefault("Top Radiator Support", ""));

        casing.setIncluded_fan(extractFanCount(spec.getOrDefault("Pre- Installed Fans", "0")));

        String features = fullname.toLowerCase();
        casing.setRgb(
                features.contains("rgb") || spec.getOrDefault("Additional Feature", "").toLowerCase().contains("rgb"));
        casing.setDisplay(features.contains("lcd") || features.contains("display") ||
                spec.getOrDefault("Additional Feature", "").toLowerCase().contains("display"));

    }

    // Add this method to extract fan count
    private int extractFanCount(String text) {
        // Try to find patterns like "3 fans", "3x 120mm fans"
        Pattern pattern = Pattern.compile("(\\d+)\\s*(?:x\\s*\\d+\\s*mm)?\\s*fan", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                // Ignore parsing errors
            }
        }

        // If we can't find a specific pattern, try to extract any number
        pattern = Pattern.compile("(\\d+)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                // Ignore
            }
        }

        return 0; // Default to 0 if no number found
    }

    private int extractMeasurement(String text) {
        Pattern pattern = Pattern.compile("(\\d+)\\s*(?:mm|cm|m)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        // Try just finding any number
        pattern = Pattern.compile("(\\d+)");
        matcher = pattern.matcher(text);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        return 0;
    }

}