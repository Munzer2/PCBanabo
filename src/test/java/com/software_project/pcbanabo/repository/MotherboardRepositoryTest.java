package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Motherboard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DisplayName("Motherboard Repository Tests")
class MotherboardRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MotherboardRepository motherboardRepository;

    private Motherboard asusMotherboard;
    private Motherboard msiMotherboard;
    private Motherboard budgetMotherboard;

    @BeforeEach
    void setUp() {
        asusMotherboard = new Motherboard();
        asusMotherboard.setModel_name("ASUS ROG Strix B550-F Gaming");
        asusMotherboard.setBrand_name("ASUS");
        asusMotherboard.setSocket("AM4");
        asusMotherboard.setChipset("B550");
        asusMotherboard.setFormFactor("ATX");
        asusMotherboard.setMem_slot(4);
        asusMotherboard.setMax_mem_speed(3200);
        asusMotherboard.setAvg_price(179.99);

        msiMotherboard = new Motherboard();
        msiMotherboard.setModel_name("MSI MAG B650 Tomahawk WiFi");
        msiMotherboard.setBrand_name("MSI");
        msiMotherboard.setSocket("AM5");
        msiMotherboard.setChipset("B650");
        msiMotherboard.setFormFactor("ATX");
        msiMotherboard.setMem_slot(4);
        msiMotherboard.setMax_mem_speed(5600);
        msiMotherboard.setAvg_price(229.99);

        budgetMotherboard = new Motherboard();
        budgetMotherboard.setModel_name("ASRock B450M PRO4");
        budgetMotherboard.setBrand_name("ASRock");
        budgetMotherboard.setSocket("AM4");
        budgetMotherboard.setChipset("B450");
        budgetMotherboard.setFormFactor("Micro-ATX");
        budgetMotherboard.setMem_slot(4);
        budgetMotherboard.setMax_mem_speed(3200);
        budgetMotherboard.setAvg_price(79.99);

        entityManager.persistAndFlush(asusMotherboard);
        entityManager.persistAndFlush(msiMotherboard);
        entityManager.persistAndFlush(budgetMotherboard);
    }

    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryOperations {

        @Test
        void findAll_ShouldReturnAllMotherboards() {
            List<Motherboard> result = motherboardRepository.findAll();

            assertThat(result).hasSize(3);
            assertThat(result).extracting(Motherboard::getModel_name)
                    .contains("ASUS ROG Strix B550-F Gaming", "MSI MAG B650 Tomahawk WiFi", "ASRock B450M PRO4");
        }

        @Test
        void findById_ExistingId_ShouldReturnMotherboard() {
            Optional<Motherboard> result = motherboardRepository.findById(asusMotherboard.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("ASUS ROG Strix B550-F Gaming");
            assertThat(result.get().getBrand_name()).isEqualTo("ASUS");
        }

        @Test
        void findById_NonExistingId_ShouldReturnEmpty() {
            Optional<Motherboard> result = motherboardRepository.findById(999L);

            assertThat(result).isEmpty();
        }

        @Test
        void save_ShouldPersistNewMotherboard() {
            Motherboard newMotherboard = new Motherboard();
            newMotherboard.setModel_name("Gigabyte B650 AORUS Elite AX");
            newMotherboard.setBrand_name("Gigabyte");
            newMotherboard.setSocket("AM5");
            newMotherboard.setChipset("B650");
            newMotherboard.setFormFactor("ATX");
            newMotherboard.setMem_slot(4);
            newMotherboard.setMax_mem_speed(5600);
            newMotherboard.setAvg_price(199.99);

            Motherboard saved = motherboardRepository.save(newMotherboard);

            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getModel_name()).isEqualTo("Gigabyte B650 AORUS Elite AX");
        }

        @Test
        void deleteById_ShouldRemoveMotherboard() {
            Long motherboardId = asusMotherboard.getId();
            motherboardRepository.deleteById(motherboardId);

            Optional<Motherboard> result = motherboardRepository.findById(motherboardId);
            assertThat(result).isEmpty();
        }

        @Test
        void count_ShouldReturnCorrectCount() {
            long count = motherboardRepository.count();

            assertThat(count).isEqualTo(3);
        }

        @Test
        void existsById_ShouldReturnTrueForExistingMotherboard() {
            boolean exists = motherboardRepository.existsById(asusMotherboard.getId());

            assertThat(exists).isTrue();
        }

        @Test
        void existsById_ShouldReturnFalseForNonExistentMotherboard() {
            boolean exists = motherboardRepository.existsById(999L);

            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("Custom Query Operations")
    class CustomQueryOperations {

        @Test
        void findBySocket_ShouldReturnMotherboardsWithSpecifiedSocket() {
            List<Motherboard> result = motherboardRepository.findBySocket("AM4");

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Motherboard::getModel_name)
                    .containsExactlyInAnyOrder("ASUS ROG Strix B550-F Gaming", "ASRock B450M PRO4");
        }

        @Test
        void findBySocket_AM5_ShouldReturnAM5Motherboards() {
            List<Motherboard> result = motherboardRepository.findBySocket("AM5");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getModel_name()).isEqualTo("MSI MAG B650 Tomahawk WiFi");
        }

        @Test
        void findBySocket_NonExistentSocket_ShouldReturnEmptyList() {
            List<Motherboard> result = motherboardRepository.findBySocket("LGA1700");

            assertThat(result).isEmpty();
        }

        @Test
        void findByFormFactor_ShouldReturnMotherboardsWithSpecifiedFormFactor() {
            List<Motherboard> result = motherboardRepository.findByFormFactor("ATX");

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Motherboard::getModel_name)
                    .containsExactlyInAnyOrder("ASUS ROG Strix B550-F Gaming", "MSI MAG B650 Tomahawk WiFi");
        }

        @Test
        void findByFormFactor_MicroATX_ShouldReturnMicroATXMotherboards() {
            List<Motherboard> result = motherboardRepository.findByFormFactor("Micro-ATX");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getModel_name()).isEqualTo("ASRock B450M PRO4");
        }

        @Test
        void findByFormFactor_NonExistentFormFactor_ShouldReturnEmptyList() {
            List<Motherboard> result = motherboardRepository.findByFormFactor("Mini-ITX");

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        void save_UpdateExistingMotherboard_ShouldPersistChanges() {
            asusMotherboard.setAvg_price(159.99);
            Motherboard updated = motherboardRepository.save(asusMotherboard);

            assertThat(updated.getAvg_price()).isEqualTo(159.99);
            assertThat(updated.getId()).isEqualTo(asusMotherboard.getId());
        }

        @Test
        void findAll_AfterDeletion_ShouldReturnRemainingMotherboards() {
            motherboardRepository.deleteById(msiMotherboard.getId());

            List<Motherboard> remaining = motherboardRepository.findAll();
            assertThat(remaining).hasSize(2);
            assertThat(remaining).extracting(Motherboard::getModel_name)
                    .doesNotContain("MSI MAG B650 Tomahawk WiFi");
        }

        @Test
        void findBySocket_AfterAddingMultipleSockets_ShouldGroupCorrectly() {
            Motherboard intelMotherboard = new Motherboard();
            intelMotherboard.setModel_name("ASUS Prime Z790-P WiFi");
            intelMotherboard.setBrand_name("ASUS");
            intelMotherboard.setSocket("LGA1700");
            intelMotherboard.setChipset("Z790");
            intelMotherboard.setFormFactor("ATX");
            intelMotherboard.setMem_slot(4);
            intelMotherboard.setMax_mem_speed(5600);
            intelMotherboard.setAvg_price(249.99);
            
            motherboardRepository.save(intelMotherboard);

            List<Motherboard> am4List = motherboardRepository.findBySocket("AM4");
            List<Motherboard> am5List = motherboardRepository.findBySocket("AM5");
            List<Motherboard> lga1700List = motherboardRepository.findBySocket("LGA1700");

            assertThat(am4List).hasSize(2);
            assertThat(am5List).hasSize(1);
            assertThat(lga1700List).hasSize(1);
        }
    }
}
