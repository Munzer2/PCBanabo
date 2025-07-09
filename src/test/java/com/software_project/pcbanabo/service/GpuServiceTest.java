package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Gpu;
import com.software_project.pcbanabo.repository.GpuRepository;
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
@DisplayName("GPU Service Tests")
class GpuServiceTest {

    @Mock
    private GpuRepository gpuRepository;

    @InjectMocks
    private GpuService gpuService;

    private Gpu rtxGpu;
    private Gpu radeonGpu;
    private Gpu budgetGpu;
    private List<Gpu> gpuList;

    @BeforeEach
    void setUp() {
        rtxGpu = new Gpu();
        rtxGpu.setId(1L);
        rtxGpu.setModel_name("RTX 4070 Ti");
        rtxGpu.setBrand_name("NVIDIA");
        rtxGpu.setGpu_core("Ada Lovelace");
        rtxGpu.setVram(12);
        rtxGpu.setCardLength(336);
        rtxGpu.setAvg_price(799.99);

        radeonGpu = new Gpu();
        radeonGpu.setId(2L);
        radeonGpu.setModel_name("RX 7700 XT");
        radeonGpu.setBrand_name("AMD");
        radeonGpu.setGpu_core("RDNA 3");
        radeonGpu.setVram(12);
        radeonGpu.setTdp(245);
        radeonGpu.setCardLength(324);
        radeonGpu.setAvg_price(449.99);

        budgetGpu = new Gpu();
        budgetGpu.setId(3L);
        budgetGpu.setModel_name("GTX 1660 Super");
        budgetGpu.setBrand_name("NVIDIA");
        budgetGpu.setGpu_core("Turing");
        budgetGpu.setVram(6);
        budgetGpu.setTdp(125);
        budgetGpu.setCardLength(267);
        budgetGpu.setAvg_price(229.99);

        gpuList = Arrays.asList(rtxGpu, radeonGpu, budgetGpu);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllGpus_ShouldReturnAllGpus() {
            when(gpuRepository.findAll()).thenReturn(gpuList);

            List<Gpu> result = gpuService.getAllGpus();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(rtxGpu, radeonGpu, budgetGpu);
            verify(gpuRepository, times(1)).findAll();
        }

        @Test
        void getGpuById_ExistingId_ShouldReturnGpu() {
            when(gpuRepository.findById(1L)).thenReturn(Optional.of(rtxGpu));

            Gpu result = gpuService.getGpuById(1L);

            assertThat(result).isEqualTo(rtxGpu);
            verify(gpuRepository, times(1)).findById(1L);
        }

        @Test
        void getGpuById_NonExistingId_ShouldReturnNull() {
            when(gpuRepository.findById(999L)).thenReturn(Optional.empty());

            Gpu result = gpuService.getGpuById(999L);

            assertThat(result).isNull();
            verify(gpuRepository, times(1)).findById(999L);
        }

        @Test
        void insertGpu_ShouldSaveGpu() {
            gpuService.insertGpu(rtxGpu);

            verify(gpuRepository, times(1)).save(rtxGpu);
        }

        @Test
        void deleteGpuById_ShouldDeleteGpu() {
            gpuService.deleteGpuById(1L);

            verify(gpuRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateGpu_ShouldSaveGpu() {
            gpuService.updateGpu(rtxGpu, 1L);

            verify(gpuRepository, times(1)).save(rtxGpu);
        }

        @Test
        void getGpuByModelName_ExistingModel_ShouldReturnGpu() {
            when(gpuRepository.findByModelName("RTX 4070 Ti")).thenReturn(Optional.of(rtxGpu));

            Optional<Gpu> result = gpuService.getGpuByModelName("RTX 4070 Ti");

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("RTX 4070 Ti");
            verify(gpuRepository, times(1)).findByModelName("RTX 4070 Ti");
        }

        @Test
        void getGpuByModelName_NonExistingModel_ShouldReturnEmpty() {
            when(gpuRepository.findByModelName("Non-existent GPU")).thenReturn(Optional.empty());

            Optional<Gpu> result = gpuService.getGpuByModelName("Non-existent GPU");

            assertThat(result).isEmpty();
            verify(gpuRepository, times(1)).findByModelName("Non-existent GPU");
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getGpusByCardLengthLessThanEqual_ShouldReturnFilteredResults() {
            when(gpuRepository.findByCardLengthLessThanEqual(300)).thenReturn(Arrays.asList(budgetGpu));

            List<Gpu> result = gpuService.getGpusByCardLengthLessThanEqual(300);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getCardLength()).isLessThanOrEqualTo(300);
            verify(gpuRepository, times(1)).findByCardLengthLessThanEqual(300);
        }

        @Test
        void getGpusByCardLengthLessThanEqual_WithNoMatches_ShouldReturnEmpty() {
            when(gpuRepository.findByCardLengthLessThanEqual(200)).thenReturn(Collections.emptyList());

            List<Gpu> result = gpuService.getGpusByCardLengthLessThanEqual(200);

            assertThat(result).isEmpty();
            verify(gpuRepository, times(1)).findByCardLengthLessThanEqual(200);
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithBrandFilter_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(rtxGpu, budgetGpu));

            List<Gpu> result = gpuService.getFilteredGpus("NVIDIA", null, null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(gpu -> "NVIDIA".equals(gpu.getBrand_name()));
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithGpuCoreFilter_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(rtxGpu));

            List<Gpu> result = gpuService.getFilteredGpus(null, "Ada Lovelace", null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getGpu_core()).isEqualTo("Ada Lovelace");
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithVramFilter_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(rtxGpu, radeonGpu));

            List<Gpu> result = gpuService.getFilteredGpus(null, null, 10, 
                                                         null, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(gpu -> gpu.getVram() >= 10);
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithTdpFilter_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(budgetGpu, radeonGpu));

            List<Gpu> result = gpuService.getFilteredGpus(null, null, null, 
                                                         250, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(gpu -> gpu.getTdp() <= 250);
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithCardLengthFilter_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(budgetGpu, radeonGpu));

            List<Gpu> result = gpuService.getFilteredGpus(null, null, null, 
                                                         null, 330, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(gpu -> gpu.getCardLength() <= 330);
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithPriceRange_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(radeonGpu));

            List<Gpu> result = gpuService.getFilteredGpus(null, null, null, 
                                                         null, null, 400.0, 500.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getAvg_price()).isBetween(400.0, 500.0);
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithMultipleFilters_ShouldReturnFilteredResults() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(rtxGpu));

            List<Gpu> result = gpuService.getFilteredGpus("NVIDIA", "Ada Lovelace", 10, 
                                                         300, 350, 700.0, 900.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("NVIDIA");
            assertThat(result.get(0).getGpu_core()).isEqualTo("Ada Lovelace");
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithNoFilters_ShouldReturnAllGpus() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(gpuList);

            List<Gpu> result = gpuService.getFilteredGpus(null, null, null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(3);
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithEmptyResults_ShouldReturnEmptyList() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Gpu> result = gpuService.getFilteredGpus("NonExistentBrand", null, null, 
                                                         null, null, null, null);

            assertThat(result).isEmpty();
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        void getAllGpus_EmptyRepository_ShouldReturnEmptyList() {
            when(gpuRepository.findAll()).thenReturn(Collections.emptyList());

            List<Gpu> result = gpuService.getAllGpus();

            assertThat(result).isEmpty();
            verify(gpuRepository, times(1)).findAll();
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredGpus_WithExtremeValues_ShouldHandleGracefully() {
            when(gpuRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Gpu> result = gpuService.getFilteredGpus(null, null, 999, 
                                                         1, 1, 10000.0, 20000.0);

            assertThat(result).isEmpty();
            verify(gpuRepository, times(1)).findAll(any(Specification.class));
        }
    }
}
