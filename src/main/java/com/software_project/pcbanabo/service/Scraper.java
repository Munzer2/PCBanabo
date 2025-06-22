package com.software_project.pcbanabo.service;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;

import com.software_project.pcbanabo.model.Cpu;

public class Scraper {
    private final WebDriver webDriver;
    private final CpuService cpuService; 


    public Scraper(WebDriver webDriver, CpuService cpuService) {
        this.webDriver = webDriver;
        this.cpuService = cpuService;
    }

    @Scheduled(fixedDelayString="${scrape.interval.ms:36000000}") // Default is 10 hours
    public void scrape() {
        scrapeCpus(); 
    }

    private void scrapeCpus() {
        webDriver.get("https://www.startech.com.bd/component/processor");
        new WebDriverWait(webDriver, Duration.ofSeconds(10))
        .until(ExpectedConditions
            .presenceOfElementLocated(By.cssSelector(".product-listing .product-item")));
            List<WebElement> items = webDriver.findElements(By.cssSelector(".product-listing .product-item"));
            for(WebElement e : items ) {
                Cpu cpu = new Cpu();
                cpu.setBrand_name(e.findElement(By.cssSelector(".product-brand")).getText());
                String priceText = e.findElement(By.cssSelector(".product-price")).getText().replaceAll("[^\\d.]", "");
                double price = priceText.isEmpty() ? 0.0 : Double.parseDouble(priceText);
                cpu.setAverage_price(price);
                cpuService.save(cpu);
            }

    }
}
