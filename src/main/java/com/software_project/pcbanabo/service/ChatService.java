package com.software_project.pcbanabo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.model.CpuCooler;
import com.software_project.pcbanabo.model.Gpu;
import com.software_project.pcbanabo.model.Motherboard;
import com.software_project.pcbanabo.model.Psu;
import com.software_project.pcbanabo.model.Ram;
import com.software_project.pcbanabo.model.Ssd;

@Service
public class ChatService {

    private final String apiKey;
    private final String modelId;
    private final Client client;
    
    // Inject all component services
    @Autowired
    private CpuService cpuService;
    @Autowired
    private GpuService gpuService;
    @Autowired
    private MotherboardService motherboardService;
    @Autowired
    private RamService ramService;
    @Autowired
    private SsdService ssdService;
    @Autowired
    private PsuService psuService;
    @Autowired
    private CpuCoolerService cpuCoolerService;
    @Autowired
    private CasingService casingService;

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

    public String suggestBuild(String budget, String useCase, String preferences) {
        try {
            System.out.println("ChatService: Generating AI build suggestion with REAL components from database");
            System.out.println("Budget: " + budget + ", Use Case: " + useCase + ", Preferences: " + preferences);
            
            // Parse budget
            double budgetAmount = parseBudget(budget);
            System.out.println("Parsed budget amount: " + budgetAmount);
            
            // Fetch real components from database
            List<Cpu> allCpus = cpuService.getAllCpus();
            List<Gpu> allGpus = gpuService.getAllGpus();
            List<Motherboard> allMotherboards = motherboardService.getAllMotherboards();
            List<Ram> allRams = ramService.getAllRams();
            List<Ssd> allSsds = ssdService.getAllSsds();
            List<Psu> allPsus = psuService.getAllPsus();
            List<CpuCooler> allCoolers = cpuCoolerService.getAllCpuCoolers();
            List<Casing> allCasings = casingService.getAllCasings();
            
            System.out.println("Fetched components: " + allCpus.size() + " CPUs, " + allGpus.size() + " GPUs, " 
                             + allMotherboards.size() + " Motherboards, " + allRams.size() + " RAMs, "
                             + allSsds.size() + " SSDs, " + allPsus.size() + " PSUs, "
                             + allCoolers.size() + " Coolers, " + allCasings.size() + " Cases");
            
            // Filter components by budget ranges
            List<Cpu> affordableCpus = allCpus.stream()
                .filter(cpu -> cpu.getAverage_price() > 0 && cpu.getAverage_price() <= budgetAmount * 0.4)
                .limit(15) // Limit for AI token efficiency
                .toList();
            
            List<Gpu> affordableGpus = allGpus.stream()
                .filter(gpu -> gpu.getAvg_price() > 0 && gpu.getAvg_price() <= budgetAmount * 0.5)
                .limit(15)
                .toList();
            
            List<Motherboard> affordableMotherboards = allMotherboards.stream()
                .filter(mb -> mb.getAvg_price() > 0 && mb.getAvg_price() <= budgetAmount * 0.2)
                .limit(10)
                .toList();
            
            List<Ram> affordableRams = allRams.stream()
                .filter(ram -> ram.getAvg_price() > 0 && ram.getAvg_price() <= budgetAmount * 0.15)
                .limit(10)
                .toList();
            
            List<Ssd> affordableSsds = allSsds.stream()
                .filter(ssd -> ssd.getAvg_price() > 0 && ssd.getAvg_price() <= budgetAmount * 0.15)
                .limit(10)
                .toList();
            
            List<Psu> affordablePsus = allPsus.stream()
                .filter(psu -> psu.getAvg_price() > 0 && psu.getAvg_price() <= budgetAmount * 0.2)
                .limit(10)
                .toList();
            
            List<CpuCooler> affordableCoolers = allCoolers.stream()
                .filter(cooler -> cooler.getAvg_price() > 0 && cooler.getAvg_price() <= budgetAmount * 0.1)
                .limit(8)
                .toList();
            
            List<Casing> affordableCasings = allCasings.stream()
                .filter(casing -> casing.getAvg_price() > 0 && casing.getAvg_price() <= budgetAmount * 0.15)
                .limit(8)
                .toList();
            
            // Build AI prompt with real component data
            StringBuilder prompt = new StringBuilder();
            prompt.append("You are an expert PC builder. Select EXACTLY ONE component from each category below to create a complete PC build.\n");
            prompt.append("BUDGET: $").append(budgetAmount).append("\n");
            prompt.append("USE CASE: ").append(useCase != null ? useCase : "General purpose").append("\n");
            prompt.append("PREFERENCES: ").append(preferences != null ? preferences : "Balanced").append("\n\n");
            
            // Add real CPU options
            prompt.append("AVAILABLE CPUS (select ONE by ID):\n");
            for (Cpu cpu : affordableCpus) {
                prompt.append("ID: ").append(cpu.getId())
                      .append(" | ").append(cpu.getBrand_name()).append(" ").append(cpu.getModel_name())
                      .append(" | $").append(cpu.getAverage_price())
                      .append(" | Socket: ").append(cpu.getSocket())
                      .append(" | TDP: ").append(cpu.getTdp()).append("W\n");
            }
            
            // Add real GPU options
            prompt.append("\nAVAILABLE GPUS (select ONE by ID):\n");
            for (Gpu gpu : affordableGpus) {
                prompt.append("ID: ").append(gpu.getId())
                      .append(" | ").append(gpu.getBrand_name()).append(" ").append(gpu.getModel_name())
                      .append(" | $").append(gpu.getAvg_price())
                      .append(" | VRAM: ").append(gpu.getVram()).append("GB")
                      .append(" | TDP: ").append(gpu.getTdp()).append("W\n");
            }
            
            // Add real Motherboard options
            prompt.append("\nAVAILABLE MOTHERBOARDS (select ONE by ID):\n");
            for (Motherboard mb : affordableMotherboards) {
                prompt.append("ID: ").append(mb.getId())
                      .append(" | ").append(mb.getBrand_name()).append(" ").append(mb.getModel_name())
                      .append(" | $").append(mb.getAvg_price())
                      .append(" | Socket: ").append(mb.getSocket())
                      .append(" | Form: ").append(mb.getFormFactor()).append("\n");
            }
            
            // Add real RAM options
            prompt.append("\nAVAILABLE RAM (select ONE by ID):\n");
            for (Ram ram : affordableRams) {
                prompt.append("ID: ").append(ram.getId())
                      .append(" | ").append(ram.getBrand_name()).append(" ").append(ram.getModel_name())
                      .append(" | $").append(ram.getAvg_price())
                      .append(" | ").append(ram.getCapacity()).append(" ").append(ram.getMemType())
                      .append(" | Speed: ").append(ram.getSpeed()).append("MHz\n");
            }
            
            // Add real SSD options
            prompt.append("\nAVAILABLE STORAGE (select ONE by ID):\n");
            for (Ssd ssd : affordableSsds) {
                prompt.append("ID: ").append(ssd.getId())
                      .append(" | ").append(ssd.getBrand_name()).append(" ").append(ssd.getModel_name())
                      .append(" | $").append(ssd.getAvg_price())
                      .append(" | ").append(ssd.getCapacity())
                      .append(" | Form: ").append(ssd.getForm_factor()).append("\n");
            }
            
            // Add real PSU options
            prompt.append("\nAVAILABLE POWER SUPPLIES (select ONE by ID):\n");
            for (Psu psu : affordablePsus) {
                prompt.append("ID: ").append(psu.getId())
                      .append(" | ").append(psu.getBrand_name()).append(" ").append(psu.getModel_name())
                      .append(" | $").append(psu.getAvg_price())
                      .append(" | ").append(psu.getWattage()).append("W")
                      .append(" | Cert: ").append(psu.getCertification()).append("\n");
            }
            
            // Add real CPU Cooler options
            prompt.append("\nAVAILABLE CPU COOLERS (select ONE by ID):\n");
            for (CpuCooler cooler : affordableCoolers) {
                prompt.append("ID: ").append(cooler.getId())
                      .append(" | ").append(cooler.getBrand_name()).append(" ").append(cooler.getModel_name())
                      .append(" | $").append(cooler.getAvg_price())
                      .append(" | Type: ").append(cooler.getCooler_type())
                      .append(" | Capacity: ").append(cooler.getCoolingCapacity()).append("W\n");
            }
            
            // Add real Case options
            prompt.append("\nAVAILABLE CASES (select ONE by ID):\n");
            for (Casing casing : affordableCasings) {
                prompt.append("ID: ").append(casing.getId())
                      .append(" | ").append(casing.getBrand_name()).append(" ").append(casing.getModel_name())
                      .append(" | $").append(casing.getAvg_price())
                      .append(" | Supports: ").append(casing.getMotherboardSupport())
                      .append(" | GPU Clearance: ").append(casing.getGpuClearance()).append("mm\n");
            }
            
            prompt.append("\nIMPORTANT RULES:\n");
            prompt.append("1. Select components that are COMPATIBLE (CPU socket = Motherboard socket)\n");
            prompt.append("2. Ensure PSU wattage covers total system power draw\n");
            prompt.append("3. Stay within budget: $").append(budgetAmount).append("\n");
            prompt.append("4. Optimize for the specified use case: ").append(useCase).append("\n\n");
            
            prompt.append("RESPONSE FORMAT - Return ONLY this JSON (no extra text):\n");
            prompt.append("{\n");
            prompt.append("  \"buildName\": \"Build name based on use case and budget\",\n");
            prompt.append("  \"totalPrice\": calculated_total_price,\n");
            prompt.append("  \"explanation\": \"Brief explanation of component choices\",\n");
            prompt.append("  \"components\": {\n");
            prompt.append("    \"cpu\": { \"id\": selected_cpu_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"gpu\": { \"id\": selected_gpu_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"motherboard\": { \"id\": selected_mb_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"ram\": { \"id\": selected_ram_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"storage\": { \"id\": selected_ssd_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"psu\": { \"id\": selected_psu_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"cpuCooler\": { \"id\": selected_cooler_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price },\n");
            prompt.append("    \"casing\": { \"id\": selected_case_id, \"brand_name\": \"brand\", \"model_name\": \"model\", \"avg_price\": price }\n");
            prompt.append("  }\n");
            prompt.append("}\n");

            GenerateContentConfig config = GenerateContentConfig.builder()
                    .temperature(0.3f) // Lower temperature for more consistent component selection
                    .topP(0.8f)
                    .maxOutputTokens(3048)
                    .build();

            GenerateContentResponse response = client.models.generateContent(modelId, prompt.toString(), config);
            String reply = response.text();
            
            // Clean up the response
            String cleanedReply = reply.trim();
            if (cleanedReply.startsWith("```json")) {
                cleanedReply = cleanedReply.replaceFirst("```json\\s*", "");
            }
            if (cleanedReply.endsWith("```")) {
                cleanedReply = cleanedReply.replaceFirst("\\s*```$", "");
            }
            
            int jsonStart = cleanedReply.indexOf('{');
            int jsonEnd = cleanedReply.lastIndexOf('}');
            
            if (jsonStart != -1 && jsonEnd != -1 && jsonEnd > jsonStart) {
                cleanedReply = cleanedReply.substring(jsonStart, jsonEnd + 1);
            }
            
            System.out.println("AI build suggestion with real components: " + cleanedReply);
            
            // Validate JSON structure
            if (!cleanedReply.trim().startsWith("{") || !cleanedReply.trim().endsWith("}")) {
                throw new RuntimeException("Invalid JSON response from AI");
            }
            
            return cleanedReply;
            
        } catch (Exception e) {
            System.err.println("Error generating AI build with real components: " + e.getMessage());
            e.printStackTrace();
            // Return fallback build with real component IDs if available
            return createFallbackBuildWithRealComponents(budget);
        }
    }
    
