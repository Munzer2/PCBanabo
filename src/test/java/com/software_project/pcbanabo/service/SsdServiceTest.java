package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ssd;
import com.software_project.pcbanabo.repository.SsdRepository;
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
@DisplayName("SSD Service Tests")
class SsdServiceTest {

    @Mock
    private SsdRepository ssdRepository;

    @InjectMocks
    private SsdService ssdService;

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
        void getAllSsds_ShouldReturnAllSsds() {
            when(ssdRepository.findAll()).thenReturn(ssdList);

            List<Ssd> result = ssdService.getAllSsds();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(samsungSsd, wdSsd, budgetSsd);
            verify(ssdRepository, times(1)).findAll();
        }

        @Test
        void getSsdById_ExistingId_ShouldReturnSsd() {
            when(ssdRepository.findById(1L)).thenReturn(Optional.of(samsungSsd));

            Ssd result = ssdService.getSsdById(1L);

            assertThat(result).isEqualTo(samsungSsd);
            verify(ssdRepository, times(1)).findById(1L);
        }

        @Test
        void getSsdById_NonExistingId_ShouldReturnNull() {
            when(ssdRepository.findById(999L)).thenReturn(Optional.empty());

            Ssd result = ssdService.getSsdById(999L);

            assertThat(result).isNull();
            verify(ssdRepository, times(1)).findById(999L);
        }

        @Test
        void insertSsd_ShouldSaveSsd() {
            ssdService.insertSsd(samsungSsd);

            verify(ssdRepository, times(1)).save(samsungSsd);
        }

        @Test
        void deleteSsdById_ShouldDeleteSsd() {
            ssdService.deleteSsdById(1L);

            verify(ssdRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateSsd_ShouldSaveSsd() {
            ssdService.updateSsd(samsungSsd, 1L);

            verify(ssdRepository, times(1)).save(samsungSsd);
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredSsds_WithBrandFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd));

            List<Ssd> result = ssdService.getFilteredSsds("Samsung", null, null, null, 
                                                         null, null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("Samsung");
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithCapacityFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd));

            List<Ssd> result = ssdService.getFilteredSsds(null, "1TB", null, null, 
                                                         null, null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getCapacity()).isEqualTo("1TB");
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithFormFactorFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(ssdList);

            List<Ssd> result = ssdService.getFilteredSsds(null, null, "M.2", null, 
                                                         null, null, null, null, null);

            assertThat(result).hasSize(3);
            assertThat(result.get(0).getForm_factor()).isEqualTo("M.2");
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithPcieGenFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(ssdList);

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, "PCIe 4.0", 
                                                         null, null, null, null, null);

            assertThat(result).hasSize(3);
            assertThat(result.get(0).getPcie_gen()).isEqualTo("PCIe 4.0");
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithSeqReadFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd, wdSsd));

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, null, 
                                                         5000, null, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getSeq_read()).isGreaterThanOrEqualTo(5000);
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithSeqWriteFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd, wdSsd));

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, null, 
                                                         null, 4000, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getSeq_write()).isGreaterThanOrEqualTo(4000);
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithDramCacheFilter_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd, wdSsd));

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, null, 
                                                         null, null, true, null, null);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getDram_cache()).isTrue();
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithPriceRange_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd, wdSsd));

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, null, 
                                                         null, null, null, 100.0, 200.0);

            assertThat(result).hasSize(2);
            assertThat(result.get(0).getAvg_price()).isBetween(100.0, 200.0);
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithMultipleFilters_ShouldReturnFilteredResults() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(samsungSsd));

            List<Ssd> result = ssdService.getFilteredSsds("Samsung", "1TB", "M.2", "PCIe 4.0", 
                                                         5000, 3000, true, 100.0, 150.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("Samsung");
            assertThat(result.get(0).getCapacity()).isEqualTo("1TB");
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithNoFilters_ShouldReturnAllSsds() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(ssdList);

            List<Ssd> result = ssdService.getFilteredSsds(null, null, null, null, 
                                                         null, null, null, null, null);

            assertThat(result).hasSize(3);
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getFilteredSsds_WithEmptyResults_ShouldReturnEmptyList() {
            when(ssdRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Ssd> result = ssdService.getFilteredSsds("NonExistentBrand", null, null, null, 
                                                         null, null, null, null, null);

            assertThat(result).isEmpty();
            verify(ssdRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Specification Helper Methods")
    class SpecificationHelperMethods {

        @Test
        void stringEquals_WithNullValue_ShouldReturnNull() {
            Specification<Ssd> spec = SsdService.stringEquals("brand_name", null);
            assertThat(spec).isNull();
        }

        @Test
        void integerGreaterThanOrEqual_WithNullValue_ShouldReturnNull() {
            Specification<Ssd> spec = SsdService.integerGreaterThanOrEqual("seq_read", null);
            assertThat(spec).isNull();
        }

        @Test
        void booleanEquals_WithNullValue_ShouldReturnNull() {
            Specification<Ssd> spec = SsdService.booleanEquals("dram_cache", null);
            assertThat(spec).isNull();
        }

        @Test
        void rangeBetween_WithNullValues_ShouldReturnNull() {
            Specification<Ssd> spec = SsdService.rangeBetween("avg_price", null, null);
            assertThat(spec).isNull();
        }
    }
}
