package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Psu;
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
@DisplayName("PSU Repository Tests")
class PsuRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PsuRepository psuRepository;

    private Psu corsairPsu;
    private Psu evgaPsu;
    private Psu budgetPsu;

    @BeforeEach
    void setUp() {
        corsairPsu = new Psu();
        corsairPsu.setModel_name("Corsair RM850x");
        corsairPsu.setBrand_name("Corsair");
        corsairPsu.setForm_factor("ATX");
        corsairPsu.setWattage(850);
        corsairPsu.setPsuLength(160);
        corsairPsu.setEps_connectors(1);
        corsairPsu.setPcie_8pin_connectors(4);
        corsairPsu.setCertification("80+ Gold");
        corsairPsu.setAvg_price(149.99);

        evgaPsu = new Psu();
        evgaPsu.setModel_name("EVGA SuperNOVA 1000 P5");
        evgaPsu.setBrand_name("EVGA");
        evgaPsu.setForm_factor("ATX");
        evgaPsu.setWattage(1000);
        evgaPsu.setPsuLength(180);
        evgaPsu.setEps_connectors(2);
        evgaPsu.setPcie_8pin_connectors(6);
        evgaPsu.setCertification("80+ Platinum");
        evgaPsu.setAvg_price(199.99);

        budgetPsu = new Psu();
        budgetPsu.setModel_name("Cooler Master MWE Bronze 650W");
        budgetPsu.setBrand_name("Cooler Master");
        budgetPsu.setForm_factor("ATX");
        budgetPsu.setWattage(650);
        budgetPsu.setPsuLength(140);
        budgetPsu.setEps_connectors(1);
        budgetPsu.setPcie_8pin_connectors(2);
        budgetPsu.setCertification("80+ Bronze");
        budgetPsu.setAvg_price(59.99);

        entityManager.persistAndFlush(corsairPsu);
        entityManager.persistAndFlush(evgaPsu);
        entityManager.persistAndFlush(budgetPsu);
    }

    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryOperations {

        @Test
        void findAll_ShouldReturnAllPsus() {
            List<Psu> result = psuRepository.findAll();

            assertThat(result).hasSize(3);
            assertThat(result).extracting(Psu::getModel_name)
                    .contains("Corsair RM850x", "EVGA SuperNOVA 1000 P5", "Cooler Master MWE Bronze 650W");
        }

        @Test
        void findById_ExistingId_ShouldReturnPsu() {
            Optional<Psu> result = psuRepository.findById(corsairPsu.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("Corsair RM850x");
            assertThat(result.get().getBrand_name()).isEqualTo("Corsair");
        }

        @Test
        void findById_NonExistingId_ShouldReturnEmpty() {
            Optional<Psu> result = psuRepository.findById(999L);

            assertThat(result).isEmpty();
        }

        @Test
        void save_ShouldPersistNewPsu() {
            Psu newPsu = new Psu();
            newPsu.setModel_name("Seasonic Focus GX-750");
            newPsu.setBrand_name("Seasonic");
            newPsu.setForm_factor("ATX");
            newPsu.setWattage(750);
            newPsu.setPsuLength(150);
            newPsu.setEps_connectors(1);
            newPsu.setPcie_8pin_connectors(4);
            newPsu.setCertification("80+ Gold");
            newPsu.setAvg_price(119.99);

            Psu saved = psuRepository.save(newPsu);

            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getModel_name()).isEqualTo("Seasonic Focus GX-750");
        }

        @Test
        void deleteById_ShouldRemovePsu() {
            Long psuId = corsairPsu.getId();
            psuRepository.deleteById(psuId);

            Optional<Psu> result = psuRepository.findById(psuId);
            assertThat(result).isEmpty();
        }

        @Test
        void count_ShouldReturnCorrectCount() {
            long count = psuRepository.count();

            assertThat(count).isEqualTo(3);
        }

        @Test
        void existsById_ShouldReturnTrueForExistingPsu() {
            boolean exists = psuRepository.existsById(corsairPsu.getId());

            assertThat(exists).isTrue();
        }

        @Test
        void existsById_ShouldReturnFalseForNonExistentPsu() {
            boolean exists = psuRepository.existsById(999L);

            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("Custom Query Operations")
    class CustomQueryOperations {

        @Test
        void findByWattageGreaterThanEqual_ShouldReturnPsusWithSufficientWattage() {
            List<Psu> result = psuRepository.findByWattageGreaterThanEqual(800);

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Psu::getModel_name)
                    .containsExactlyInAnyOrder("Corsair RM850x", "EVGA SuperNOVA 1000 P5");
        }

        @Test
        void findByWattageGreaterThanEqual_HighWattage_ShouldReturnHighWattagePsus() {
            List<Psu> result = psuRepository.findByWattageGreaterThanEqual(1000);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getModel_name()).isEqualTo("EVGA SuperNOVA 1000 P5");
        }

        @Test
        void findByWattageGreaterThanEqual_VeryHighWattage_ShouldReturnEmptyList() {
            List<Psu> result = psuRepository.findByWattageGreaterThanEqual(1500);

            assertThat(result).isEmpty();
        }

        @Test
        void findByPsuLengthLessThanEqual_ShouldReturnPsusWithinLength() {
            List<Psu> result = psuRepository.findByPsuLengthLessThanEqual(150);

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Psu::getModel_name)
                    .containsExactlyInAnyOrder("Corsair RM850x", "Cooler Master MWE Bronze 650W");
        }

        @Test
        void findByPsuLengthLessThanEqual_SmallLength_ShouldReturnEmptyList() {
            List<Psu> result = psuRepository.findByPsuLengthLessThanEqual(100);

            assertThat(result).isEmpty();
        }

        @Test
        void findByPsuLengthLessThanEqual_LargeLength_ShouldReturnAllPsus() {
            List<Psu> result = psuRepository.findByPsuLengthLessThanEqual(200);

            assertThat(result).hasSize(3);
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        void save_UpdateExistingPsu_ShouldPersistChanges() {
            corsairPsu.setAvg_price(139.99);
            Psu updated = psuRepository.save(corsairPsu);

            assertThat(updated.getAvg_price()).isEqualTo(139.99);
            assertThat(updated.getId()).isEqualTo(corsairPsu.getId());
        }

        @Test
        void findAll_AfterDeletion_ShouldReturnRemainingPsus() {
            psuRepository.deleteById(evgaPsu.getId());

            List<Psu> remaining = psuRepository.findAll();
            assertThat(remaining).hasSize(2);
            assertThat(remaining).extracting(Psu::getModel_name)
                    .doesNotContain("EVGA SuperNOVA 1000 P5");
        }

        @Test
        void findByWattageGreaterThanEqual_AfterUpdate_ShouldReflectChanges() {
            budgetPsu.setWattage(900);
            psuRepository.save(budgetPsu);

            List<Psu> result = psuRepository.findByWattageGreaterThanEqual(850);
            assertThat(result).hasSize(3);
        }
    }
}