    private double parseBudget(String budget) {
        try {
            // Remove all non-numeric characters except decimal point
            String numericBudget = budget.replaceAll("[^0-9.]", "");
            if (numericBudget.isEmpty()) {
                return 50000.0; // Default budget
            }
            return Double.parseDouble(numericBudget);
        } catch (NumberFormatException e) {
            System.err.println("Could not parse budget: " + budget + ", using default 50000");
            return 50000.0;
        }
    }
    
    private String createFallbackBuildWithRealComponents(String budget) {
        try {
            System.out.println("Creating fallback build with real components");
            
            // Get first available components from each category
            List<Cpu> cpus = cpuService.getAllCpus();
            List<Gpu> gpus = gpuService.getAllGpus();
            List<Motherboard> motherboards = motherboardService.getAllMotherboards();
            List<Ram> rams = ramService.getAllRams();
            List<Ssd> ssds = ssdService.getAllSsds();
            List<Psu> psus = psuService.getAllPsus();
            List<CpuCooler> coolers = cpuCoolerService.getAllCpuCoolers();
            List<Casing> casings = casingService.getAllCasings();
            
            if (!cpus.isEmpty() && !gpus.isEmpty() && !motherboards.isEmpty() && !rams.isEmpty() && 
                !ssds.isEmpty() && !psus.isEmpty() && !coolers.isEmpty() && !casings.isEmpty()) {
                
                Cpu cpu = cpus.get(0);
                Gpu gpu = gpus.get(0);
                Motherboard mb = motherboards.get(0);
                Ram ram = rams.get(0);
                Ssd ssd = ssds.get(0);
                Psu psu = psus.get(0);
                CpuCooler cooler = coolers.get(0);
                Casing casing = casings.get(0);
                
                double totalPrice = cpu.getAverage_price() + gpu.getAvg_price() + mb.getAvg_price() + 
                                  ram.getAvg_price() + ssd.getAvg_price() + psu.getAvg_price() + 
                                  cooler.getAvg_price() + casing.getAvg_price();
                
                return String.format("{\"buildName\":\"Fallback Build with Real Components\",\"totalPrice\":%.0f,\"explanation\":\"A fallback build using real components from the database\",\"components\":{\"cpu\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"gpu\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"motherboard\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"ram\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"storage\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"psu\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"cpuCooler\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f},\"casing\":{\"id\":%d,\"brand_name\":\"%s\",\"model_name\":\"%s\",\"avg_price\":%.0f}}}",
                    totalPrice,
                    cpu.getId(), cpu.getBrand_name(), cpu.getModel_name(), cpu.getAverage_price(),
                    gpu.getId(), gpu.getBrand_name(), gpu.getModel_name(), gpu.getAvg_price(),
                    mb.getId(), mb.getBrand_name(), mb.getModel_name(), mb.getAvg_price(),
                    ram.getId(), ram.getBrand_name(), ram.getModel_name(), ram.getAvg_price(),
                    ssd.getId(), ssd.getBrand_name(), ssd.getModel_name(), ssd.getAvg_price(),
                    psu.getId(), psu.getBrand_name(), psu.getModel_name(), psu.getAvg_price(),
                    cooler.getId(), cooler.getBrand_name(), cooler.getModel_name(), cooler.getAvg_price(),
                    casing.getId(), casing.getBrand_name(), casing.getModel_name(), casing.getAvg_price());
            }
        } catch (Exception e) {
            System.err.println("Error creating fallback build with real components: " + e.getMessage());
        }
        
        // Ultimate fallback with mock data if database is empty
        return createFallbackBuild(budget);
    }
    
