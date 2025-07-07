package com.software_project.pcbanabo.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

import com.google.genai.types.GenerationConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;

@Service
public class ChatService {

    private final String apiKey;
    private final String modelId;
    private final Client client;

    public ChatService(
            @Value("${gemini.api-key}") String apiKey,
            @Value("${gemini.model}") String modelId) {
        this.apiKey = apiKey;
        this.modelId = modelId;
        this.client = Client.builder()
                .apiKey(this.apiKey)  
                .build(); 
        System.out.println("🔍 Gemini modelId = '" + modelId + "'");
    }

    public String ask(String userMessage) {
        try {
            System.out.println("ChatService: Asking model " + modelId + " with message: " + userMessage);
            
            // Create the system prompt + user message
            String fullPrompt = "You are a helpful PCBanabo assistant specialized in PC building and hardware advice.\n\n" + userMessage;
            
            // Call the Gemini API using the client
            // GenerateContentResponse response = client.models.generateContent(modelId, fullPrompt);
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.7f)
                    .topP(0.95f)
                    .maxOutputTokens(2048)
                    .build();
            GenerateContentResponse response = client.models.generateContent(modelId, fullPrompt, config); 
            
            // Extract the response text
            String reply = response.text();
            
            System.out.println("Gemini said: " + reply);
            return reply;
        } catch (Exception e) {
            System.err.println("Error calling Gemini API: " + e.getMessage());
            e.printStackTrace();
            return "Sorry, I couldn't get a reply. Error: " + e.getMessage();
        }
    }
}