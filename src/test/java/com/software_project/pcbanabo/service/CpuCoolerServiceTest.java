package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.CpuCooler;
import com.software_project.pcbanabo.repository.CpuCoolerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CPU Cooler Service Tests")
class CpuCoolerServiceTest {

    @Mock
    private CpuCoolerRepository cpuCoolerRepository;

    @InjectMocks
    private CpuCoolerService cpuCoolerService;

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
        void getAllCpuCoolers_ShouldReturnAllCoolers() {
            when(cpuCoolerRepository.findAll()).thenReturn(coolerList);

            List<CpuCooler> result = cpuCoolerService.getAllCpuCoolers();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(noctuaCooler, corsairCooler, budgetCooler);
            verify(cpuCoolerRepository, times(1)).findAll();
        }

        @Test
        void getCpuCoolerById_ExistingId_ShouldReturnCooler() {
            when(cpuCoolerRepository.findById(1L)).thenReturn(Optional.of(noctuaCooler));

            CpuCooler result = cpuCoolerService.getCpuCoolerById(1L);

            assertThat(result).isEqualTo(noctuaCooler);
            verify(cpuCoolerRepository, times(1)).findById(1L);
        }

        @Test
        void getCpuCoolerById_NonExistingId_ShouldReturnNull() {
            when(cpuCoolerRepository.findById(999L)).thenReturn(Optional.empty());

            CpuCooler result = cpuCoolerService.getCpuCoolerById(999L);

            assertThat(result).isNull();
            verify(cpuCoolerRepository, times(1)).findById(999L);
        }

        @Test
        void insertCpuCooler_ShouldSaveCooler() {
            cpuCoolerService.insertCpuCooler(noctuaCooler);

            verify(cpuCoolerRepository, times(1)).save(noctuaCooler);
        }

        @Test
        void deleteCpuCoolerById_ShouldDeleteCooler() {
            cpuCoolerService.deleteCpuCoolerById(1L);

            verify(cpuCoolerRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateCpuCooler_ShouldSaveCooler() {
            cpuCoolerService.updateCpuCooler(noctuaCooler, 1L);

            verify(cpuCoolerRepository, times(1)).save(noctuaCooler);
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getCpuCoolersByTowerHeight_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findByTowerHeight(165)).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getCpuCoolersByTowerHeight(165);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTowerHeight()).isEqualTo(165);
            verify(cpuCoolerRepository, times(1)).findByTowerHeight(165);
        }

        @Test
        void getCpuCoolersByTowerHeightLessThanEqual_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findByTowerHeightLessThanEqual(160)).thenReturn(Arrays.asList(budgetCooler));

            List<CpuCooler> result = cpuCoolerService.getCpuCoolersByTowerHeightLessThanEqual(160);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTowerHeight()).isLessThanOrEqualTo(160);
            verify(cpuCoolerRepository, times(1)).findByTowerHeightLessThanEqual(160);
        }

        @Test
        void getCpuCoolersByCoolingCapacity_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findByCoolingCapacity(220)).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getCpuCoolersByCoolingCapacity(220);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getCoolingCapacity()).isEqualTo(220);
            verify(cpuCoolerRepository, times(1)).findByCoolingCapacity(220);
        }

        @Test
        void getCpuCoolersByCoolingCapacityIsGreaterThanEqual_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findByCoolingCapacityIsGreaterThanEqual(200)).thenReturn(Arrays.asList(noctuaCooler, corsairCooler));

            List<CpuCooler> result = cpuCoolerService.getCpuCoolersByCoolingCapacityIsGreaterThanEqual(200);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getCoolingCapacity()).isGreaterThanOrEqualTo(200);
            verify(cpuCoolerRepository, times(1)).findByCoolingCapacityIsGreaterThanEqual(200);
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithBrandFilter_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers("Noctua", null, null, 
                                                                          null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("Noctua");
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithCoolerTypeFilter_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler, budgetCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, "Air", null, 
                                                                          null, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getCooler_type()).isEqualTo("Air");
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithTowerHeightFilter_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, null, 165, 
                                                                          null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getTowerHeight()).isEqualTo(165);
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithRadiatorSizeFilter_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(corsairCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, null, null, 
                                                                          360, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getRadiator_size()).isEqualTo(360);
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithCoolingCapacityFilter_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler, corsairCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, null, null, 
                                                                          null, 200, null, null);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getCoolingCapacity()).isGreaterThanOrEqualTo(200);
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithPriceRange_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, null, null, 
                                                                          null, null, 80.0, 150.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getAvg_price()).isBetween(80.0, 150.0);
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithMultipleFilters_ShouldReturnFilteredResults() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(noctuaCooler));

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers("Noctua", "Air", 165, 
                                                                          null, 220, 80.0, 120.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("Noctua");
            assertThat(result.get(0).getCooler_type()).isEqualTo("Air");
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithNoFilters_ShouldReturnAllCoolers() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(coolerList);

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers(null, null, null, 
                                                                          null, null, null, null);

            assertThat(result).hasSize(3);
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCpuCoolers_WithEmptyResults_ShouldReturnEmptyList() {
            when(cpuCoolerRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<CpuCooler> result = cpuCoolerService.getFilteredCpuCoolers("NonExistentBrand", null, null, 
                                                                          null, null, null, null);

            assertThat(result).isEmpty();
            verify(cpuCoolerRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Specification Helper Methods")
    class SpecificationHelperMethods {

        @Test
        void stringEquals_WithNullValue_ShouldReturnNull() {
            Specification<CpuCooler> spec = CpuCoolerService.stringEquals("brand_name", null);
            assertThat(spec).isNull();
        }

        @Test
        void integerEquals_WithNullValue_ShouldReturnNull() {
            Specification<CpuCooler> spec = CpuCoolerService.integerEquals("towerHeight", null);
            assertThat(spec).isNull();
        }

        @Test
        void rangeBetween_WithNullValues_ShouldReturnNull() {
            Specification<CpuCooler> spec = CpuCoolerService.rangeBetween("avg_price", null, null);
            assertThat(spec).isNull();
        }
    }
}
