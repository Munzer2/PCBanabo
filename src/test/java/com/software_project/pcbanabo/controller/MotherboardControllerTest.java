package com.software_project.pcbanabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software_project.pcbanabo.service.MotherboardService;
import com.software_project.pcbanabo.model.Motherboard;
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

@WebMvcTest(MotherboardController.class)
@DisplayName("Motherboard Controller Tests")
class MotherboardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MotherboardService motherboardService;

    @Autowired
    private ObjectMapper objectMapper;

    private Motherboard asusMotherboard;
    private Motherboard msiMotherboard;
    private Motherboard budgetMotherboard;
    private List<Motherboard> motherboardList;

    @BeforeEach
    void setUp() {
        asusMotherboard = new Motherboard();
        asusMotherboard.setId(1L);
        asusMotherboard.setModel_name("ASUS ROG STRIX Z690-E GAMING");
        asusMotherboard.setBrand_name("ASUS");
        asusMotherboard.setChipset("Z690");
        asusMotherboard.setSocket("LGA1700");
        asusMotherboard.setFormFactor("ATX");
        asusMotherboard.setMem_type("DDR5");
        asusMotherboard.setMem_slot(4);
        asusMotherboard.setMax_mem_speed(6400);
        asusMotherboard.setMax_power(180);
        asusMotherboard.setAvg_price(399.99);

        msiMotherboard = new Motherboard();
        msiMotherboard.setId(2L);
        msiMotherboard.setModel_name("MSI MAG B550 TOMAHAWK");
        msiMotherboard.setBrand_name("MSI");
        msiMotherboard.setChipset("B550");
        msiMotherboard.setSocket("AM4");
        msiMotherboard.setFormFactor("ATX");
        msiMotherboard.setMem_type("DDR4");
        msiMotherboard.setMem_slot(4);
        msiMotherboard.setMax_mem_speed(4400);
        msiMotherboard.setMax_power(105);
        msiMotherboard.setAvg_price(179.99);

        budgetMotherboard = new Motherboard();
        budgetMotherboard.setId(3L);
        budgetMotherboard.setModel_name("ASRock B450M PRO4");
        budgetMotherboard.setBrand_name("ASRock");
        budgetMotherboard.setChipset("B450");
        budgetMotherboard.setSocket("AM4");
        budgetMotherboard.setFormFactor("Micro-ATX");
        budgetMotherboard.setMem_type("DDR4");
        budgetMotherboard.setMem_slot(4);
        budgetMotherboard.setMax_mem_speed(3200);
        budgetMotherboard.setMax_power(95);
        budgetMotherboard.setAvg_price(79.99);

        motherboardList = Arrays.asList(asusMotherboard, msiMotherboard, budgetMotherboard);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllMotherboards_ShouldReturnMotherboardList() throws Exception {
            when(motherboardService.getAllMotherboards()).thenReturn(motherboardList);

            mockMvc.perform(get("/api/components/motherboards")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("ASUS ROG STRIX Z690-E GAMING"))
                    .andExpect(jsonPath("$[0].brand_name").value("ASUS"))
                    .andExpect(jsonPath("$[0].avg_price").value(399.99));
            
            verify(motherboardService, times(1)).getAllMotherboards();
        }

        @Test
        void getMotherboardById_ShouldReturnMotherboard() throws Exception {
            when(motherboardService.getMotherboardById(1L)).thenReturn(asusMotherboard);

            mockMvc.perform(get("/api/components/motherboards/id/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("ASUS ROG STRIX Z690-E GAMING"))
                    .andExpect(jsonPath("$.brand_name").value("ASUS"));
            
            verify(motherboardService, times(1)).getMotherboardById(1L);
        }

        @Test
        void getMotherboardById_NotFound_ShouldReturn404() throws Exception {
            when(motherboardService.getMotherboardById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/motherboards/id/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(motherboardService, times(1)).getMotherboardById(999L);
        }

        @Test
        void getAllMotherboards_ShouldHandleEmptyList() throws Exception {
            when(motherboardService.getAllMotherboards()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/motherboards")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(motherboardService, times(1)).getAllMotherboards();
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getMotherboardsBySocket_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getMotherboardsBySocket("LGA1700")).thenReturn(Arrays.asList(asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/socket/LGA1700")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].socket").value("LGA1700"));
            
            verify(motherboardService, times(1)).getMotherboardsBySocket("LGA1700");
        }

        @Test
        void getMotherboardsByFormFactor_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getMotherboardsByFormFactor("ATX")).thenReturn(Arrays.asList(asusMotherboard, msiMotherboard));

            mockMvc.perform(get("/api/components/motherboards/form_factor/ATX")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].formFactor").value("ATX"));
            
            verify(motherboardService, times(1)).getMotherboardsByFormFactor("ATX");
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredMotherboards_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(eq("ASUS"), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("brandName", "ASUS")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("ASUS"))
                    .andExpect(jsonPath("$[0].model_name").value("ASUS ROG STRIX Z690-E GAMING"));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(eq("ASUS"), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithSocketFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), eq("AM4"), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(msiMotherboard, budgetMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("socket", "AM4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].socket").value("AM4"));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), eq("AM4"), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithChipsetFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), eq("Z690"), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("chipset", "Z690")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].chipset").value("Z690"));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), eq("Z690"), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithFormFactorFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), eq("ATX"), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(asusMotherboard, msiMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("formFactor", "ATX")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].formFactor").value("ATX"));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), eq("ATX"), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithMemTypeFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           eq("DDR4"), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(msiMotherboard, budgetMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("memType", "DDR4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].mem_type").value("DDR4"));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         eq("DDR4"), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithMemSlotRange_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), eq(4), eq(4), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(motherboardList);

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("memSlotMin", "4")
                            .param("memSlotMax", "4")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].mem_slot").value(4));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), eq(4), eq(4), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithMaxMemSpeedFilter_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), eq(4000), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(asusMotherboard, msiMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("maxMemSpeedMin", "4000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].max_mem_speed").exists());
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), eq(4000), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithMaxPowerRange_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           eq(100), eq(200), isNull(), isNull()))
                    .thenReturn(Arrays.asList(msiMotherboard, asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("maxPowerMin", "100")
                            .param("maxPowerMax", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].max_power").exists());
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         eq(100), eq(200), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), eq(150.0), eq(400.0)))
                    .thenReturn(Arrays.asList(msiMotherboard, asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("minPrice", "150")
                            .param("maxPrice", "400")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").exists());
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), eq(150.0), eq(400.0));
        }

        @Test
        void getFilteredMotherboards_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(eq("ASUS"), eq("Z690"), eq("LGA1700"), eq("ATX"), 
                                                           eq("DDR5"), eq(4), eq(4), eq(6000), 
                                                           eq(150), eq(200), eq(350.0), eq(450.0)))
                    .thenReturn(Arrays.asList(asusMotherboard));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("brandName", "ASUS")
                            .param("chipset", "Z690")
                            .param("socket", "LGA1700")
                            .param("formFactor", "ATX")
                            .param("memType", "DDR5")
                            .param("memSlotMin", "4")
                            .param("memSlotMax", "4")
                            .param("maxMemSpeedMin", "6000")
                            .param("maxPowerMin", "150")
                            .param("maxPowerMax", "200")
                            .param("minPrice", "350")
                            .param("maxPrice", "450")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("ASUS"))
                    .andExpect(jsonPath("$[0].socket").value("LGA1700"))
                    .andExpect(jsonPath("$[0].avg_price").value(399.99));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(eq("ASUS"), eq("Z690"), eq("LGA1700"), eq("ATX"), 
                                                                         eq("DDR5"), eq(4), eq(4), eq(6000), 
                                                                         eq(150), eq(200), eq(350.0), eq(450.0));
        }

        @Test
        void getFilteredMotherboards_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(motherboardList);

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredMotherboards_WithPriceRangeEdgeCases_ShouldWork() throws Exception {
            when(motherboardService.getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), eq(0.0), eq(1000.0)))
                    .thenReturn(motherboardList);

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("minPrice", "0")
                            .param("maxPrice", "1000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), eq(0.0), eq(1000.0));
        }

        @Test
        void getFilteredMotherboards_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredMotherboards_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(motherboardService.getFilteredMotherboards2(eq("NonExistentBrand"), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull(), 
                                                           isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(motherboardService, times(1)).getFilteredMotherboards2(eq("NonExistentBrand"), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull(), 
                                                                         isNull(), isNull(), isNull(), isNull());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredMotherboards_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(motherboardService.getFilteredMotherboards2(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/motherboards/filtered")
                            .param("brandName", "ASUS")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getMotherboardById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(motherboardService.getMotherboardById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/motherboards/id/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
