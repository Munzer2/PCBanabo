package com.software_project.pcbanabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software_project.pcbanabo.service.CasingService;
import com.software_project.pcbanabo.model.Casing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CasingController.class)
@DisplayName("Casing Controller Tests")
class CasingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CasingService casingService;

    @Autowired
    private ObjectMapper objectMapper;

    private Casing fractalCasing;
    private Casing corsairCasing;
    private Casing budgetCasing;
    private List<Casing> casingList;

    @BeforeEach
    void setUp() {
        fractalCasing = new Casing();
        fractalCasing.setId(1L);
        fractalCasing.setModel_name("Fractal Design Define 7");
        fractalCasing.setBrand_name("Fractal Design");
        fractalCasing.setMotherboardSupport("E-ATX, ATX, Micro-ATX, Mini-ITX");
        fractalCasing.setPsuClearance(300);
        fractalCasing.setGpuClearance(440);
        fractalCasing.setCpuClearance(185);
        fractalCasing.setRgb(false);
        fractalCasing.setDisplay(false);
        fractalCasing.setColor("Black");
        fractalCasing.setAvg_price(169.99);

        corsairCasing = new Casing();
        corsairCasing.setId(2L);
        corsairCasing.setModel_name("Corsair 4000D RGB");
        corsairCasing.setBrand_name("Corsair");
        corsairCasing.setMotherboardSupport("ATX, Micro-ATX, Mini-ITX");
        corsairCasing.setPsuClearance(220);
        corsairCasing.setGpuClearance(360);
        corsairCasing.setCpuClearance(170);
        corsairCasing.setRgb(true);
        corsairCasing.setDisplay(false);
        corsairCasing.setColor("Black");
        corsairCasing.setAvg_price(124.99);

        budgetCasing = new Casing();
        budgetCasing.setId(3L);
        budgetCasing.setModel_name("Cooler Master MasterBox Q300L");
        budgetCasing.setBrand_name("Cooler Master");
        budgetCasing.setMotherboardSupport("Micro-ATX, Mini-ITX");
        budgetCasing.setPsuClearance(160);
        budgetCasing.setGpuClearance(360);
        budgetCasing.setCpuClearance(158);
        budgetCasing.setRgb(false);
        budgetCasing.setDisplay(false);
        budgetCasing.setColor("Black");
        budgetCasing.setAvg_price(44.99);

        casingList = Arrays.asList(fractalCasing, corsairCasing, budgetCasing);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllCasings_ShouldReturnCasingList() throws Exception {
            when(casingService.getAllCasings()).thenReturn(casingList);

            mockMvc.perform(get("/api/components/casings")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("Fractal Design Define 7"))
                    .andExpect(jsonPath("$[0].brand_name").value("Fractal Design"))
                    .andExpect(jsonPath("$[0].avg_price").value(169.99));
            
            verify(casingService, times(1)).getAllCasings();
        }

        @Test
        void getCasingById_ShouldReturnCasing() throws Exception {
            when(casingService.getCasingById(1L)).thenReturn(fractalCasing);

            mockMvc.perform(get("/api/components/casings/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("Fractal Design Define 7"))
                    .andExpect(jsonPath("$.brand_name").value("Fractal Design"));
            
            verify(casingService, times(1)).getCasingById(1L);
        }

        @Test
        void getCasingById_NotFound_ShouldReturn404() throws Exception {
            when(casingService.getCasingById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/casings/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(casingService, times(1)).getCasingById(999L);
        }

        @Test
        void getAllCasings_ShouldHandleEmptyList() throws Exception {
            when(casingService.getAllCasings()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/casings")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(casingService, times(1)).getAllCasings();
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredCasings_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(eq(100.0), eq(200.0), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("price_gte", "100")
                            .param("price_lte", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(169.99));
            
            verify(casingService, times(1)).getFilteredCasings2(eq(100.0), eq(200.0), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithPsuClearanceRange_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), eq(200), eq(400), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("psu_gte", "200")
                            .param("psu_lte", "400")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].psuClearance").exists());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), eq(200), eq(400), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithGpuClearanceRange_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 eq(300), eq(500), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing, budgetCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("gpu_gte", "300")
                            .param("gpu_lte", "500")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].gpuClearance").exists());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              eq(300), eq(500), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithCpuClearanceRange_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), eq(150), eq(200), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing, budgetCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("cpu_gte", "150")
                            .param("cpu_lte", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].cpuClearance").exists());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), eq(150), eq(200), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithBrandsFilter_ShouldReturnFilteredResults() throws Exception {
            List<String> brands = Arrays.asList("Fractal Design", "Corsair");
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 eq(brands), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("brands", "Fractal Design")
                            .param("brands", "Corsair")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Fractal Design"));
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              eq(brands), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithMotherboardFilter_ShouldReturnFilteredResults() throws Exception {
            List<String> motherboards = Arrays.asList("ATX");
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), eq(motherboards), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(fractalCasing, corsairCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("motherboard", "ATX")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].motherboardSupport").exists());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), eq(motherboards), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithColorFilter_ShouldReturnFilteredResults() throws Exception {
            List<String> colors = Arrays.asList("Black");
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), eq(colors), isNull(), isNull()))
                    .thenReturn(casingList);

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("color", "Black")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].color").value("Black"));
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), eq(colors), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithRgbFilter_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), eq(true), isNull()))
                    .thenReturn(Arrays.asList(corsairCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("rgb", "true")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].rgb").value(true));
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), eq(true), isNull());
        }

        @Test
        void getFilteredCasings_WithDisplayFilter_ShouldReturnFilteredResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), eq(false)))
                    .thenReturn(casingList);

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("display", "false")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].display").value(false));
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), eq(false));
        }

        @Test
        void getFilteredCasings_WithMultipleFilters_ShouldReturnResults() throws Exception {
            List<String> brands = Arrays.asList("Fractal Design");
            List<String> colors = Arrays.asList("Black");
            when(casingService.getFilteredCasings2(eq(150.0), eq(200.0), eq(250), eq(350), 
                                                 eq(400), eq(500), eq(180), eq(200), 
                                                 eq(brands), isNull(), eq(colors), eq(false), eq(false)))
                    .thenReturn(Arrays.asList(fractalCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("price_gte", "150")
                            .param("price_lte", "200")
                            .param("psu_gte", "250")
                            .param("psu_lte", "350")
                            .param("gpu_gte", "400")
                            .param("gpu_lte", "500")
                            .param("cpu_gte", "180")
                            .param("cpu_lte", "200")
                            .param("brands", "Fractal Design")
                            .param("color", "Black")
                            .param("rgb", "false")
                            .param("display", "false")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Fractal Design"))
                    .andExpect(jsonPath("$[0].avg_price").value(169.99));
            
            verify(casingService, times(1)).getFilteredCasings2(eq(150.0), eq(200.0), eq(250), eq(350), 
                                                              eq(400), eq(500), eq(180), eq(200), 
                                                              eq(brands), isNull(), eq(colors), eq(false), eq(false));
        }

        @Test
        void getFilteredCasings_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(casingList);

            mockMvc.perform(get("/api/components/casings/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithPriceRangeEdgeCases_ShouldWork() throws Exception {
            when(casingService.getFilteredCasings2(eq(0.0), eq(1000.0), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(casingList);

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("price_gte", "0")
                            .param("price_lte", "1000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(casingService, times(1)).getFilteredCasings2(eq(0.0), eq(1000.0), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredCasings_WithBooleanParameters_ShouldWork() throws Exception {
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), eq(false), eq(false)))
                    .thenReturn(Arrays.asList(fractalCasing, budgetCasing));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("rgb", "false")
                            .param("display", "false")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), eq(false), eq(false));
        }

        @Test
        void getFilteredCasings_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("price_gte", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredCasings_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            List<String> nonExistentBrands = Arrays.asList("NonExistentBrand");
            when(casingService.getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                 isNull(), isNull(), isNull(), isNull(), 
                                                 eq(nonExistentBrands), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("brands", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(casingService, times(1)).getFilteredCasings2(isNull(), isNull(), isNull(), isNull(), 
                                                              isNull(), isNull(), isNull(), isNull(), 
                                                              eq(nonExistentBrands), isNull(), isNull(), isNull(), isNull());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredCasings_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(casingService.getFilteredCasings2(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/casings/filtered")
                            .param("price_gte", "100")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getCasingById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(casingService.getCasingById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/casings/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
