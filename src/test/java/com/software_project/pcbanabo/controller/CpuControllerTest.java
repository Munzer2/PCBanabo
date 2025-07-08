package com.software_project.pcbanabo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.software_project.pcbanabo.service.*;
import com.software_project.pcbanabo.model.Cpu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CpuController.class)
class CpuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CpuService cpuService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cpu testCpu;
    private List<Cpu> cpuList;

    @BeforeEach
    void setUp() {
        testCpu = new Cpu();
        testCpu.setId(1L);
        testCpu.setModel_name("Intel Core i7-12700K");
        testCpu.setBrand_name("Intel");
        testCpu.setSocket("LGA1700");
        testCpu.setAverage_price(399.99);

        cpuList = Arrays.asList(testCpu);
    }

    @Test
    void getAllCpus_ShouldReturnCpuList() throws Exception {
        when(cpuService.getAllCpus()).thenReturn(cpuList);

        mockMvc.perform(get("/api/components/cpus")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Intel Core i7-12700K"))
                .andExpect(jsonPath("$[0].brand").value("Intel"))
                .andExpect(jsonPath("$[0].price").value(399.99));
    }

    // @Test
    // void getFilteredCpus_ShouldReturnFilteredResults() throws Exception {
    //     when(cpuService.getFilteredCpus(anyMap())).thenReturn(cpuList);

    //     mockMvc.perform(get("/api/components/cpus/filtered")
    //                     .param("brand", "Intel")
    //                     .param("price_gte", "300")
    //                     .param("price_lte", "500")
    //                     .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$").isArray())
    //             .andExpect(jsonPath("$[0].brand").value("Intel"))
    //             .andExpect(jsonPath("$[0].price").value(399.99));
    // }

    @Test
    void getCpuById_ShouldReturnCpu() throws Exception {
        when(cpuService.getCpuById(1L)).thenReturn(testCpu);

        mockMvc.perform(get("/api/components/cpus/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Intel Core i7-12700K"))
                .andExpect(jsonPath("$.brand").value("Intel"));
    }

    @Test
    void getCpuById_NotFound_ShouldReturn404() throws Exception {
        when(cpuService.getCpuById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/components/cpus/999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllCpus_ShouldHandleEmptyList() throws Exception {
        when(cpuService.getAllCpus()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/components/cpus")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    // @Test
    // void getFilteredCpus_WithMultipleFilters_ShouldReturnResults() throws Exception {
    //     when(cpuService.getFilteredCpus(anyMap())).thenReturn(cpuList);

    //     mockMvc.perform(get("/api/components/cpus/filtered")
    //                     .param("brand", "Intel", "AMD")
    //                     .param("socket", "LGA1700")
    //                     .param("cores_gte", "8")
    //                     .param("threads_gte", "16")
    //                     .param("price_gte", "200")
    //                     .param("price_lte", "600")
    //                     .contentType(MediaType.APPLICATION_JSON))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$").isArray());
    // }
}