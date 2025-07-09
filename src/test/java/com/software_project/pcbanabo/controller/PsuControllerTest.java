package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.service.PsuService;
import com.software_project.pcbanabo.model.Psu;
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

@WebMvcTest(PsuController.class)
@DisplayName("PSU Controller Tests")
class PsuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PsuService psuService;

    private Psu corsairPsu;
    private Psu seasonicPsu;
    private Psu budgetPsu;
    private List<Psu> psuList;

    @BeforeEach
    void setUp() {
        corsairPsu = new Psu();
        corsairPsu.setId(1L);
        corsairPsu.setModel_name("Corsair RM850x");
        corsairPsu.setBrand_name("Corsair");
        corsairPsu.setWattage(850);
        corsairPsu.setForm_factor("ATX");
        corsairPsu.setCertification("80+ Gold");
        corsairPsu.setAvg_price(149.99);
        corsairPsu.setPsuLength(160);

        seasonicPsu = new Psu();
        seasonicPsu.setId(2L);
        seasonicPsu.setModel_name("Seasonic Focus GX-750");
        seasonicPsu.setBrand_name("Seasonic");
        seasonicPsu.setWattage(750);
        seasonicPsu.setForm_factor("ATX");
        seasonicPsu.setCertification("80+ Gold");
        seasonicPsu.setAvg_price(129.99);
        seasonicPsu.setPsuLength(165);

        budgetPsu = new Psu();
        budgetPsu.setId(3L);
        budgetPsu.setModel_name("EVGA BR 450W");
        budgetPsu.setBrand_name("EVGA");
        budgetPsu.setWattage(450);
        budgetPsu.setForm_factor("ATX");
        budgetPsu.setCertification("80+ Bronze");
        budgetPsu.setAvg_price(49.99);
        budgetPsu.setPsuLength(140);

        psuList = Arrays.asList(corsairPsu, seasonicPsu, budgetPsu);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllPsus_ShouldReturnPsuList() throws Exception {
            when(psuService.getAllPsus()).thenReturn(psuList);

            mockMvc.perform(get("/api/components/psus")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("Corsair RM850x"))
                    .andExpect(jsonPath("$[0].brand_name").value("Corsair"))
                    .andExpect(jsonPath("$[0].avg_price").value(149.99));
            
            verify(psuService, times(1)).getAllPsus();
        }

        @Test
        void getPsuById_ShouldReturnPsu() throws Exception {
            when(psuService.getPsuById(1L)).thenReturn(corsairPsu);

            mockMvc.perform(get("/api/components/psus/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("Corsair RM850x"))
                    .andExpect(jsonPath("$.brand_name").value("Corsair"));
            
            verify(psuService, times(1)).getPsuById(1L);
        }

        @Test
        void getPsuById_NotFound_ShouldReturn404() throws Exception {
            when(psuService.getPsuById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/psus/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(psuService, times(1)).getPsuById(999L);
        }

        @Test
        void getAllPsus_ShouldHandleEmptyList() throws Exception {
            when(psuService.getAllPsus()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/psus")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(psuService, times(1)).getAllPsus();
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredPsus_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(eq("Corsair"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("brandName", "Corsair")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Corsair"))
                    .andExpect(jsonPath("$[0].model_name").value("Corsair RM850x"));
            
            verify(psuService, times(1)).getFilteredPsus(eq("Corsair"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithFormFactorFilter_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), eq("ATX"), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(psuList);

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("formFactor", "ATX")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), eq("ATX"), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithWattageMinFilter_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), isNull(), eq(700), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairPsu, seasonicPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("wattageMin", "700")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].wattage").value(850))
                    .andExpect(jsonPath("$[1].wattage").value(750));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), isNull(), eq(700), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithPsuLengthMaxFilter_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), isNull(), isNull(), 
                                          eq(150), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(budgetPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("psuLengthMax", "150")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].psuLength").value(140));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), isNull(), isNull(), 
                                                        eq(150), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithCertificationFilter_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), isNull(), isNull(), 
                                          isNull(), eq("80+ Gold"), isNull(), isNull()))
                    .thenReturn(Arrays.asList(corsairPsu, seasonicPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("certification", "80+ Gold")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].certification").value("80+ Gold"))
                    .andExpect(jsonPath("$[1].certification").value("80+ Gold"));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), isNull(), isNull(), 
                                                        isNull(), eq("80+ Gold"), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(100.0), eq(200.0)))
                    .thenReturn(Arrays.asList(corsairPsu, seasonicPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("minPrice", "100")
                            .param("maxPrice", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(149.99))
                    .andExpect(jsonPath("$[1].avg_price").value(129.99));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), eq(100.0), eq(200.0));
        }

        @Test
        void getFilteredPsus_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(psuService.getFilteredPsus(eq("Corsair"), eq("ATX"), eq(800), 
                                          eq(170), eq("80+ Gold"), eq(100.0), eq(200.0)))
                    .thenReturn(Arrays.asList(corsairPsu));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("brandName", "Corsair")
                            .param("formFactor", "ATX")
                            .param("wattageMin", "800")
                            .param("psuLengthMax", "170")
                            .param("certification", "80+ Gold")
                            .param("minPrice", "100")
                            .param("maxPrice", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Corsair"))
                    .andExpect(jsonPath("$[0].wattage").value(850))
                    .andExpect(jsonPath("$[0].certification").value("80+ Gold"));
            
            verify(psuService, times(1)).getFilteredPsus(eq("Corsair"), eq("ATX"), eq(800), 
                                                        eq(170), eq("80+ Gold"), eq(100.0), eq(200.0));
        }

        @Test
        void getFilteredPsus_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(psuService.getFilteredPsus(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(psuList);

            mockMvc.perform(get("/api/components/psus/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(psuService, times(1)).getFilteredPsus(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredPsus_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredPsus_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(psuService.getFilteredPsus(eq("NonExistentBrand"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(psuService, times(1)).getFilteredPsus(eq("NonExistentBrand"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredPsus_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(psuService.getFilteredPsus(any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/psus/filtered")
                            .param("brandName", "Corsair")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getPsuById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(psuService.getPsuById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/psus/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
