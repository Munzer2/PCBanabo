package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.repository.CpuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ComponentServiceTest {

    @Mock
    private CpuRepository cpuRepository;

    @InjectMocks
    private CpuService componentService;

    private Cpu intelCpu;
    private Cpu amdCpu;
    private List<Cpu> cpuList;

    @BeforeEach
    void setUp() {
        intelCpu = new Cpu();
        intelCpu.setId(1L);
        intelCpu.setModel_name("Intel Core i7-12700K");
        intelCpu.setBrand_name("Intel");
        intelCpu.setSocket("LGA1700");
        intelCpu.setAverage_price(399.99);

        amdCpu = new Cpu();
        amdCpu.setId(2L);
        amdCpu.setModel_name("AMD Ryzen 7 5800X");
        amdCpu.setBrand_name("AMD");
        amdCpu.setSocket("AM4");
        amdCpu.setAverage_price(349.99);

        cpuList = Arrays.asList(intelCpu, amdCpu);
    }

    @Test
    void getAllCpus_ShouldReturnAllCpus() {
        when(cpuRepository.findAll()).thenReturn(cpuList);

        List<Cpu> result = componentService.getAllCpus();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(intelCpu, amdCpu);
        verify(cpuRepository, times(1)).findAll();
    }

    @Test
    void getCpuById_ExistingId_ShouldReturnCpu() {
        when(cpuRepository.findById(1L)).thenReturn(Optional.of(intelCpu));

        Cpu result = componentService.getCpuById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getModel_name()).isEqualTo("Intel Core i7-12700K");
        assertThat(result.getBrand_name()).isEqualTo("Intel");
        verify(cpuRepository, times(1)).findById(1L);
    }

    @Test
    void getCpuById_NonExistingId_ShouldReturnNull() {
        when(cpuRepository.findById(999L)).thenReturn(Optional.empty());

        Cpu result = componentService.getCpuById(999L);

        assertThat(result).isNull();
        verify(cpuRepository, times(1)).findById(999L);
    }

    // @Test
    // void getFilteredCpus_WithBrandFilter_ShouldReturnFilteredResults() {
    //     Map<String, Object> filters = new HashMap<>();
    //     filters.put("brand", Arrays.asList("Intel"));
        
    //     when(cpuRepository.findByBrandIn(anyList())).thenReturn(Arrays.asList(intelCpu));

    //     List<Cpu> result = componentService.getFilteredCpus(filters);

    //     assertThat(result).hasSize(1);
    //     assertThat(result.get(0).getBrand_name()).isEqualTo("Intel");
    //     verify(cpuRepository, times(1)).findByBrandIn(Arrays.asList("Intel"));
    // }

    // @Test
    // void getFilteredCpus_WithPriceRange_ShouldReturnFilteredResults() {
    //     Map<String, Object> filters = new HashMap<>();
    //     filters.put("price_gte", "300");
    //     filters.put("price_lte", "500");
        
    //     when(cpuRepository.findByPriceBetween(300.0, 500.0)).thenReturn(cpuList);

    //     List<Cpu> result = componentService.getFilteredCpus(filters);

    //     assertThat(result).hasSize(2);
    //     assertThat(result).allMatch(cpu -> cpu.getPrice() >= 300.0 && cpu.getPrice() <= 500.0);
    //     verify(cpuRepository, times(1)).findByPriceBetween(300.0, 500.0);
    // }

    // @Test
    // void getFilteredCpus_WithSocketFilter_ShouldReturnFilteredResults() {
    //     Map<String, Object> filters = new HashMap<>();
    //     filters.put("socket", Arrays.asList("AM4"));
        
    //     when(cpuRepository.findBySocketIn(anyList())).thenReturn(Arrays.asList(amdCpu));

    //     List<Cpu> result = componentService.getFilteredCpus(filters);

    //     assertThat(result).hasSize(1);
    //     assertThat(result.get(0).getSocket()).isEqualTo("AM4");
    //     verify(cpuRepository, times(1)).findBySocketIn(Arrays.asList("AM4"));
    // }

    // @Test
    // void getFilteredCpus_WithNoFilters_ShouldReturnAllCpus() {
    //     Map<String, Object> filters = new HashMap<>();
        
    //     when(cpuRepository.findAll()).thenReturn(cpuList);

    //     List<Cpu> result = componentService.getFilteredCpus(filters);

    //     assertThat(result).hasSize(2);
    //     verify(cpuRepository, times(1)).findAll();
    // }
}