package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Motherboard;
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
@DisplayName("Motherboard Service Tests")
class MotherboardServiceTest {

    @Mock
    private MotherboardRepository motherboardRepository;

    @InjectMocks
    private MotherboardService motherboardService;

    private Motherboard asusBoard;
    private Motherboard msiBoard;
    private Motherboard gigabyteBoard;
    private List<Motherboard> motherboardList;

    @BeforeEach
    void setUp() {
        asusBoard = new Motherboard();
        asusBoard.setId(1L);
        asusBoard.setModel_name("ASUS ROG Strix B550-F Gaming");
        asusBoard.setBrand_name("ASUS");
        asusBoard.setSocket("AM4");
        asusBoard.setFormFactor("ATX");
        asusBoard.setMem_slot(4);
        asusBoard.setMax_mem_speed(3200);
        asusBoard.setAvg_price(189.99);

        msiBoard = new Motherboard();
        msiBoard.setId(2L);
        msiBoard.setModel_name("MSI MAG B550M Mortar");
        msiBoard.setBrand_name("MSI");
        msiBoard.setSocket("AM4");
        msiBoard.setFormFactor("Micro-ATX");
        msiBoard.setMem_slot(4);
        msiBoard.setMax_mem_speed(3200);
        msiBoard.setAvg_price(159.99);

        gigabyteBoard = new Motherboard();
        gigabyteBoard.setId(3L);
        gigabyteBoard.setModel_name("Gigabyte Z690 AORUS Elite");
        gigabyteBoard.setBrand_name("Gigabyte");
        gigabyteBoard.setSocket("LGA1700");
        gigabyteBoard.setFormFactor("ATX");
        gigabyteBoard.setMem_slot(4);
        gigabyteBoard.setMax_mem_speed(3200);
        gigabyteBoard.setAvg_price(279.99);

        motherboardList = Arrays.asList(asusBoard, msiBoard, gigabyteBoard);
    }

    @Nested
    @DisplayName("Basic CRUD Operations")
    class BasicOperations {

        @Test
        void getAllMotherboards_ShouldReturnAllMotherboards() {
            when(motherboardRepository.findAll()).thenReturn(motherboardList);

            List<Motherboard> result = motherboardService.getAllMotherboards();

            assertThat(result).hasSize(3);
            assertThat(result).containsExactlyInAnyOrder(asusBoard, msiBoard, gigabyteBoard);
            verify(motherboardRepository, times(1)).findAll();
        }

        @Test
        void getMotherboardById_ExistingId_ShouldReturnMotherboard() {
            when(motherboardRepository.findById(1L)).thenReturn(Optional.of(asusBoard));

            Motherboard result = motherboardService.getMotherboardById(1L);

            assertThat(result).isEqualTo(asusBoard);
            verify(motherboardRepository, times(1)).findById(1L);
        }

        @Test
        void getMotherboardById_NonExistingId_ShouldReturnNull() {
            when(motherboardRepository.findById(999L)).thenReturn(Optional.empty());

            Motherboard result = motherboardService.getMotherboardById(999L);

            assertThat(result).isNull();
            verify(motherboardRepository, times(1)).findById(999L);
        }

        @Test
        void insertMotherboard_ShouldSaveMotherboard() {
            motherboardService.insertMotherboard(asusBoard);

            verify(motherboardRepository, times(1)).save(asusBoard);
        }

        @Test
        void deleteMotherboardById_ShouldDeleteMotherboard() {
            motherboardService.deleteMotherboardById(1L);

            verify(motherboardRepository, times(1)).deleteById(1L);
        }

        @Test
        void updateMotherboard_ShouldSaveMotherboard() {
            motherboardService.updateMotherboard(asusBoard, 1L);

            verify(motherboardRepository, times(1)).save(asusBoard);
        }
    }

    @Nested
    @DisplayName("Specific Query Operations")
    class SpecificQueryOperations {

        @Test
        void getMotherboardsBySocket_ShouldReturnFilteredResults() {
            when(motherboardRepository.findBySocket("AM4")).thenReturn(Arrays.asList(asusBoard, msiBoard));

            List<Motherboard> result = motherboardService.getMotherboardsBySocket("AM4");

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(board -> "AM4".equals(board.getSocket()));
            verify(motherboardRepository, times(1)).findBySocket("AM4");
        }

        @Test
        void getMotherboardsByFormFactor_ShouldReturnFilteredResults() {
            when(motherboardRepository.findByFormFactor("ATX")).thenReturn(Arrays.asList(asusBoard, gigabyteBoard));

            List<Motherboard> result = motherboardService.getMotherboardsByFormFactor("ATX");

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(board -> "ATX".equals(board.getFormFactor()));
            verify(motherboardRepository, times(1)).findByFormFactor("ATX");
        }

        @Test
        void getMotherboardsBySocket_EmptyResults_ShouldReturnEmptyList() {
            when(motherboardRepository.findBySocket("LGA1200")).thenReturn(Collections.emptyList());

            List<Motherboard> result = motherboardService.getMotherboardsBySocket("LGA1200");

            assertThat(result).isEmpty();
            verify(motherboardRepository, times(1)).findBySocket("LGA1200");
        }

