package com.software_project.pcbanabo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                            "http://localhost", 
                            "http://localhost:3000", 
                            "http://localhost:3001", 
                            "http://localhost:5173", // Vite default port
                            "http://127.0.0.1:3000",
                            "http://127.0.0.1:5173",
                            "http://127.14.0.8", 
                            "http://98.70.34.216"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600); // Cache preflight for 1 hour
            }
        };
    }
}
