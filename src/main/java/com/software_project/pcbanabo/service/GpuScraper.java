package com.software_project.pcbanabo.service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.Gpu;

@Service
public class GpuScraper {
    private final WebDriver driver;
    private final GpuService gpuService;
    private final String baseUrl = "https://www.startech.com.bd/component/graphics-card";

    public GpuScraper(WebDriver webDriver, GpuService gpuService) {
        this.driver = webDriver;
        this.gpuService = gpuService;
    }

    // @Scheduled(fixedDelayString = "${scrape.interval.ms:36000000}") // Default is 10 hours
    public void scrape() {
        scrapeGpus();
    }

    public void scrapeGpus() {
        driver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1) figure out how many pages there are
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("ul.pagination li a")));
        int lastPage = driver.findElements(
                By.cssSelector("ul.pagination li a")).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .mapToInt(txt -> {
                    try {
                        return Integer.parseInt(txt);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                }).max().orElse(1);

        // 2) loop through each page
        for (int pg = 1; pg <= lastPage; pg++) {
            driver.get(baseUrl + "?page=" + pg);
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.cssSelector(".p-item")));

            // collect all detail URLs
            List<String> urls = driver.findElements(
                    By.cssSelector("h4.p-item-name > a"))
                    .stream()
                    .map(el -> el.getAttribute("href"))
                    .distinct()
                    .collect(Collectors.toList());

            // scrape each GPU
            for (String url : urls) {
                try {
                    Gpu gpu = scrapeGpuDetail(url, wait);

                    Optional<Gpu> existing = gpuService.getGpuByModelName(gpu.getModel_name());
                    if (existing.isPresent()) {
                        Gpu e = existing.get();
                        // copy over changed fields...
                        System.out.println("Updating existing GPU: " + gpu.getModel_name());
                        e.setAvg_price(gpu.getAvg_price());
                        e.setOfficial_product_url(gpu.getOfficial_product_url());
                        e.setGpu_core(gpu.getGpu_core());
                        e.setVram(gpu.getVram());
                        e.setTdp(gpu.getTdp());
                        e.setPower_connector_type(gpu.getPower_connector_type());
                        e.setPower_connector_count(gpu.getPower_connector_count());
                        e.setCardLength(gpu.getCardLength());
                        e.setCard_height(gpu.getCard_height());
                        e.setCard_thickness(gpu.getCard_thickness());
                        e.setRgb(gpu.getRgb());
                        gpuService.updateGpu(e, e.getId());
                    } else {
                        System.out.println("Saving new GPU: " + gpu.getModel_name());
                        gpuService.insertGpu(gpu);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to scrape " + url + ": " + e.getMessage());
                }
            }
        }
    }

    private Gpu scrapeGpuDetail(String url, WebDriverWait wait) {
        driver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.pd-summary")));

        Gpu gpu = new Gpu();
        gpu.setOfficial_product_url(url);
        String core = "N/A";
        // Key Features (model, core clock, memory size)
        List<WebElement> features = driver.findElements(
                By.cssSelector("div.short-description ul li"));
        for (WebElement li : features) {
            String txt = li.getText().trim();
            if (txt.toLowerCase().contains("model")) {
                gpu.setModel_name(
                        txt.replaceFirst("(?i)model:\\s*", "")
                                .trim());
            } else if (txt.toLowerCase().startsWith("core clock:")) {
                core = txt.replaceFirst("(?i)core clock\\s*[:\\-]?\\s*", "").trim();
            }
        }
        gpu.setGpu_core(core);


        // Brand
        WebElement brandEl = driver.findElement(
                By.cssSelector("td.product-info-data.product-brand[itemprop='name']"));
        gpu.setBrand_name(brandEl.getText().trim());

        // Price
        String priceContent = driver.findElement(
                By.cssSelector("meta[itemprop='price']"))
                .getAttribute("content");
        gpu.setAvg_price(Double.parseDouble(priceContent));

        // Full spec table
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("table.data-table.flex-table")));
        System.out.println("Processing row: " + gpu.getModel_name());
        Map<String, String> specMap = new HashMap<>();
        for (WebElement row : driver.findElements(
                By.cssSelector("table.data-table.flex-table tr"))) {
            List<WebElement> nameTd = row.findElements(By.cssSelector("td.name"));
            List<WebElement> valueTd = row.findElements(By.cssSelector("td.value"));
            System.out.println("Row: " + row.getText());
            if (nameTd.isEmpty() || valueTd.isEmpty())
                continue;
            specMap.put(nameTd.get(0).getText().trim(),
                    valueTd.get(0).getText().trim());
        }

        String dim = specMap.getOrDefault("Dimensions", "");
        // System.out.println("Card dimensions: " + dim);
        dim = dim.replaceAll("(?i)\\s*mm\\s*$", "");
        String[] parts = dim.split("x");
        int length = 0, width = 0, height = 0;
        if (parts.length >= 1) {
            try {
                length = Integer.parseInt(parts[0].trim());
            } catch (NumberFormatException ignored) {
            }
        }
        if (parts.length >= 2) {
            try {
                width = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException ignored) {
            }
        }
        if (parts.length >= 3) {
            try {
                height = Integer.parseInt(parts[2].trim());
            } catch (NumberFormatException ignored) {
            }
        }
        gpu.setCardLength(length);
        gpu.setCard_height(width);
        gpu.setCard_thickness(height);

        gpu.setVram(
                Integer.parseInt(
                        specMap.getOrDefault("Size", "0")
                                .replaceAll("[^0-9]", "")));
        // RGB
        gpu.setRgb(
                specMap.getOrDefault("RGB", "No")
                        .equalsIgnoreCase("Yes"));

        gpu.setPower_connector_type(
                specMap.getOrDefault("Power Connector Type", "N/A"));
        gpu.setPower_connector_count(
                Integer.parseInt(
                        specMap.getOrDefault("Power Connector Count", "0")
                                .replaceAll("[^0-9]", "")));

        gpu.setTdp(
                Integer.parseInt(
                        specMap.getOrDefault("Default TDP", "0")
                                .replaceAll("[^0-9]", ""))
        );
        return gpu;
    }
}
