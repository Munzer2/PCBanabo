// src/main/java/com/software_project/pcbanabo/controller/ChatController.java
package com.software_project.pcbanabo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@PreAuthorize("isAuthenticated()")
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  ///Controller method to handle chat requests
  /// extracts the message using req.message() and passes it to the chatService.ask() method
  @PostMapping 
  public ResponseEntity<ChatResponse> chat(@RequestBody @Valid ChatRequest req) {
    // System.out.println("Received chat request: " + req.message());
    if(req.message() == null || req.message().isBlank()) {
      return ResponseEntity.badRequest().body(new ChatResponse("Message cannot be empty."));
    }
    if(req.message().length() > 500) {
      return ResponseEntity.badRequest().body(new ChatResponse("Message is too big"));
    }
    ///sanitize the input message
    String sanitizedMssg = HtmlUtils.htmlEscape(req.message()); //// Replaces special characters with HTML entities to prevent XSS attacks
    String reply = chatService.ask(sanitizedMssg);
    return ResponseEntity.ok(new ChatResponse(reply));
  }

  @GetMapping
  public ResponseEntity<String> healthCheck() {
    return ResponseEntity.ok("Chat API is up and running");
  }
}
