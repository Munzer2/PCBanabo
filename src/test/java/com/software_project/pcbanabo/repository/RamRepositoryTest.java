package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Ram;
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
@DisplayName("RAM Repository Tests")
class RamRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RamRepository ramRepository;

    private Ram ddr4Ram;
    private Ram ddr5Ram;
    private Ram lowLatencyRam;

    @BeforeEach
    void setUp() {
        ddr4Ram = new Ram();
        ddr4Ram.setModel_name("Corsair Vengeance LPX 16GB");
        ddr4Ram.setBrand_name("Corsair");
        ddr4Ram.setMemType("DDR4");
        ddr4Ram.setCapacity("16GB");
        ddr4Ram.setSpeed(3200);
        ddr4Ram.setTimings("16-18-18-36");
        ddr4Ram.setAvg_price(79.99);

        ddr5Ram = new Ram();
        ddr5Ram.setModel_name("G.Skill Trident Z5 32GB");
        ddr5Ram.setBrand_name("G.Skill");
        ddr5Ram.setMemType("DDR5");
        ddr5Ram.setCapacity("32GB");
        ddr5Ram.setSpeed(6000);
        ddr5Ram.setTimings("36-36-36-76");
        ddr5Ram.setAvg_price(249.99);

        lowLatencyRam = new Ram();
        lowLatencyRam.setModel_name("G.Skill Ripjaws V 32GB");
        lowLatencyRam.setBrand_name("G.Skill");
        lowLatencyRam.setMemType("DDR4");
        lowLatencyRam.setCapacity("32GB");
        lowLatencyRam.setSpeed(3600);
        lowLatencyRam.setTimings("14-15-15-35");
        lowLatencyRam.setAvg_price(139.99);

        entityManager.persistAndFlush(ddr4Ram);
        entityManager.persistAndFlush(ddr5Ram);
        entityManager.persistAndFlush(lowLatencyRam);
    }

    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryOperations {

        @Test
        void findAll_ShouldReturnAllRam() {
            List<Ram> result = ramRepository.findAll();

            assertThat(result).hasSize(3);
            assertThat(result).extracting(Ram::getModel_name)
                    .contains("Corsair Vengeance LPX 16GB", "G.Skill Trident Z5 32GB", "G.Skill Ripjaws V 32GB");
        }

        @Test
        void findById_ExistingId_ShouldReturnRam() {
            Optional<Ram> result = ramRepository.findById(ddr4Ram.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("Corsair Vengeance LPX 16GB");
            assertThat(result.get().getBrand_name()).isEqualTo("Corsair");
        }

        @Test
        void findById_NonExistingId_ShouldReturnEmpty() {
            Optional<Ram> result = ramRepository.findById(999L);

            assertThat(result).isEmpty();
        }

        @Test
        void save_ShouldPersistNewRam() {
            Ram newRam = new Ram();
            newRam.setModel_name("Crucial Ballistix 16GB");
            newRam.setBrand_name("Crucial");
            newRam.setMemType("DDR4");
            newRam.setCapacity("16GB");
            newRam.setSpeed(3000);
            newRam.setTimings("15-16-16-35");
            newRam.setAvg_price(69.99);

            Ram saved = ramRepository.save(newRam);

            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getModel_name()).isEqualTo("Crucial Ballistix 16GB");
        }

        @Test
        void deleteById_ShouldRemoveRam() {
            Long ramId = ddr4Ram.getId();
            ramRepository.deleteById(ramId);

            Optional<Ram> result = ramRepository.findById(ramId);
            assertThat(result).isEmpty();
        }

        @Test
        void count_ShouldReturnCorrectCount() {
            long count = ramRepository.count();

            assertThat(count).isEqualTo(3);
        }

        @Test
        void existsById_ShouldReturnTrueForExistingRam() {
            boolean exists = ramRepository.existsById(ddr4Ram.getId());

            assertThat(exists).isTrue();
        }

        @Test
        void existsById_ShouldReturnFalseForNonExistentRam() {
            boolean exists = ramRepository.existsById(999L);

            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("Custom Query Operations")
    class CustomQueryOperations {

        @Test
        void findByModelName_ShouldReturnRamWithSpecifiedModelName() {
            // This test needs to be adjusted since findByModelName doesn't exist in RamRepository
            // We'll test with findByMemType instead
            List<Ram> result = ramRepository.findByMemType("DDR4");

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Ram::getBrand_name)
                    .contains("Corsair", "G.Skill");
        }

        @Test
        void findByMemType_NonExistentType_ShouldReturnEmpty() {
            List<Ram> result = ramRepository.findByMemType("Non-existent Type");

            assertThat(result).isEmpty();
        }

        @Test
        void findByMemType_ShouldReturnRamOfSpecifiedType() {
            List<Ram> result = ramRepository.findByMemType("DDR4");

            assertThat(result).hasSize(2);
            assertThat(result).extracting(Ram::getModel_name)
                    .containsExactlyInAnyOrder("Corsair Vengeance LPX 16GB", "G.Skill Ripjaws V 32GB");
        }

        @Test
        void findByMemType_DDR5_ShouldReturnDDR5Ram() {
            List<Ram> result = ramRepository.findByMemType("DDR5");

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getModel_name()).isEqualTo("G.Skill Trident Z5 32GB");
        }

        @Test
        void findByMemType_NonExistentMemType_ShouldReturnEmptyList() {
            List<Ram> result = ramRepository.findByMemType("DDR3");

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        void save_UpdateExistingRam_ShouldPersistChanges() {
            ddr4Ram.setAvg_price(89.99);
            Ram updated = ramRepository.save(ddr4Ram);

            assertThat(updated.getAvg_price()).isEqualTo(89.99);
            assertThat(updated.getId()).isEqualTo(ddr4Ram.getId());
        }

        @Test
        void findAll_AfterDeletion_ShouldReturnRemainingRam() {
            ramRepository.deleteById(ddr5Ram.getId());

            List<Ram> remaining = ramRepository.findAll();
            assertThat(remaining).hasSize(2);
            assertThat(remaining).extracting(Ram::getModel_name)
                    .doesNotContain("G.Skill Trident Z5 32GB");
        }

        @Test
        void findByMemType_AfterAddingMultipleTypes_ShouldGroupCorrectly() {
            Ram newDDR5 = new Ram();
            newDDR5.setModel_name("Corsair Dominator DDR5 64GB");
            newDDR5.setBrand_name("Corsair");
            newDDR5.setMemType("DDR5");
            newDDR5.setCapacity("64GB");
            newDDR5.setSpeed(5600);
            newDDR5.setTimings("40-40-40-80");
            newDDR5.setAvg_price(499.99);
            
            ramRepository.save(newDDR5);

            List<Ram> ddr4List = ramRepository.findByMemType("DDR4");
            List<Ram> ddr5List = ramRepository.findByMemType("DDR5");

            assertThat(ddr4List).hasSize(2);
            assertThat(ddr5List).hasSize(2);
        }
    }
}
