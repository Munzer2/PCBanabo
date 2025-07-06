package com.software_project.pcbanabo.service;

import java.time.Duration;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.Cpu;

@Service
public class CpuScraper {
    private final WebDriver webDriver;
    private final CpuService cpuService;
    private final String baseUrl = "https://www.startech.com.bd/component/processor";

    public CpuScraper(WebDriver webDriver, CpuService cpuService) {
        this.webDriver = webDriver;
        this.cpuService = cpuService;
    }

    // @Scheduled(fixedDelayString = "${scrape.interval.ms:36000000}") // Default is 10 hours
    public void scrape() {
        scrapeCpus();
    }

    public void scrapeCpus() {
        webDriver.get(baseUrl);
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10)); 
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("ul.pagination li a")));
        List<WebElement> paginationLinks = webDriver.findElements(
                By.cssSelector("ul.pagination li a"));

        int lastPage = 1;
        for (WebElement link : paginationLinks) {
            String txt = link.getText().trim();
            try {
                int p = Integer.parseInt(txt);
                lastPage = Math.max(lastPage, p);
            } catch (NumberFormatException e) {
                // Ignore non-numeric pagination links
            }
        }

        for (int pg = 1; pg <= lastPage; pg++) {
            String pageUrl = baseUrl + "?page=" + pg;
            webDriver.get(pageUrl);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".p-item")));
            List<String> urls = webDriver.findElements(
                    By.cssSelector("h4.p-item-name > a"))
                    .stream()
                    .map(e -> e.getAttribute("href"))
                    .distinct()
                    .toList();

            for (String url : urls) {
                try {
                    Cpu cpu = scrapeCpuDetail(url,wait);
                    if (cpuService.getCpuByModelName(cpu.getModel_name()).isEmpty()) {
                        cpuService.insertCpu(cpu);
                        System.out.println("Saved: " + cpu.getModel_name());
                    } else {
                        Cpu existingCpu = cpuService.getCpuByModelName(cpu.getModel_name()).get();
                        existingCpu.setAverage_price(cpu.getAverage_price());
                        existingCpu.setOfficial_product_url(cpu.getOfficial_product_url());
                        existingCpu.setSku_number(cpu.getSku_number());
                        existingCpu.setSocket(cpu.getSocket());
                        existingCpu.setCache_size(cpu.getCache_size());
                        existingCpu.setTdp(cpu.getTdp());

                        cpuService.updateCpu(existingCpu, existingCpu.getId());
                        System.out.println("Updated: " + existingCpu.getModel_name());
                    }
                } catch (Exception e) {
                    System.err.println("Failed to scrape " + url + ": " + e.getMessage());
                }
            }
        }
    }

    private Cpu scrapeCpuDetail(String url, WebDriverWait wait) {
        webDriver.get(url);
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("div.pd-summary")));

        Cpu cpu = new Cpu();
        cpu.setOfficial_product_url(url);

        String sku = webDriver.findElement(
                By.cssSelector("meta[itemprop='sku']")).getAttribute("content");
        cpu.setSku_number(sku);

        WebElement nameEl = webDriver.findElement(
                By.cssSelector("h1.product-name[itemprop='name']"));
        String fullName = nameEl.getText().trim();
        String[] parts = fullName.split("\\s+", 2);
        cpu.setBrand_name(parts[0]);
        cpu.setModel_name(parts.length > 1 ? parts[1] : fullName);

        String priceStr = webDriver.findElement(
                By.cssSelector("meta[itemprop='price']")).getAttribute("content");
        cpu.setAverage_price(Double.parseDouble(priceStr));

        for (WebElement li : webDriver.findElements(
                By.cssSelector("div.short-description ul li"))) {
            String txt = li.getText().trim();
            if (txt.toLowerCase().startsWith("socket")) {
                cpu.setSocket(txt.replaceAll("(?i)socket supported\\s*", ""));
                break;
            }
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("table.data-table")));
        Map<String, String> specs = new HashMap<>();
        for (WebElement row : webDriver.findElements(
                By.cssSelector("table.data-table tr"))) {
            List<WebElement> key = row.findElements(By.cssSelector("td.name"));
            List<WebElement> val = row.findElements(By.cssSelector("td.value"));
            if (key.isEmpty() || val.isEmpty())
                continue;
            specs.put(key.get(0).getText().trim(), val.get(0).getText().trim());
        }
        String _cache = specs.getOrDefault("Cache", "");
        Pattern p = Pattern.compile("(\\d+)\\s*(KB|MB|GB)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(_cache);
        long maxInKB = -1;
        String best = "";
        while (m.find()) {
            long value = Long.parseLong(m.group(1));
            String unit = m.group(2).toUpperCase();
            long inKB;
            inKB = switch (unit) {
                case "GB" -> value * 1024 * 1024;
                case "MB" -> value * 1024;
                default -> value;
            };
            if (inKB > maxInKB) {
                maxInKB = inKB;
                best = value + " " + unit; // remember the human‐friendly form
            }
        }
        cpu.setCache_size(best);
        cpu.setTdp(Integer.parseInt(
                specs.getOrDefault("Default TDP", "0").replaceAll("[^0-9]", "")));
        // cpu.setOverclockable(
        // "Yes".equalsIgnoreCase(
        // specs.getOrDefault("Overclockable","No"))
        // );

        return cpu;
    }

}
