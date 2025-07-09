package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ram;
import com.software_project.pcbanabo.repository.RamRepository;
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
@DisplayName("RAM Service Tests")
class RamServiceTest {

    @Mock
    private RamRepository ramRepository;

    @InjectMocks
    private RamService ramService;

    private Ram highSpeedRam;
    private Ram midRangeRam;
    private Ram budgetRam;
    private List<Ram> ramList;

    @BeforeEach
    void setUp() {
        highSpeedRam = new Ram();
        highSpeedRam.setId(1L);
        highSpeedRam.setModel_name("G.Skill Trident Z5 RGB");
        highSpeedRam.setBrand_name("G.Skill");
        highSpeedRam.setMemType("DDR5");
        highSpeedRam.setCapacity("32GB");
        highSpeedRam.setSpeed(6000);
        highSpeedRam.setTimings("30-36-36-96");
        highSpeedRam.setRgb(true);
        highSpeedRam.setAvg_price(299.99);

        midRangeRam = new Ram();
        midRangeRam.setId(2L);
        midRangeRam.setModel_name("Corsair Vengeance LPX");
        midRangeRam.setBrand_name("Corsair");
        midRangeRam.setMemType("DDR4");
        midRangeRam.setCapacity("16GB");
        midRangeRam.setSpeed(3200);
        midRangeRam.setTimings("16-18-18-36");
        midRangeRam.setRgb(false);
        midRangeRam.setAvg_price(79.99);

        budgetRam = new Ram();
        budgetRam.setId(3L);
        budgetRam.setModel_name("Crucial Ballistix");
        budgetRam.setBrand_name("Crucial");
        budgetRam.setMemType("DDR4");
        budgetRam.setCapacity("8GB");
        budgetRam.setSpeed(2666);
        budgetRam.setTimings("19-19-19-43");
        budgetRam.setRgb(false);
        budgetRam.setAvg_price(39.99);

        ramList = Arrays.asList(highSpeedRam, midRangeRam, budgetRam);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllRams_ShouldReturnAllRams() {
            when(ramRepository.findAll()).thenReturn(ramList);

            List<Ram> result = ramService.getAllRams();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(highSpeedRam, midRangeRam, budgetRam);
            verify(ramRepository, times(1)).findAll();
        }

        @Test
        void getRamById_ExistingId_ShouldReturnRam() {
            when(ramRepository.findById(1L)).thenReturn(Optional.of(highSpeedRam));

            Ram result = ramService.getRamById(1L);

            assertThat(result).isEqualTo(highSpeedRam);
            verify(ramRepository, times(1)).findById(1L);
        }

        @Test
        void getRamById_NonExistingId_ShouldReturnNull() {
            when(ramRepository.findById(999L)).thenReturn(Optional.empty());

            Ram result = ramService.getRamById(999L);

            assertThat(result).isNull();
            verify(ramRepository, times(1)).findById(999L);
        }

        @Test
        void insertRam_ShouldSaveRam() {
            ramService.insertRam(highSpeedRam);

            verify(ramRepository, times(1)).save(highSpeedRam);
        }

        @Test
        void deleteRamById_ShouldDeleteRam() {
            ramService.deleteRamById(1L);

            verify(ramRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateRam_ShouldSaveRam() {
            ramService.updateRam(highSpeedRam, 1L);

            verify(ramRepository, times(1)).save(highSpeedRam);
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getRamsByMemType_ShouldReturnFilteredResults() {
            when(ramRepository.findByMemType("DDR4")).thenReturn(Arrays.asList(midRangeRam, budgetRam));

            List<Ram> result = ramService.getRamsByMemType("DDR4");

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(ram -> "DDR4".equals(ram.getMemType()));
            verify(ramRepository, times(1)).findByMemType("DDR4");
        }

        @Test
        void getRamsByMemType_WithNoMatches_ShouldReturnEmpty() {
            when(ramRepository.findByMemType("DDR3")).thenReturn(Collections.emptyList());

            List<Ram> result = ramService.getRamsByMemType("DDR3");

            assertThat(result).isEmpty();
            verify(ramRepository, times(1)).findByMemType("DDR3");
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithBrandFilter_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(highSpeedRam));

            List<Ram> result = ramService.getFilteredRams("G.Skill", null, null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("G.Skill");
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithMemTypeFilter_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(midRangeRam, budgetRam));

            List<Ram> result = ramService.getFilteredRams(null, "DDR4", null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(ram -> "DDR4".equals(ram.getMemType()));
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithCapacityFilter_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(midRangeRam));

            List<Ram> result = ramService.getFilteredRams(null, null, "16GB", 
                                                         null, null, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getCapacity()).isEqualTo("16GB");
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithSpeedFilter_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(highSpeedRam, midRangeRam));

            List<Ram> result = ramService.getFilteredRams(null, null, null, 
                                                         3000, null, null, null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(ram -> ram.getSpeed() >= 3000);
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithRgbFilter_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(highSpeedRam));

            List<Ram> result = ramService.getFilteredRams(null, null, null, 
                                                         null, true, null, null);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getRgb()).isTrue();
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithPriceRange_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(midRangeRam));

            List<Ram> result = ramService.getFilteredRams(null, null, null, 
                                                         null, null, 50.0, 100.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getAvg_price()).isBetween(50.0, 100.0);
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithMultipleFilters_ShouldReturnFilteredResults() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(highSpeedRam));

            List<Ram> result = ramService.getFilteredRams("G.Skill", "DDR5", "32GB", 
                                                         5000, true, 200.0, 350.0);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getBrand_name()).isEqualTo("G.Skill");
            assertThat(result.get(0).getMemType()).isEqualTo("DDR5");
            assertThat(result.get(0).getCapacity()).isEqualTo("32GB");
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithNoFilters_ShouldReturnAllRams() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(ramList);

            List<Ram> result = ramService.getFilteredRams(null, null, null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(3);
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithEmptyResults_ShouldReturnEmptyList() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Ram> result = ramService.getFilteredRams("NonExistentBrand", null, null, 
                                                         null, null, null, null);

            assertThat(result).isEmpty();
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        void getAllRams_EmptyRepository_ShouldReturnEmptyList() {
            when(ramRepository.findAll()).thenReturn(Collections.emptyList());

            List<Ram> result = ramService.getAllRams();

            assertThat(result).isEmpty();
            verify(ramRepository, times(1)).findAll();
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithExtremeValues_ShouldHandleGracefully() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Ram> result = ramService.getFilteredRams(null, null, "128GB", 
                                                         10000, true, 1000.0, 2000.0);

            assertThat(result).isEmpty();
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredRams_WithNullFilters_ShouldHandleGracefully() {
            when(ramRepository.findAll(any(Specification.class))).thenReturn(ramList);

            List<Ram> result = ramService.getFilteredRams(null, null, null, 
                                                         null, null, null, null);

            assertThat(result).hasSize(3);
            verify(ramRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        void getRamsByMemType_WithNullMemType_ShouldHandleGracefully() {
            when(ramRepository.findByMemType(null)).thenReturn(Collections.emptyList());

            List<Ram> result = ramService.getRamsByMemType(null);

            assertThat(result).isEmpty();
            verify(ramRepository, times(1)).findByMemType(null);
        }
    }
}
