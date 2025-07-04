package com.software_project.pcbanabo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

///Usecase : non blocking HTTP client for OpenRouter API
/// can send requests to OpenRouter API endpoints to generate text completions, chat completions, etc.
/// Handle responses in a reactive manner
/// Inject the OpenRouter API key into all requests automatically
@Configuration
public class OpenRouterConfig {

    @Value("${openrouter.url}")
    private String baseUrl;

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Bean
    public WebClient openRouterClient(WebClient.Builder builder) { 
        return builder
                .baseUrl(baseUrl)
                // .defaultHeader("X-API-KEY", apiKey)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("HTTP-Referer", "http://localhost:8080")
                .defaultHeader("x-openai-compatibility", "true")
                .build();
    }
}
