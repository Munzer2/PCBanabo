package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Gpu;
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
@DisplayName("GPU Repository Tests")
class GpuRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GpuRepository gpuRepository;

    private Gpu nvidiaGpu;
    private Gpu amdGpu;
    private Gpu budgetGpu;

    @BeforeEach
    void setUp() {
        nvidiaGpu = new Gpu();
        nvidiaGpu.setModel_name("NVIDIA GeForce RTX 4080");
        nvidiaGpu.setBrand_name("NVIDIA");
        nvidiaGpu.setGpu_core("Ada Lovelace");
        nvidiaGpu.setVram(16);
        nvidiaGpu.setAvg_price(1199.99);
        nvidiaGpu.setTdp(320);
        nvidiaGpu.setCardLength(310);

        amdGpu = new Gpu();
        amdGpu.setModel_name("AMD Radeon RX 7900 XTX");
        amdGpu.setBrand_name("AMD");
        amdGpu.setGpu_core("RDNA 3");
        amdGpu.setVram(24);
        amdGpu.setAvg_price(999.99);
        amdGpu.setTdp(355);
        amdGpu.setCardLength(320);

        budgetGpu = new Gpu();
        budgetGpu.setModel_name("NVIDIA GeForce RTX 4060");
        budgetGpu.setBrand_name("NVIDIA");
        budgetGpu.setGpu_core("Ada Lovelace");
        budgetGpu.setVram(8);
        budgetGpu.setAvg_price(299.99);
        budgetGpu.setTdp(115);
        budgetGpu.setCardLength(245);

        entityManager.persistAndFlush(nvidiaGpu);
        entityManager.persistAndFlush(amdGpu);
        entityManager.persistAndFlush(budgetGpu);
    }

    @Nested
    @DisplayName("Basic Repository Operations")
    class BasicRepositoryOperations {

        @Test
        void findAll_ShouldReturnAllGpus() {
            List<Gpu> result = gpuRepository.findAll();

            assertThat(result).hasSize(3);
            assertThat(result).extracting(Gpu::getModel_name)
                    .contains("NVIDIA GeForce RTX 4080", "AMD Radeon RX 7900 XTX", "NVIDIA GeForce RTX 4060");
        }

        @Test
        void findById_ExistingId_ShouldReturnGpu() {
            Optional<Gpu> result = gpuRepository.findById(nvidiaGpu.getId());

            assertThat(result).isPresent();
            assertThat(result.get().getModel_name()).isEqualTo("NVIDIA GeForce RTX 4080");
            assertThat(result.get().getBrand_name()).isEqualTo("NVIDIA");
        }

        @Test
        void findById_NonExistingId_ShouldReturnEmpty() {
            Optional<Gpu> result = gpuRepository.findById(999L);

            assertThat(result).isEmpty();
        }

        @Test
        void save_ShouldPersistNewGpu() {
            Gpu newGpu = new Gpu();
            newGpu.setModel_name("AMD Radeon RX 6700 XT");
            newGpu.setBrand_name("AMD");
            newGpu.setGpu_core("RDNA 2");
            newGpu.setVram(12);
            newGpu.setAvg_price(479.99);
            newGpu.setTdp(230);
            newGpu.setCardLength(267);

            Gpu saved = gpuRepository.save(newGpu);

            assertThat(saved.getId()).isNotNull();
            assertThat(saved.getModel_name()).isEqualTo("AMD Radeon RX 6700 XT");
        }

        @Test
        void deleteById_ShouldRemoveGpu() {
            Long gpuId = nvidiaGpu.getId();
            gpuRepository.deleteById(gpuId);

            Optional<Gpu> result = gpuRepository.findById(gpuId);
            assertThat(result).isEmpty();
        }

        @Test
        void count_ShouldReturnCorrectCount() {
            long count = gpuRepository.count();

            assertThat(count).isEqualTo(3);
        }

        @Test
        void existsById_ShouldReturnTrueForExistingGpu() {
            boolean exists = gpuRepository.existsById(nvidiaGpu.getId());

            assertThat(exists).isTrue();
        }

        @Test
        void existsById_ShouldReturnFalseForNonExistentGpu() {
            boolean exists = gpuRepository.existsById(999L);

            assertThat(exists).isFalse();
        }
    }

    @Nested
    @DisplayName("Custom Query Operations")
    class CustomQueryOperations {

        @Test
        void findByModelName_ShouldReturnGpuWithSpecifiedModelName() {
            Optional<Gpu> result = gpuRepository.findByModelName("NVIDIA GeForce RTX 4080");

            assertThat(result).isPresent();
            assertThat(result.get().getBrand_name()).isEqualTo("NVIDIA");
            assertThat(result.get().getGpu_core()).isEqualTo("Ada Lovelace");
        }

        @Test
        void findByModelName_NonExistentModel_ShouldReturnEmpty() {
            Optional<Gpu> result = gpuRepository.findByModelName("Non-existent GPU");

            assertThat(result).isEmpty();
        }

        @Test
        void findByCardLengthLessThanEqual_ShouldReturnGpusWithinCardLength() {
            List<Gpu> result = gpuRepository.findByCardLengthLessThanEqual(250);

            assertThat(result).hasSize(1);
            assertThat(result.get(0).getModel_name()).isEqualTo("NVIDIA GeForce RTX 4060");
            assertThat(result.get(0).getCardLength()).isLessThanOrEqualTo(250);
        }

        @Test
        void findByCardLengthLessThanEqual_LargeValue_ShouldReturnAllGpus() {
            List<Gpu> result = gpuRepository.findByCardLengthLessThanEqual(400);

            assertThat(result).hasSize(3);
        }

        @Test
        void findByCardLengthLessThanEqual_SmallValue_ShouldReturnEmptyList() {
            List<Gpu> result = gpuRepository.findByCardLengthLessThanEqual(100);

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("Data Integrity Tests")
    class DataIntegrityTests {

        @Test
        void save_UpdateExistingGpu_ShouldPersistChanges() {
            nvidiaGpu.setAvg_price(1099.99);
            Gpu updated = gpuRepository.save(nvidiaGpu);

            assertThat(updated.getAvg_price()).isEqualTo(1099.99);
            assertThat(updated.getId()).isEqualTo(nvidiaGpu.getId());
        }

        @Test
        void findAll_AfterDeletion_ShouldReturnRemainingGpus() {
            gpuRepository.deleteById(amdGpu.getId());

            List<Gpu> remaining = gpuRepository.findAll();
            assertThat(remaining).hasSize(2);
            assertThat(remaining).extracting(Gpu::getModel_name)
                    .doesNotContain("AMD Radeon RX 7900 XTX");
        }
    }
}
