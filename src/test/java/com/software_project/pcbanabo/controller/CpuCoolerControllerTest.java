package com.software_project.pcbanabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software_project.pcbanabo.service.CpuCoolerService;
import com.software_project.pcbanabo.model.CpuCooler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

@WebMvcTest(CpuCoolerController.class)
@DisplayName("CPU Cooler Controller Tests")
@AutoConfigureMockMvc(addFilters = false)
class CpuCoolerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpuCoolerService cpuCoolerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CpuCooler noctuaCooler;
    private CpuCooler corsairCooler;
    private CpuCooler budgetCooler;
    private List<CpuCooler> coolerList;

    @BeforeEach
    void setUp() {
        noctuaCooler = new CpuCooler();
        noctuaCooler.setId(1L);
        noctuaCooler.setModel_name("Noctua NH-D15");
        noctuaCooler.setBrand_name("Noctua");
        noctuaCooler.setCooler_type("Air");
        noctuaCooler.setTowerHeight(165);
        noctuaCooler.setRadiator_size(null);
        noctuaCooler.setCoolingCapacity(220);
        noctuaCooler.setRam_clearance(32);
        noctuaCooler.setAvg_price(99.99);

        corsairCooler = new CpuCooler();
        corsairCooler.setId(2L);
        corsairCooler.setModel_name("Corsair H150i Elite Capellix");
        corsairCooler.setBrand_name("Corsair");
        corsairCooler.setCooler_type("Liquid");
        corsairCooler.setTowerHeight(null);
        corsairCooler.setRadiator_size(360);
        corsairCooler.setCoolingCapacity(300);
        corsairCooler.setRam_clearance(null);
        corsairCooler.setAvg_price(189.99);

        budgetCooler = new CpuCooler();
        budgetCooler.setId(3L);
        budgetCooler.setModel_name("Cooler Master Hyper 212");
        budgetCooler.setBrand_name("Cooler Master");
        budgetCooler.setCooler_type("Air");
        budgetCooler.setTowerHeight(158);
        budgetCooler.setRadiator_size(null);
        budgetCooler.setCoolingCapacity(150);
        budgetCooler.setRam_clearance(27);
        budgetCooler.setAvg_price(39.99);

        coolerList = Arrays.asList(noctuaCooler, corsairCooler, budgetCooler);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllCpuCoolers_ShouldReturnCoolerList() throws Exception {
            when(cpuCoolerService.getAllCpuCoolers()).thenReturn(coolerList);

            mockMvc.perform(get("/api/components/cpu-coolers")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("Noctua NH-D15"))
                    .andExpect(jsonPath("$[0].brand_name").value("Noctua"))
                    .andExpect(jsonPath("$[0].avg_price").value(99.99));
            
            verify(cpuCoolerService, times(1)).getAllCpuCoolers();
        }

        @Test
        void getCpuCoolerById_ShouldReturnCooler() throws Exception {
            when(cpuCoolerService.getCpuCoolerById(1L)).thenReturn(noctuaCooler);

            mockMvc.perform(get("/api/components/cpu-coolers/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("Noctua NH-D15"))
                    .andExpect(jsonPath("$.brand_name").value("Noctua"));
            
            verify(cpuCoolerService, times(1)).getCpuCoolerById(1L);
        }

        @Test
        void getCpuCoolerById_NotFound_ShouldReturn404() throws Exception {
            when(cpuCoolerService.getCpuCoolerById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/cpu-coolers/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(cpuCoolerService, times(1)).getCpuCoolerById(999L);
        }

        @Test
        void getAllCpuCoolers_ShouldHandleEmptyList() throws Exception {
            when(cpuCoolerService.getAllCpuCoolers()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/cpu-coolers")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(cpuCoolerService, times(1)).getAllCpuCoolers();
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getCpuCoolersByTowerHeight_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getCpuCoolersByTowerHeight(165)).thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/tower_height/165")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].towerHeight").value(165));
            
            verify(cpuCoolerService, times(1)).getCpuCoolersByTowerHeight(165);
        }

        @Test
        void getCpuCoolersByCoolingCapacity_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getCpuCoolersByCoolingCapacity(220)).thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/cooling_capacity/220")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].coolingCapacity").value(220));
            
            verify(cpuCoolerService, times(1)).getCpuCoolersByCoolingCapacity(220);
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredCpuCoolers_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(eq("Noctua"), isNull(), isNull(), 
                                                      isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("brandName", "Noctua")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Noctua"))
                    .andExpect(jsonPath("$[0].model_name").value("Noctua NH-D15"));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(eq("Noctua"), isNull(), isNull(), 
                                                                   isNull(), isNull(), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithCoolerTypeFilter_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), eq("Air"), isNull(), 
                                                      isNull(), isNull(), isNull(), isNull(),isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler, budgetCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("coolerType", "Air")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].cooler_type").value("Air"));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), eq("Air"), isNull(), 
                                                                   isNull(), isNull(), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithTowerHeightFilter_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), eq(165), 
                                                      isNull(), isNull(), isNull(), isNull(),isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("towerHeight", "165")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].towerHeight").value(165));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), eq(165), 
                                                                   isNull(), isNull(), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithRadiatorSizeFilter_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                      eq(360), isNull(), isNull(), isNull(),isNull()))
                    .thenReturn(Arrays.asList(corsairCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("radiatorSize", "360")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].radiator_size").value(360));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                                   eq(360), isNull(), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithCoolingCapacityFilter_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                      isNull(), eq(200), isNull(), isNull(),isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler, corsairCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("coolingCapacity", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].coolingCapacity").exists());
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                                   isNull(), eq(200), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                      isNull(), isNull(), eq(80.0), eq(150.0),isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("minPrice", "80")
                            .param("maxPrice", "150")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(99.99));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                                   isNull(), isNull(), eq(80.0), eq(150.0),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(eq("Noctua"), eq("Air"), eq(165), 
                                                      isNull(), eq(220), eq(80.0), eq(120.0),isNull()))
                    .thenReturn(Arrays.asList(noctuaCooler));

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("brandName", "Noctua")
                            .param("coolerType", "Air")
                            .param("towerHeight", "165")
                            .param("coolingCapacity", "220")
                            .param("minPrice", "80")
                            .param("maxPrice", "120")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Noctua"))
                    .andExpect(jsonPath("$[0].cooler_type").value("Air"))
                    .andExpect(jsonPath("$[0].avg_price").value(99.99));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(eq("Noctua"), eq("Air"), eq(165), 
                                                                   isNull(), eq(220), eq(80.0), eq(120.0),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                      isNull(), isNull(), isNull(), isNull(),isNull()))
                    .thenReturn(coolerList);

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                                   isNull(), isNull(), isNull(), isNull(),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithPriceRangeEdgeCases_ShouldWork() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                      isNull(), isNull(), eq(0.0), eq(1000.0),isNull()))
                    .thenReturn(coolerList);

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("minPrice", "0")
                            .param("maxPrice", "1000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(isNull(), isNull(), isNull(), 
                                                                   isNull(), isNull(), eq(0.0), eq(1000.0),isNull());
        }

        @Test
        void getFilteredCpuCoolers_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredCpuCoolers_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(cpuCoolerService.getFilteredCpuCoolers(eq("NonExistentBrand"), isNull(), isNull(), 
                                                      isNull(), isNull(), isNull(), isNull(),isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/cpu-coolers/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(cpuCoolerService, times(1)).getFilteredCpuCoolers(eq("NonExistentBrand"), isNull(), isNull(), 
                                                                   isNull(), isNull(), isNull(), isNull(),isNull());
        }
    }

//     @Nested
//     @DisplayName("Error Handling")
//     class ErrorHandling {

//         @Test
//         void getFilteredCpuCoolers_ServiceThrowsException_ShouldReturn500() throws Exception {
//             when(cpuCoolerService.getFilteredCpuCoolers(any(), any(), any(), any(), any(), any(), any()))
//                     .thenThrow(new RuntimeException("Database error"));

//             mockMvc.perform(get("/api/components/cpu-coolers/filtered")
//                             .param("brandName", "Noctua")
//                             .contentType(MediaType.APPLICATION_JSON))
//                     .andExpect(status().isInternalServerError());
//         }

//         @Test
//         void getCpuCoolerById_ServiceThrowsException_ShouldReturn500() throws Exception {
//             when(cpuCoolerService.getCpuCoolerById(1L)).thenThrow(new RuntimeException("Database error"));

//             mockMvc.perform(get("/api/components/cpu-coolers/1")
//                             .contentType(MediaType.APPLICATION_JSON))
//                     .andExpect(status().isInternalServerError());
//         }
//     }
}