        @Test
        void getMotherboardsByFormFactor_EmptyResults_ShouldReturnEmptyList() {
            when(motherboardRepository.findByFormFactor("Mini-ITX")).thenReturn(Collections.emptyList());

            List<Motherboard> result = motherboardService.getMotherboardsByFormFactor("Mini-ITX");

            assertThat(result).isEmpty();
            verify(motherboardRepository, times(1)).findByFormFactor("Mini-ITX");
        }
    }

    @Nested
    @DisplayName("Filtering Operations")
    class FilteringOperations {

        @Test
        void getFilteredMotherboards_WithSocketFilter_ShouldReturnFilteredResults() {
            when(motherboardRepository.findBySocket("AM4")).thenReturn(Arrays.asList(asusBoard, msiBoard));

            List<Motherboard> result = motherboardService.getFilteredMotherboards("AM4", null);

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(board -> "AM4".equals(board.getSocket()));
            verify(motherboardRepository, times(1)).findBySocket("AM4");
        }

        @Test
        void getFilteredMotherboards_WithFormFactorFilter_ShouldReturnFilteredResults() {
            when(motherboardRepository.findByFormFactor("ATX")).thenReturn(Arrays.asList(asusBoard, gigabyteBoard));

            List<Motherboard> result = motherboardService.getFilteredMotherboards(null, "ATX");

            assertThat(result).hasSize(2);
            assertThat(result).allMatch(board -> "ATX".equals(board.getFormFactor()));
            verify(motherboardRepository, times(1)).findByFormFactor("ATX");
        }

        @Test
        void getFilteredMotherboards_WithBothFilters_ShouldReturnFilteredResults() {
            when(motherboardRepository.findBySocketAndFormFactor("AM4", "ATX")).thenReturn(Arrays.asList(asusBoard));

            List<Motherboard> result = motherboardService.getFilteredMotherboards("AM4", "ATX");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getSocket()).isEqualTo("AM4");
            assertThat(result.get(0).getFormFactor()).isEqualTo("ATX");
            verify(motherboardRepository, times(1)).findBySocketAndFormFactor("AM4", "ATX");
        }

        @Test
        void getFilteredMotherboards_WithNoFilters_ShouldReturnAllMotherboards() {
            when(motherboardRepository.findAll()).thenReturn(motherboardList);

            List<Motherboard> result = motherboardService.getFilteredMotherboards(null, null);

            assertThat(result).hasSize(3);
            verify(motherboardRepository, times(1)).findAll();
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredMotherboards2_WithAllFilters_ShouldReturnFilteredResults() {
            when(motherboardRepository.findAll(any(Specification.class))).thenReturn(Arrays.asList(asusBoard));

            List<Motherboard> result = motherboardService.getFilteredMotherboards2(
                "ASUS", null, "AM4", "ATX", null, 
                4, 4, 3200, null, null, 150.0, 200.0
            );

            assertThat(result).hasSize(1);
            verify(motherboardRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredMotherboards2_WithPartialFilters_ShouldReturnFilteredResults() {
            when(motherboardRepository.findAll(any(Specification.class))).thenReturn(motherboardList);

            List<Motherboard> result = motherboardService.getFilteredMotherboards2(
                null, null, "ASUS", null, null, 
                null, null, null, null, null, null, null
            );

            assertThat(result).hasSize(3);
            verify(motherboardRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredMotherboards2_WithNoFilters_ShouldReturnAllResults() {
            when(motherboardRepository.findAll(any(Specification.class))).thenReturn(motherboardList);

            List<Motherboard> result = motherboardService.getFilteredMotherboards2(
                null, null, null, null, null, 
                null, null, null, null, null, null, null
            );

            assertThat(result).hasSize(3);
            verify(motherboardRepository, times(1)).findAll(any(Specification.class));
        }

        @Test
        @SuppressWarnings("unchecked")
        void getFilteredMotherboards2_WithEmptyResults_ShouldReturnEmptyList() {
            when(motherboardRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

            List<Motherboard> result = motherboardService.getFilteredMotherboards2(
                "NonExistentBrand", null, null, null, null, 
                null, null, null, null, null, 1000.0, 2000.0
            );

            assertThat(result).isEmpty();
            verify(motherboardRepository, times(1)).findAll(any(Specification.class));
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCases {

        @Test
        void getMotherboardsBySocket_WithNullSocket_ShouldHandleGracefully() {
            motherboardService.getMotherboardsBySocket(null);

            // Depending on the implementation, this might return empty list or call findAll
            verify(motherboardRepository, never()).findBySocket(null);
        }

        @Test
        void getMotherboardsByFormFactor_WithNullFormFactor_ShouldHandleGracefully() {
            motherboardService.getMotherboardsByFormFactor(null);

            // Depending on the implementation, this might return empty list or call findAll
            verify(motherboardRepository, never()).findByFormFactor(null);
        }
    }
}
