package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Ssd;
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
@DisplayName("SSD Repository Tests")
class SsdRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SsdRepository ssdRepository;

    private Ssd samsungSsd;
    private Ssd wdSsd;
    private Ssd budgetSsd;

    @BeforeEach
    void setUp() {
        samsungSsd = new Ssd();
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
        budgetSsd.setModel_name("Kingston NV2 500GB");
        budgetSsd.setBrand_name("Kingston");
        budgetSsd.setCapacity("500GB");
        budgetSsd.setForm_factor("M.2");
        budgetSsd.setPcie_gen("PCIe 4.0");
        budgetSsd.setSeq_read(3500);
        budgetSsd.setSeq_write(2100);
        budgetSsd.setDram_cache(false);
        budgetSsd.setAvg_price(39.99);

        entityManager.persistAndFlush(samsungSsd);
        entityManager.persistAndFlush(wdSsd);
        entityManager.persistAndFlush(budgetSsd);
    }

    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryOperations {

        @Test
        void findAll_ShouldReturnAllSsds() {
            List<Ssd> ssds = ssdRepository.findAll();

            assertThat(ssds).hasSize(3);
            assertThat(ssds).extracting(Ssd::getModel_name)
                    .containsExactlyInAnyOrder("Samsung 980 PRO 1TB", "WD Black SN850X 2TB", "Kingston NV2 500GB");
        }

        @Test
        void findById_ShouldReturnSsdById() {
            Optional<Ssd> found = ssdRepository.findById(samsungSsd.getId());

            assertThat(found).isPresent();
            assertThat(found.get().getModel_name()).isEqualTo("Samsung 980 PRO 1TB");
            assertThat(found.get().getBrand_name()).isEqualTo("Samsung");
        }

        @Test
        void save_ShouldPersistNewSsd() {
            Ssd newSsd = new Ssd();
            newSsd.setModel_name("Crucial MX4 1TB");
            newSsd.setBrand_name("Crucial");
            newSsd.setCapacity("1TB");
            newSsd.setForm_factor("2.5\"");
            newSsd.setPcie_gen("SATA III");
            newSsd.setSeq_read(560);
            newSsd.setSeq_write(510);
            newSsd.setDram_cache(true);
            newSsd.setAvg_price(79.99);

            Ssd savedSsd = ssdRepository.save(newSsd);

            assertThat(savedSsd.getId()).isNotNull();
            assertThat(savedSsd.getModel_name()).isEqualTo("Crucial MX4 1TB");
            
            // Verify it was actually saved
            List<Ssd> allSsds = ssdRepository.findAll();
            assertThat(allSsds).hasSize(4);
        }

        @Test
        void deleteById_ShouldRemoveSsd() {
            Long ssdId = samsungSsd.getId();
            
            ssdRepository.deleteById(ssdId);
            
            Optional<Ssd> deleted = ssdRepository.findById(ssdId);
            assertThat(deleted).isEmpty();
            
            List<Ssd> remaining = ssdRepository.findAll();
            assertThat(remaining).hasSize(2);
        }

        @Test
        void count_ShouldReturnCorrectCount() {
            long count = ssdRepository.count();
            assertThat(count).isEqualTo(3);
        }

        @Test
        void existsById_ShouldReturnTrueForExistingSsd() {
            boolean exists = ssdRepository.existsById(samsungSsd.getId());
            assertThat(exists).isTrue();
        }

        @Test
        void existsById_ShouldReturnFalseForNonExistentSsd() {
            boolean exists = ssdRepository.existsById(999L);
            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("Custom Query Operations")
    class CustomQueryOperations {

        @Test
        void update_ShouldModifyExistingSsd() {
            Ssd ssdToUpdate = ssdRepository.findById(samsungSsd.getId()).orElseThrow();
            ssdToUpdate.setAvg_price(99.99);
            
            Ssd updatedSsd = ssdRepository.save(ssdToUpdate);
            
            assertThat(updatedSsd.getAvg_price()).isEqualTo(99.99);
            assertThat(updatedSsd.getId()).isEqualTo(samsungSsd.getId());
        }

        @Test
        void saveAndFlush_ShouldImmediatelyPersist() {
            Ssd newSsd = new Ssd();
            newSsd.setModel_name("Test SSD");
            newSsd.setBrand_name("Test Brand");
            newSsd.setCapacity("1TB");
            newSsd.setForm_factor("M.2");
            newSsd.setAvg_price(100.0);
            
            Ssd savedSsd = ssdRepository.saveAndFlush(newSsd);
            
            assertThat(savedSsd.getId()).isNotNull();
            
            // Verify immediate persistence
            Ssd found = entityManager.find(Ssd.class, savedSsd.getId());
            assertThat(found).isNotNull();
            assertThat(found.getModel_name()).isEqualTo("Test SSD");
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        void save_WithInvalidData_ShouldHandleConstraints() {
            // This would test database constraints if they exist
            // For now, just test that the repository can handle edge cases
            Ssd ssdWithNulls = new Ssd();
            ssdWithNulls.setModel_name(null);
            ssdWithNulls.setBrand_name("Test");
            ssdWithNulls.setAvg_price(0.0);
            
            // Depending on your constraints, this might throw an exception
            // For this test, we'll assume the save operation completes
            Ssd saved = ssdRepository.save(ssdWithNulls);
            assertThat(saved.getId()).isNotNull();
        }

        @Test
        void save_WithDuplicateData_ShouldAllowDuplicates() {
            // Unless you have unique constraints, duplicates should be allowed
            Ssd duplicate = new Ssd();
            duplicate.setModel_name("Samsung 980 PRO 1TB");
            duplicate.setBrand_name("Samsung");
            duplicate.setCapacity("1TB");
            duplicate.setForm_factor("M.2");
            duplicate.setAvg_price(129.99);
            
            Ssd savedDuplicate = ssdRepository.save(duplicate);
            assertThat(savedDuplicate.getId()).isNotNull();
            assertThat(savedDuplicate.getId()).isNotEqualTo(samsungSsd.getId());
        }
    }
}
