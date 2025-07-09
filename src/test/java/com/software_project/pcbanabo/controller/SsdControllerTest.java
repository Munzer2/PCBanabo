package com.software_project.pcbanabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software_project.pcbanabo.service.SsdService;
import com.software_project.pcbanabo.model.Ssd;
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

@WebMvcTest(SsdController.class)
@DisplayName("SSD Controller Tests")
class SsdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SsdService ssdService;

    @Autowired
    private ObjectMapper objectMapper;

    private Ssd samsungSsd;
    private Ssd wdSsd;
    private Ssd budgetSsd;
    private List<Ssd> ssdList;

    @BeforeEach
    void setUp() {
        samsungSsd = new Ssd();
        samsungSsd.setId(1L);
        samsungSsd.setModel_name("Samsung 980 PRO 1TB");
        samsungSsd.setBrand_name("Samsung");
        samsungSsd.setCapacity("1TB");
        samsungSsd.setForm_factor("M.2");
        samsungSsd.setPcie_gen("PCIe 4.0");
        samsungSsd.setSeq_read(7000);
        samsungSsd.setSeq_write(5000);
        samsungSsd.setDram_cache(true);
        samsungSsd.setAvg_price(129.99);

        wdSsd = new Ssd();
        wdSsd.setId(2L);
        wdSsd.setModel_name("WD Black SN850X 2TB");
        wdSsd.setBrand_name("Western Digital");
        wdSsd.setCapacity("2TB");
        wdSsd.setForm_factor("M.2");
        wdSsd.setPcie_gen("PCIe 4.0");
        wdSsd.setSeq_read(7300);
        wdSsd.setSeq_write(6600);
        wdSsd.setDram_cache(true);
        wdSsd.setAvg_price(189.99);

        budgetSsd = new Ssd();
        budgetSsd.setId(3L);
        budgetSsd.setModel_name("Kingston NV2 500GB");
        budgetSsd.setBrand_name("Kingston");
        budgetSsd.setCapacity("500GB");
        budgetSsd.setForm_factor("M.2");
        budgetSsd.setPcie_gen("PCIe 4.0");
        budgetSsd.setSeq_read(3500);
        budgetSsd.setSeq_write(2100);
        budgetSsd.setDram_cache(false);
        budgetSsd.setAvg_price(39.99);

        ssdList = Arrays.asList(samsungSsd, wdSsd, budgetSsd);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllSsds_ShouldReturnSsdList() throws Exception {
            when(ssdService.getAllSsds()).thenReturn(ssdList);

            mockMvc.perform(get("/api/components/ssds")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("Samsung 980 PRO 1TB"))
                    .andExpect(jsonPath("$[0].brand_name").value("Samsung"))
                    .andExpect(jsonPath("$[0].avg_price").value(129.99));
            
            verify(ssdService, times(1)).getAllSsds();
        }

        @Test
        void getSsdById_ShouldReturnSsd() throws Exception {
            when(ssdService.getSsdById(1L)).thenReturn(samsungSsd);

            mockMvc.perform(get("/api/components/ssds/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("Samsung 980 PRO 1TB"))
                    .andExpect(jsonPath("$.brand_name").value("Samsung"));
            
            verify(ssdService, times(1)).getSsdById(1L);
        }

        @Test
        void getSsdById_NotFound_ShouldReturn404() throws Exception {
            when(ssdService.getSsdById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/ssds/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(ssdService, times(1)).getSsdById(999L);
        }

        @Test
        void getAllSsds_ShouldHandleEmptyList() throws Exception {
            when(ssdService.getAllSsds()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/ssds")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(ssdService, times(1)).getAllSsds();
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredSsds_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(eq("Samsung"), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(samsungSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("brandName", "Samsung")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Samsung"))
                    .andExpect(jsonPath("$[0].model_name").value("Samsung 980 PRO 1TB"));
            
            verify(ssdService, times(1)).getFilteredSsds(eq("Samsung"), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithCapacityFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), eq("1TB"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(samsungSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("capacity", "1TB")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].capacity").value("1TB"));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), eq("1TB"), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithFormFactorFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), eq("M.2"), isNull(), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(ssdList);

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("formFactor", "M.2")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].form_factor").value("M.2"));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), eq("M.2"), isNull(), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithPcieGenFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), eq("PCIe 4.0"), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(ssdList);

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("pcieGen", "PCIe 4.0")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].pcie_gen").value("PCIe 4.0"));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), eq("PCIe 4.0"), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithSeqReadFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          eq(5000), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(samsungSsd, wdSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("seqReadMin", "5000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].seq_read").value(7000));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       eq(5000), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithSeqWriteFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), eq(4000), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(samsungSsd, wdSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("seqWriteMin", "4000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].seq_write").exists());
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), eq(4000), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithDramCacheFilter_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(true), isNull(), isNull()))
                    .thenReturn(Arrays.asList(samsungSsd, wdSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("dramCache", "true")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].dram_cache").value(true));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), eq(true), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), eq(100.0), eq(200.0)))
                    .thenReturn(Arrays.asList(samsungSsd, wdSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("minPrice", "100")
                            .param("maxPrice", "200")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(129.99));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), eq(100.0), eq(200.0));
        }

        @Test
        void getFilteredSsds_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(ssdService.getFilteredSsds(eq("Samsung"), eq("1TB"), eq("M.2"), eq("PCIe 4.0"), 
                                          eq(5000), eq(3000), eq(true), eq(100.0), eq(150.0)))
                    .thenReturn(Arrays.asList(samsungSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("brandName", "Samsung")
                            .param("capacity", "1TB")
                            .param("formFactor", "M.2")
                            .param("pcieGen", "PCIe 4.0")
                            .param("seqReadMin", "5000")
                            .param("seqWriteMin", "3000")
                            .param("dramCache", "true")
                            .param("minPrice", "100")
                            .param("maxPrice", "150")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("Samsung"))
                    .andExpect(jsonPath("$[0].capacity").value("1TB"))
                    .andExpect(jsonPath("$[0].avg_price").value(129.99));
            
            verify(ssdService, times(1)).getFilteredSsds(eq("Samsung"), eq("1TB"), eq("M.2"), eq("PCIe 4.0"), 
                                                       eq(5000), eq(3000), eq(true), eq(100.0), eq(150.0));
        }

        @Test
        void getFilteredSsds_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(ssdList);

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithPriceRangeEdgeCases_ShouldWork() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), eq(0.0), eq(1000.0)))
                    .thenReturn(ssdList);

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("minPrice", "0")
                            .param("maxPrice", "1000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), eq(0.0), eq(1000.0));
        }

        @Test
        void getFilteredSsds_WithBooleanParameters_ShouldWork() throws Exception {
            when(ssdService.getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(false), isNull(), isNull()))
                    .thenReturn(Arrays.asList(budgetSsd));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("dramCache", "false")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray());
            
            verify(ssdService, times(1)).getFilteredSsds(isNull(), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), eq(false), isNull(), isNull());
        }

        @Test
        void getFilteredSsds_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredSsds_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(ssdService.getFilteredSsds(eq("NonExistentBrand"), isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(ssdService, times(1)).getFilteredSsds(eq("NonExistentBrand"), isNull(), isNull(), isNull(), 
                                                       isNull(), isNull(), isNull(), isNull(), isNull());
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredSsds_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(ssdService.getFilteredSsds(any(), any(), any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/ssds/filtered")
                            .param("brandName", "Samsung")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getSsdById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(ssdService.getSsdById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/ssds/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
