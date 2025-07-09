package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.service.GpuService;
import com.software_project.pcbanabo.model.Gpu;
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

@WebMvcTest(GpuController.class)
@DisplayName("GPU Controller Tests")
class GpuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GpuService gpuService;

    private Gpu nvidiaGpu;
    private Gpu amdGpu;
    private Gpu budgetGpu;
    private List<Gpu> gpuList;

    @BeforeEach
    void setUp() {
        nvidiaGpu = new Gpu();
        nvidiaGpu.setId(1L);
        nvidiaGpu.setModel_name("NVIDIA GeForce RTX 4080");
        nvidiaGpu.setBrand_name("NVIDIA");
        nvidiaGpu.setGpu_core("Ada Lovelace");
        nvidiaGpu.setVram(16);
        nvidiaGpu.setAvg_price(1199.99);
        nvidiaGpu.setTdp(320);
        nvidiaGpu.setCardLength(310);

        amdGpu = new Gpu();
        amdGpu.setId(2L);
        amdGpu.setModel_name("AMD Radeon RX 7900 XTX");
        amdGpu.setBrand_name("AMD");
        amdGpu.setGpu_core("RDNA 3");
        amdGpu.setVram(24);
        amdGpu.setAvg_price(999.99);
        amdGpu.setTdp(355);
        amdGpu.setCardLength(320);

        budgetGpu = new Gpu();
        budgetGpu.setId(3L);
        budgetGpu.setModel_name("NVIDIA GeForce RTX 4060");
        budgetGpu.setBrand_name("NVIDIA");
        budgetGpu.setGpu_core("Ada Lovelace");
        budgetGpu.setVram(8);
        budgetGpu.setAvg_price(299.99);
        budgetGpu.setTdp(115);
        budgetGpu.setCardLength(245);

        gpuList = Arrays.asList(nvidiaGpu, amdGpu, budgetGpu);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllGpus_ShouldReturnGpuList() throws Exception {
            when(gpuService.getAllGpus()).thenReturn(gpuList);

            mockMvc.perform(get("/api/components/gpus")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3))
                    .andExpect(jsonPath("$[0].model_name").value("NVIDIA GeForce RTX 4080"))
                    .andExpect(jsonPath("$[0].brand_name").value("NVIDIA"))
                    .andExpect(jsonPath("$[0].avg_price").value(1199.99));
            
            verify(gpuService, times(1)).getAllGpus();
        }

        @Test
        void getGpuById_ShouldReturnGpu() throws Exception {
            when(gpuService.getGpuById(1L)).thenReturn(nvidiaGpu);

            mockMvc.perform(get("/api/components/gpus/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.model_name").value("NVIDIA GeForce RTX 4080"))
                    .andExpect(jsonPath("$.brand_name").value("NVIDIA"));
            
            verify(gpuService, times(1)).getGpuById(1L);
        }

        @Test
        void getGpuById_NotFound_ShouldReturn404() throws Exception {
            when(gpuService.getGpuById(999L)).thenReturn(null);

            mockMvc.perform(get("/api/components/gpus/999")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
            
            verify(gpuService, times(1)).getGpuById(999L);
        }

        @Test
        void getAllGpus_ShouldHandleEmptyList() throws Exception {
            when(gpuService.getAllGpus()).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/gpus")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(gpuService, times(1)).getAllGpus();
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredGpus_WithBrandFilter_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(eq("NVIDIA"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(nvidiaGpu, budgetGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("brandName", "NVIDIA")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("NVIDIA"))
                    .andExpect(jsonPath("$[1].brand_name").value("NVIDIA"));
            
            verify(gpuService, times(1)).getFilteredGpus(eq("NVIDIA"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithPriceRange_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(500.0), eq(1500.0)))
                    .thenReturn(Arrays.asList(nvidiaGpu, amdGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("minPrice", "500")
                            .param("maxPrice", "1500")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].avg_price").value(1199.99))
                    .andExpect(jsonPath("$[1].avg_price").value(999.99));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), eq(500.0), eq(1500.0));
        }

        @Test
        void getFilteredGpus_WithVramFilter_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), eq(16), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(nvidiaGpu, amdGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("vramMin", "16")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].vram").value(16))
                    .andExpect(jsonPath("$[1].vram").value(24));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), eq(16), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithTdpMax_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), isNull(), 
                                          eq(320), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(nvidiaGpu, budgetGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("tdpMax", "320")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].tdp").value(320))
                    .andExpect(jsonPath("$[1].tdp").value(115));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), isNull(), 
                                                        eq(320), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithCardLengthMax_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), isNull(), 
                                          isNull(), eq(300), isNull(), isNull()))
                    .thenReturn(Arrays.asList(budgetGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("cardLengthMax", "300")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].cardLength").value(245));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), isNull(), 
                                                        isNull(), eq(300), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithGpuCoreFilter_ShouldReturnFilteredResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), eq("Ada Lovelace"), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Arrays.asList(nvidiaGpu, budgetGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("gpuCore", "Ada Lovelace")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].gpu_core").value("Ada Lovelace"));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), eq("Ada Lovelace"), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithMultipleFilters_ShouldReturnResults() throws Exception {
            when(gpuService.getFilteredGpus(eq("NVIDIA"), eq("Ada Lovelace"), eq(8), 
                                          eq(350), eq(320), eq(200.0), eq(1500.0)))
                    .thenReturn(Arrays.asList(nvidiaGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("brandName", "NVIDIA")
                            .param("gpuCore", "Ada Lovelace")
                            .param("vramMin", "8")
                            .param("tdpMax", "350")
                            .param("cardLengthMax", "320")
                            .param("minPrice", "200")
                            .param("maxPrice", "1500")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].brand_name").value("NVIDIA"))
                    .andExpect(jsonPath("$[0].gpu_core").value("Ada Lovelace"))
                    .andExpect(jsonPath("$[0].avg_price").value(1199.99));
            
            verify(gpuService, times(1)).getFilteredGpus(eq("NVIDIA"), eq("Ada Lovelace"), eq(8), 
                                                        eq(350), eq(320), eq(200.0), eq(1500.0));
        }

        @Test
        void getFilteredGpus_WithNoFilters_ShouldReturnAllResults() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(gpuList);

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$.length()").value(3));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithInvalidParameters_ShouldHandleGracefully() throws Exception {
            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("minPrice", "invalid")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
        
        @Test
        void getFilteredGpus_EmptyResults_ShouldReturnEmptyArray() throws Exception {
            when(gpuService.getFilteredGpus(eq("NonExistentBrand"), isNull(), isNull(), 
                                          isNull(), isNull(), isNull(), isNull()))
                    .thenReturn(Collections.emptyList());

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("brandName", "NonExistentBrand")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$").isEmpty());
            
            verify(gpuService, times(1)).getFilteredGpus(eq("NonExistentBrand"), isNull(), isNull(), 
                                                        isNull(), isNull(), isNull(), isNull());
        }

        @Test
        void getFilteredGpus_WithBudgetRange_ShouldReturnBudgetGpus() throws Exception {
            when(gpuService.getFilteredGpus(isNull(), isNull(), isNull(), 
                                          isNull(), isNull(), eq(200.0), eq(400.0)))
                    .thenReturn(Arrays.asList(budgetGpu));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("minPrice", "200")
                            .param("maxPrice", "400")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].model_name").value("NVIDIA GeForce RTX 4060"));
            
            verify(gpuService, times(1)).getFilteredGpus(isNull(), isNull(), isNull(), 
                                                        isNull(), isNull(), eq(200.0), eq(400.0));
        }
    }

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        void getFilteredGpus_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(gpuService.getFilteredGpus(any(), any(), any(), any(), any(), any(), any()))
                    .thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/gpus/filtered")
                            .param("brandName", "NVIDIA")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }

        @Test
        void getGpuById_ServiceThrowsException_ShouldReturn500() throws Exception {
            when(gpuService.getGpuById(1L)).thenThrow(new RuntimeException("Database error"));

            mockMvc.perform(get("/api/components/gpus/1")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
        }
    }
}
