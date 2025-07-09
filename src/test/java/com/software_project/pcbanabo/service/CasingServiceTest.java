package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.repository.CasingRepository;
import com.software_project.pcbanabo.repository.MotherboardRepository;
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
@DisplayName("Casing Service Tests")
class CasingServiceTest {

    @Mock
    private CasingRepository casingRepository;

    @Mock
    private MotherboardRepository motherboardRepository;

    @InjectMocks
    private CasingService casingService;

    private Casing midTowerCase;
    private Casing fullTowerCase;
    private Casing miniItxCase;
    private List<Casing> casingList;

    @BeforeEach
    void setUp() {
        midTowerCase = new Casing();
        midTowerCase.setId(1L);
        midTowerCase.setModel_name("Fractal Design Define 7");
        midTowerCase.setBrand_name("Fractal Design");
        midTowerCase.setMotherboardSupport("ATX");
        midTowerCase.setPsuClearance(180);
        midTowerCase.setGpuClearance(440);
        midTowerCase.setCpuClearance(185);
        midTowerCase.setAvg_price(179.99);

        fullTowerCase = new Casing();
        fullTowerCase.setId(2L);
        fullTowerCase.setModel_name("Corsair Obsidian 1000D");
        fullTowerCase.setBrand_name("Corsair");
        fullTowerCase.setMotherboardSupport("E-ATX");
        fullTowerCase.setPsuClearance(230);
        fullTowerCase.setGpuClearance(540);
        fullTowerCase.setCpuClearance(200);
        fullTowerCase.setAvg_price(549.99);

        miniItxCase = new Casing();
        miniItxCase.setId(3L);
        miniItxCase.setModel_name("NZXT H1 Elite");
        miniItxCase.setBrand_name("NZXT");
        miniItxCase.setMotherboardSupport("Mini-ITX");
        miniItxCase.setPsuClearance(140);
        miniItxCase.setGpuClearance(324);
        miniItxCase.setCpuClearance(165);
        miniItxCase.setAvg_price(399.99);

        casingList = Arrays.asList(midTowerCase, fullTowerCase, miniItxCase);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllCasings_ShouldReturnAllCasings() {
            when(casingRepository.findAll()).thenReturn(casingList);

            List<Casing> result = casingService.getAllCasings();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(midTowerCase, fullTowerCase, miniItxCase);
            verify(casingRepository, times(1)).findAll();
        }

        @Test
        void getCasingById_ExistingId_ShouldReturnCasing() {
            when(casingRepository.findById(1L)).thenReturn(Optional.of(midTowerCase));

            Casing result = casingService.getCasingById(1L);

            assertThat(result).isEqualTo(midTowerCase);
            verify(casingRepository, times(1)).findById(1L);
        }

        @Test
        void getCasingById_NonExistingId_ShouldReturnNull() {
            when(casingRepository.findById(999L)).thenReturn(Optional.empty());

            Casing result = casingService.getCasingById(999L);

            assertThat(result).isNull();
            verify(casingRepository, times(1)).findById(999L);
        }

        @Test
        void insertCasing_ShouldSaveCasing() {
            casingService.insertCasing(midTowerCase);

            verify(casingRepository, times(1)).save(midTowerCase);
        }

        @Test
        void deleteCasingById_ShouldDeleteCasing() {
            casingService.deleteCasingById(1L);

            verify(casingRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateCasing_ShouldSaveCasing() {
            casingService.updateCasing(midTowerCase, 1L);

            verify(casingRepository, times(1)).save(midTowerCase);
        }

        @Test
        void getCasingByModelName_ExistingModel_ShouldReturnCasing() {
            when(casingRepository.findByModelName("Fractal Design Define 7")).thenReturn(Optional.of(midTowerCase));

            Optional<Casing> result = casingService.getCasingByModelName("Fractal Design Define 7");

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("Fractal Design Define 7");
            verify(casingRepository, times(1)).findByModelName("Fractal Design Define 7");
        }

        @Test
        void getCasingByModelName_NonExistingModel_ShouldReturnEmpty() {
            when(casingRepository.findByModelName("Non-existent Case")).thenReturn(Optional.empty());

            Optional<Casing> result = casingService.getCasingByModelName("Non-existent Case");

            assertThat(result).isEmpty();
            verify(casingRepository, times(1)).findByModelName("Non-existent Case");
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredCasings_WithMotherboardSupportOnly_ShouldReturnFilteredResults() {
            when(casingRepository.findByMotherboardSupport("ATX")).thenReturn(Arrays.asList(midTowerCase));

            List<Casing> result = casingService.getFilteredCasings("ATX", null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getMotherboardSupport()).isEqualTo("ATX");
            verify(casingRepository, times(1)).findByMotherboardSupport("ATX");
        }

        @Test
        void getFilteredCasings_WithAllFilters_ShouldReturnFilteredResults() {
            when(casingRepository.findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(
                    "ATX", 180, 400, 180)).thenReturn(Arrays.asList(midTowerCase));

            List<Casing> result = casingService.getFilteredCasings("ATX", 180, 400, 180);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getMotherboardSupport()).isEqualTo("ATX");
            verify(casingRepository, times(1)).findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(
                    "ATX", 180, 400, 180);
        }

        @Test
        void getFilteredCasings_WithNoFilters_ShouldReturnAllResults() {
            when(casingRepository.findAll()).thenReturn(casingList);

            List<Casing> result = casingService.getFilteredCasings(null, null, null, null);

            assertThat(result).hasSize(3);
            verify(casingRepository, times(1)).findAll();
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCasings2_WithPriceRange_ShouldReturnFilteredResults() {
            when(casingRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(midTowerCase));

            List<Casing> result = casingService.getFilteredCasings2(
                    150.0, 200.0, 
                    null, null, null, null, null, null,
                    Arrays.asList("Fractal Design"), Arrays.asList("ATX"), null,
                    null, null
            );

            assertThat(result).hasSize(1);
            verify(casingRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCasings2_WithBrandFilter_ShouldReturnFilteredResults() {
            when(casingRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(fullTowerCase));

            List<Casing> result = casingService.getFilteredCasings2(
                    null, null, 
                    null, null, null, null, null, null,
                    Arrays.asList("Corsair"), null, null,
                    null, null
            );

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("Corsair");
            verify(casingRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCasings2_WithClearanceFilters_ShouldReturnFilteredResults() {
            when(casingRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(fullTowerCase));

            List<Casing> result = casingService.getFilteredCasings2(
                    null, null, 
                    200, null, 500, null, 190, null,
                    null, null, null,
                    null, null
            );

            assertThat(result).hasSize(1);
            verify(casingRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCasings2_WithNoFilters_ShouldReturnAllResults() {
            when(casingRepository.findAll(any(Specification.class))).thenReturn(casingList);

            List<Casing> result = casingService.getFilteredCasings2(
                    null, null, 
                    null, null, null, null, null, null,
                    null, null, null,
                    null, null
            );

            assertThat(result).hasSize(3);
            verify(casingRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredCasings2_WithEmptyResults_ShouldReturnEmptyList() {
            when(casingRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Casing> result = casingService.getFilteredCasings2(
                    1000.0, 2000.0, 
                    null, null, null, null, null, null,
                    Arrays.asList("NonExistentBrand"), null, null,
                    null, null
            );

            assertThat(result).isEmpty();
            verify(casingRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        void getFilteredCasings_WithPartialFilters_ShouldUseCorrectRepository() {
            when(casingRepository.findAll()).thenReturn(casingList);

            List<Casing> result = casingService.getFilteredCasings(null, 180, null, null);

            // When not all filters are provided, it falls back to findAll
            assertThat(result).hasSize(3);
            verify(casingRepository, times(1)).findAll();
        }

        @Test
        void getFilteredCasings_WithEmptyResults_ShouldReturnEmptyList() {
            when(casingRepository.findByMotherboardSupport("Unsupported")).thenReturn(Collections.emptyList());

            List<Casing> result = casingService.getFilteredCasings("Unsupported", null, null, null);

            assertThat(result).isEmpty();
            verify(casingRepository, times(1)).findByMotherboardSupport("Unsupported");
        }
    }
}
