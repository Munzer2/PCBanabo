// src/main/java/com/software_project/pcbanabo/controller/ChatController.java
package com.software_project.pcbanabo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import com.software_project.pcbanabo.dto.ChatRequest;
import com.software_project.pcbanabo.dto.ChatResponse;
import com.software_project.pcbanabo.service.ChatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"})
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  ///Controller method to handle chat requests
  /// extracts the message using req.message() and passes it to the chatService.ask() method
  @PostMapping 
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<ChatResponse> chat(@RequestBody @Valid ChatRequest req) {
    System.out.println("Received chat request: " + req.message());
    System.out.println("Current page: " + req.currentPage());
    System.out.println("Build context: " + req.buildContext());
    System.out.println("User builds: " + req.userBuilds());
    System.out.println("User context: " + req.userContext());
    
    if(req.message() == null || req.message().isBlank()) {
      return ResponseEntity.badRequest().body(new ChatResponse("Message cannot be empty."));
    }
    if(req.message().length() > 500) {
      return ResponseEntity.badRequest().body(new ChatResponse("Message is too big"));
    }
    ///sanitize the input message
    String sanitizedMssg = HtmlUtils.htmlEscape(req.message()); //// Replaces special characters with HTML entities to prevent XSS attacks
    
    // Pass additional context to the service
    String reply = chatService.askWithContext(
        sanitizedMssg, 
        req.buildContext(), 
        req.userBuilds(), 
        req.currentPage(),
        req.userContext()
    );
    
    return ResponseEntity.ok(new ChatResponse(reply));
  }

  @GetMapping
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Chat API is up and running");
  }

  // New endpoint for AI build suggestions
  @PostMapping("/suggest-build")
  public ResponseEntity<String> suggestBuild(@RequestBody Map<String, String> request) {
    try {
      System.out.println("ChatController: Received build suggestion request: " + request);
      
      String budget = request.get("budget");
      String useCase = request.get("useCase");
      String preferences = request.get("preferences");
      
      // Validate budget parameter
      if (budget == null || budget.trim().isEmpty()) {
        return ResponseEntity.badRequest().body("{\"error\":\"Budget is required\"}");
      }
      
      String buildSuggestion = chatService.suggestBuild(budget, useCase, preferences);
      
      System.out.println("ChatController: Returning build suggestion: " + buildSuggestion);
      return ResponseEntity.ok()
              .header("Content-Type", "application/json")
              .body(buildSuggestion);
      
    } catch (Exception e) {
      System.err.println("ChatController: Error processing build suggestion: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(500).body("{\"error\":\"Failed to generate build suggestion\"}");
    }
  }
}
