package com.software_project.pcbanabo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

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

    public String askWithContext(String userMessage, Map<String, Object> buildContext, List<Map<String, Object>> userBuilds, String currentPage, Map<String, Object> userContext) {
        try {
            System.out.println("ChatService: Asking with context - Page: " + currentPage);
            System.out.println("User context: " + userContext);
            System.out.println("Build context: " + buildContext);
            
            // Build a comprehensive context prompt
            StringBuilder contextPrompt = new StringBuilder();
            contextPrompt.append("You are PCBanabo Assistant, an expert PC building consultant. ");
            contextPrompt.append("Help users with PC building, component compatibility, performance optimization, and troubleshooting.\n\n");
            
            // Add user context if available
            if (userContext != null && !userContext.isEmpty()) {
                String userName = (String) userContext.get("userName");
                if (userName != null) {
                    contextPrompt.append("USER INFO: You are helping ").append(userName).append(".\n");
                }
                String email = (String) userContext.get("email");
                if (email != null) {
                    contextPrompt.append("User email: ").append(email).append(".\n");
                }
                contextPrompt.append("Address the user by name when appropriate.\n\n");
            }
            
            // Add page context
            if (currentPage != null) {
                switch (currentPage) {
                    case "/configurator":
                        contextPrompt.append("USER CONTEXT: User is currently building a PC in the configurator.\n");
                        if (buildContext != null && !buildContext.isEmpty()) {
                            contextPrompt.append("CURRENT BUILD COMPONENTS: ").append(buildContext.toString()).append("\n");
                        }
                        contextPrompt.append("Provide specific advice about component selection, compatibility, and build optimization.\n\n");
                        break;
                    case "/builds":
                        contextPrompt.append("USER CONTEXT: User is browsing community builds.\n");
                        contextPrompt.append("Help them understand different build configurations and find inspiration.\n\n");
                        break;
                    case "/builds/my":
                        contextPrompt.append("USER CONTEXT: User is viewing their saved builds.\n");
                        if (userBuilds != null && !userBuilds.isEmpty()) {
                            contextPrompt.append("USER'S BUILDS: ").append(userBuilds.toString()).append("\n");
                        }
                        contextPrompt.append("Help them optimize, compare, or modify their existing builds.\n\n");
                        break;
                    case "/dashboard":
                        contextPrompt.append("USER CONTEXT: User is on the main dashboard.\n");
                        contextPrompt.append("Provide general PC building guidance, tips, and help them get started with their PC building journey.\n\n");
                        break;
                    default:
                        if (currentPage.startsWith("/components/")) {
                            String component = currentPage.substring("/components/".length());
                            contextPrompt.append("USER CONTEXT: User is browsing ").append(component.toUpperCase()).append(" components.\n");
                            contextPrompt.append("Provide detailed advice about ").append(component).append(" selection, specifications, and compatibility.\n\n");
                        }
                }
            }
            
            // Add component knowledge
            contextPrompt.append("COMPONENT KNOWLEDGE:\n");
            contextPrompt.append("- CPU: Consider cores, threads, socket type, TDP, and use case (gaming/productivity)\n");
            contextPrompt.append("- GPU: Focus on resolution, frame rates, VRAM, and budget\n");
            contextPrompt.append("- RAM: Speed, capacity, timing, and motherboard compatibility\n");
            contextPrompt.append("- Storage: SSD vs HDD, NVMe vs SATA, capacity needs\n");
            contextPrompt.append("- PSU: Wattage calculation, efficiency ratings, modularity\n");
            contextPrompt.append("- Motherboard: Socket compatibility, features, form factor\n");
            contextPrompt.append("- Cooling: Air vs liquid, TDP ratings, case clearance\n\n");
            
            // Add pricing and compatibility rules
            contextPrompt.append("ALWAYS CONSIDER:\n");
            contextPrompt.append("- Budget constraints and price-to-performance ratio\n");
            contextPrompt.append("- Component compatibility (socket, power, physical fit)\n");
            contextPrompt.append("- Future upgrade paths\n");
            contextPrompt.append("- Use case optimization (gaming, content creation, office work)\n\n");
            
            contextPrompt.append("USER QUESTION: ").append(userMessage);
            
            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.7f)
                    .topP(0.95f)
                    .maxOutputTokens(2048)
                    .build();
                    
            GenerateContentResponse response = client.models.generateContent(modelId, contextPrompt.toString(), config);
            String reply = response.text();
            
            System.out.println("Gemini context-aware response: " + reply);
            return reply;
            
        } catch (Exception e) {
            System.err.println("Error calling Gemini API with context: " + e.getMessage());
            e.printStackTrace();
            return "Sorry, I couldn't process your request. Please try again.";
        }
    }
}