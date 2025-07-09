package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.service.RamService;
import com.software_project.pcbanabo.model.Ram;
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

@WebMvcTest(RamController.class)
@DisplayName("RAM Controller Tests")
class RamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RamService ramService;

    private Ram corsairRam;
    private Ram gskillRam;
    private Ram budgetRam;
    private List<Ram> ramList;

    @BeforeEach
    void setUp() {
        corsairRam = new Ram();
        corsairRam.setId(1L);
        corsairRam.setModel_name("Corsair Vengeance LPX 16GB");
        corsairRam.setBrand_name("Corsair");
        corsairRam.setMemType("DDR4");
        corsairRam.setCapacity("16GB");
        corsairRam.setSpeed(3200);
        corsairRam.setAvg_price(89.99);
        corsairRam.setRgb(false);

        gskillRam = new Ram();
        gskillRam.setId(2L);
        gskillRam.setModel_name("G.Skill Trident Z RGB 32GB");
        gskillRam.setBrand_name("G.Skill");
        gskillRam.setMemType("DDR4");
        gskillRam.setCapacity("32GB");
        gskillRam.setSpeed(3600);
        gskillRam.setAvg_price(179.99);
        gskillRam.setRgb(true);

        budgetRam = new Ram();
        budgetRam.setId(3L);
        budgetRam.setModel_name("Crucial Ballistix 8GB");
        budgetRam.setBrand_name("Crucial");
        budgetRam.setMemType("DDR4");
        budgetRam.setCapacity("8GB");
        budgetRam.setSpeed(2400);
        budgetRam.setAvg_price(39.99);
        budgetRam.setRgb(false);

        ramList = Arrays.asList(corsairRam, gskillRam, budgetRam);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllRams_ShouldReturnRamList() throws Exception {
            when(ramService.getAllRams()).thenReturn(ramList);

            mockMvc.perform(get("/api/components/rams")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("Corsair Vengeance LPX 16GB"))
                    .andExpect(jsonPath("$[0].brand_name").value("Corsair"))
                    .andExpect(jsonPath("$[0].avg_price").value(89.99));
            
            verify(ramService, times(1)).getAllRams();
        }

        @Test
        void getRamById_ShouldReturnRam() throws Exception {
            when(ramService.getRamById(1L)).thenReturn(corsairRam);

            mockMvc.perform(get("/api/components/rams/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("Corsair Vengeance LPX 16GB"))
                    .andExpect(jsonPath("$.brand_name").value("Corsair"));
            
            verify(ramService, times(1)).getRamById(1L);
        }

        @Test
        void getRamById_NotFound_ShouldReturn404() throws Exception {
            when(ramService.getRamById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/rams/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(ramService, times(1)).getRamById(999L);
        }

        @Test
        void getAllRams_ShouldHandleEmptyList() throws Exception {
            when(ramService.getAllRams()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/rams")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(ramService, times(1)).getAllRams();
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredRams_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(eq("Corsair"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("brandName", "Corsair")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Corsair"))
                    .andExpect(jsonPath("$[0].model_name").value("Corsair Vengeance LPX 16GB"));
            
            verify(ramService, times(1)).getFilteredRams(eq("Corsair"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithMemTypeFilter_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), eq("DDR4"), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(ramList);

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("memType", "DDR4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), eq("DDR4"), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithCapacityFilter_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), isNull(), eq("16GB"), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("memCapacity", "16GB")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].capacity").value("16GB"));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), isNull(), eq("16GB"), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithSpeedMinFilter_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), isNull(), isNull(), 
                                          eq(3000), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairRam, gskillRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("speedMin", "3000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].speed").value(3200))
                    .andExpect(jsonPath("$[1].speed").value(3600));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), isNull(), isNull(), 
                                                        eq(3000), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithRgbFilter_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), isNull(), isNull(), 
                                          isNull(), eq(true), isNull(), isNull()))
                    .thenReturn(Arrays.asList(gskillRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("rgb", "true")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].rgb").value(true));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), isNull(), isNull(), 
                                                        isNull(), eq(true), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(50.0), eq(150.0)))
                    .thenReturn(Arrays.asList(corsairRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("minPrice", "50")
                            .param("maxPrice", "150")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(89.99));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), eq(50.0), eq(150.0));
        }

        @Test
        void getFilteredRams_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(ramService.getFilteredRams(eq("G.Skill"), eq("DDR4"), eq("32GB"), 
                                          eq(3500), eq(true), eq(150.0), eq(200.0)))
                    .thenReturn(Arrays.asList(gskillRam));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("brandName", "G.Skill")
                            .param("memType", "DDR4")
                            .param("memCapacity", "32GB")
                            .param("speedMin", "3500")
                            .param("rgb", "true")
                            .param("minPrice", "150")
                            .param("maxPrice", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("G.Skill"))
                    .andExpect(jsonPath("$[0].capacity").value("32GB"))
                    .andExpect(jsonPath("$[0].rgb").value(true));
            
            verify(ramService, times(1)).getFilteredRams(eq("G.Skill"), eq("DDR4"), eq("32GB"), 
                                                        eq(3500), eq(true), eq(150.0), eq(200.0));
        }

        @Test
        void getFilteredRams_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(ramService.getFilteredRams(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(ramList);

            mockMvc.perform(get("/api/components/rams/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(ramService, times(1)).getFilteredRams(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredRams_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredRams_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(ramService.getFilteredRams(eq("NonExistentBrand"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(ramService, times(1)).getFilteredRams(eq("NonExistentBrand"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredRams_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(ramService.getFilteredRams(any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/rams/filtered")
                            .param("brandName", "Corsair")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getRamById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(ramService.getRamById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/rams/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
