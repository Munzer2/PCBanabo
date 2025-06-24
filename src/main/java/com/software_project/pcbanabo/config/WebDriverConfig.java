package com.software_project.pcbanabo.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.bonigarcia.wdm.WebDriverManager;

@Configuration
public class WebDriverConfig {

    @Bean
    public WebDriver webDriver() {
        String chromeBinary = System.getenv("CHROME_BINARY_PATH");
        if (chromeBinary == null || chromeBinary.isEmpty())
        {
            WebDriverManager.chromedriver().driverVersion("137.0.7151.69").setup();
        }

        ChromeOptions opts = new ChromeOptions();
        if (chromeBinary != null && !chromeBinary.isEmpty())
        {
            opts.setBinary(chromeBinary);
        }

        opts.addArguments("--headless", "--disable-gpu", "--no-sandbox","--window-size=1920,1080");
        // opts.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080", "--remote-debugging-port=9222");
        return new ChromeDriver(opts);
    }
}
