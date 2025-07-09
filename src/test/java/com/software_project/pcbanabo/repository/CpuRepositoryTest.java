package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.Cpu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CpuRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CpuRepository cpuRepository;

    private Cpu intelCpu;
    private Cpu amdCpu;
    private Cpu budgetCpu;

    @BeforeEach
    void setUp() {
        intelCpu = new Cpu();
        intelCpu.setModel_name("Intel Core i7-12700K");
        intelCpu.setBrand_name("Intel");
        intelCpu.setSocket("LGA1700");
        intelCpu.setAverage_price(399.99);

        amdCpu = new Cpu();
        amdCpu.setModel_name("AMD Ryzen 7 5800X");
        amdCpu.setBrand_name("AMD");
        amdCpu.setSocket("AM4");
        amdCpu.setAverage_price(349.99);

        budgetCpu = new Cpu();
        budgetCpu.setModel_name("AMD Ryzen 5 3600");
        budgetCpu.setBrand_name("AMD");
        budgetCpu.setSocket("AM4");
        budgetCpu.setAverage_price(199.99   );

        entityManager.persistAndFlush(intelCpu);
        entityManager.persistAndFlush(amdCpu);
        entityManager.persistAndFlush(budgetCpu);
    }

    @Test
    void findByModelName_ShouldReturnCpuWithSpecifiedModelName() {
        Optional<Cpu> result = cpuRepository.findByModelName("Intel Core i7-12700K");

        assertThat(result).isPresent();
        assertThat(result.get().getBrand_name()).isEqualTo("Intel");
        assertThat(result.get().getModel_name()).isEqualTo("Intel Core i7-12700K");
    }

    @Test
    void findByModelName_NonExistentModel_ShouldReturnEmpty() {
        Optional<Cpu> result = cpuRepository.findByModelName("Non-existent CPU");

        assertThat(result).isEmpty();
    }

    @Test
    void findAll_ShouldReturnAllCpus() {
        List<Cpu> result = cpuRepository.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).extracting(Cpu::getModel_name)
                .contains("Intel Core i7-12700K", "AMD Ryzen 7 5800X", "AMD Ryzen 5 3600");
    }

    @Test
    void findById_ShouldReturnCpuById() {
        Cpu savedCpu = cpuRepository.findAll().get(0);
        Optional<Cpu> result = cpuRepository.findById(savedCpu.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getModel_name()).isEqualTo(savedCpu.getModel_name());
    }

    @Test
    void save_ShouldPersistNewCpu() {
        Cpu newCpu = new Cpu();
        newCpu.setModel_name("Intel Core i9-13900K");
        newCpu.setBrand_name("Intel");
        newCpu.setSocket("LGA1700");
        newCpu.setAverage_price(599.99);

        Cpu savedCpu = cpuRepository.save(newCpu);

        assertThat(savedCpu.getId()).isNotNull();
        assertThat(savedCpu.getModel_name()).isEqualTo("Intel Core i9-13900K");
        
        // Verify it was actually saved
        Optional<Cpu> found = cpuRepository.findByModelName("Intel Core i9-13900K");
        assertThat(found).isPresent();
    }

    @Test
    void deleteById_ShouldRemoveCpu() {
        Cpu savedCpu = cpuRepository.findAll().get(0);
        Long cpuId = savedCpu.getId();

        cpuRepository.deleteById(cpuId);

        Optional<Cpu> result = cpuRepository.findById(cpuId);
        assertThat(result).isEmpty();
    }

    @Test
    void count_ShouldReturnCorrectCount() {
        long count = cpuRepository.count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void existsById_ShouldReturnTrueForExistingCpu() {
        Cpu savedCpu = cpuRepository.findAll().get(0);
        
        boolean exists = cpuRepository.existsById(savedCpu.getId());
        
        assertThat(exists).isTrue();
    }

    @Test
    void existsById_ShouldReturnFalseForNonExistentCpu() {
        boolean exists = cpuRepository.existsById(999L);
        
        assertThat(exists).isFalse();
    }
}