    private String createFallbackBuild(String budget) {
        // Extract budget number for basic categorization
        int budgetAmount = 50000; // default
        try {
            String budgetNumbers = budget.replaceAll("[^0-9]", "");
            if (!budgetNumbers.isEmpty()) {
                budgetAmount = Integer.parseInt(budgetNumbers);
            }
        } catch (NumberFormatException e) {
            // Use default
        }
        
        // Simple fallback build based on budget
        if (budgetAmount <= 30000) {
            return "{\"buildName\":\"Budget Office Build\",\"totalPrice\":28000,\"explanation\":\"A basic build suitable for office work and light tasks\",\"components\":{\"cpu\":{\"brand_name\":\"AMD\",\"model_name\":\"Ryzen 3 3200G\",\"avg_price\":8000},\"gpu\":{\"brand_name\":\"Integrated\",\"model_name\":\"Vega 8\",\"avg_price\":0},\"motherboard\":{\"brand_name\":\"MSI\",\"model_name\":\"A320M\",\"avg_price\":5000},\"ram\":{\"brand_name\":\"Corsair\",\"model_name\":\"8GB DDR4\",\"avg_price\":4000},\"storage\":{\"brand_name\":\"WD\",\"model_name\":\"240GB SSD\",\"avg_price\":3000},\"psu\":{\"brand_name\":\"Corsair\",\"model_name\":\"CV450\",\"avg_price\":4000},\"cpuCooler\":{\"brand_name\":\"Stock\",\"model_name\":\"AMD Stock Cooler\",\"avg_price\":0},\"casing\":{\"brand_name\":\"Generic\",\"model_name\":\"Mid Tower\",\"avg_price\":4000}}}";
        } else if (budgetAmount <= 60000) {
            return "{\"buildName\":\"Mid-Range Gaming Build\",\"totalPrice\":55000,\"explanation\":\"A balanced build suitable for 1080p gaming and productivity\",\"components\":{\"cpu\":{\"brand_name\":\"Intel\",\"model_name\":\"Core i5-10400F\",\"avg_price\":12000},\"gpu\":{\"brand_name\":\"NVIDIA\",\"model_name\":\"GTX 1650 Super\",\"avg_price\":18000},\"motherboard\":{\"brand_name\":\"MSI\",\"model_name\":\"B460M\",\"avg_price\":8000},\"ram\":{\"brand_name\":\"Corsair\",\"model_name\":\"16GB DDR4\",\"avg_price\":7000},\"storage\":{\"brand_name\":\"Samsung\",\"model_name\":\"500GB NVMe SSD\",\"avg_price\":5000},\"psu\":{\"brand_name\":\"Corsair\",\"model_name\":\"CV550\",\"avg_price\":5000},\"cpuCooler\":{\"brand_name\":\"Cooler Master\",\"model_name\":\"Hyper 212\",\"avg_price\":2500},\"casing\":{\"brand_name\":\"Cooler Master\",\"model_name\":\"MasterBox Q300L\",\"avg_price\":3500}}}";
        } else {
            return "{\"buildName\":\"High-End Gaming Build\",\"totalPrice\":85000,\"explanation\":\"A powerful build for high-end gaming and content creation\",\"components\":{\"cpu\":{\"brand_name\":\"Intel\",\"model_name\":\"Core i7-12700F\",\"avg_price\":25000},\"gpu\":{\"brand_name\":\"NVIDIA\",\"model_name\":\"RTX 3070\",\"avg_price\":45000},\"motherboard\":{\"brand_name\":\"ASUS\",\"model_name\":\"B660 Gaming\",\"avg_price\":15000},\"ram\":{\"brand_name\":\"Corsair\",\"model_name\":\"32GB DDR4\",\"avg_price\":12000},\"storage\":{\"brand_name\":\"Samsung\",\"model_name\":\"1TB NVMe SSD\",\"avg_price\":8000},\"psu\":{\"brand_name\":\"Corsair\",\"model_name\":\"RM750x\",\"avg_price\":12000},\"cpuCooler\":{\"brand_name\":\"Noctua\",\"model_name\":\"NH-U12S\",\"avg_price\":6000},\"casing\":{\"brand_name\":\"Fractal Design\",\"model_name\":\"Define 7\",\"avg_price\":8000}}}";
        }
    }
}