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
        WebDriverManager.chromedriver().driverVersion("137.0.7151.69").setup();

        // 2. Now create your headless Chrome instance
        ChromeOptions opts = new ChromeOptions();
        opts.addArguments("--headless", "--disable-gpu", "--no-sandbox");
        return new ChromeDriver(opts);
    }
}
