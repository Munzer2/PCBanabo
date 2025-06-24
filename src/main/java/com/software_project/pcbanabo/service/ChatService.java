// src/main/java/com/software_project/pcbanabo/service/ChatService.java
package com.software_project.pcbanabo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.software_project.pcbanabo.dto.OpenRouterResponse;

@Service
public class ChatService {

  private final WebClient client;
  private final String modelId;

  public ChatService(WebClient openRouterClient,
                     @Value("${openrouter.model}") String modelId) {
    this.client = openRouterClient;
    this.modelId = modelId;
  }

  public String ask(String userMessage) {
    // build request payload
    // System.out.println("ChatService: Asking model " + modelId + " with message: " + userMessage);
    Map<String,Object> payload = Map.of(
      "model", modelId,
      "messages", List.of(
        Map.of("role", "system", "content", "You are a helpful PCBanabo assistant."),
        Map.of("role", "user",   "content", userMessage)
      )
    );

    OpenRouterResponse resp = client.post()
      .uri("/chat/completions")
      .bodyValue(payload)
      .retrieve()
      .bodyToMono(OpenRouterResponse.class)
      .block();

    if (resp == null || resp.choices().isEmpty())
      return "Sorry, I couldn't get a reply.";

    // System.out.println("GPT said :  " + resp.choices().get(0).message().content());
    return resp.choices().get(0).message().content();
  }
}
