package com.software_project.pcbanabo.repository;

import com.software_project.pcbanabo.model.CpuCooler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CpuCoolerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CpuCoolerRepository cpuCoolerRepository;

    private CpuCooler airCooler;
    private CpuCooler liquidCooler;
    private CpuCooler budgetCooler;

    @BeforeEach
    void setUp() {
        airCooler = new CpuCooler();
        airCooler.setModel_name("Noctua NH-D15");
        airCooler.setBrand_name("Noctua");
        airCooler.setCooler_type("Air");
        airCooler.setTowerHeight(165);
        airCooler.setCoolingCapacity(220);
        airCooler.setAvg_price(99.99);

        liquidCooler = new CpuCooler();
        liquidCooler.setModel_name("Corsair H150i Elite Capellix");
        liquidCooler.setBrand_name("Corsair");
        liquidCooler.setCooler_type("Liquid");
        liquidCooler.setRadiator_size(360);
        liquidCooler.setCoolingCapacity(300);
        liquidCooler.setAvg_price(199.99);

        budgetCooler = new CpuCooler();
        budgetCooler.setModel_name("Cooler Master Hyper 212");
        budgetCooler.setBrand_name("Cooler Master");
        budgetCooler.setCooler_type("Air");
        budgetCooler.setTowerHeight(158);
        budgetCooler.setCoolingCapacity(150);
        budgetCooler.setAvg_price(39.99);

        entityManager.persistAndFlush(airCooler);
        entityManager.persistAndFlush(liquidCooler);
        entityManager.persistAndFlush(budgetCooler);
    }

    @Test
    void findAll_ShouldReturnAllCpuCoolers() {
        List<CpuCooler> result = cpuCoolerRepository.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).extracting(CpuCooler::getModel_name)
                .contains("Noctua NH-D15", "Corsair H150i Elite Capellix", "Cooler Master Hyper 212");
    }

    @Test
    void findById_ShouldReturnCpuCoolerById() {
        CpuCooler savedCooler = cpuCoolerRepository.findAll().get(0);
        Optional<CpuCooler> result = cpuCoolerRepository.findById(savedCooler.getId());

        assertThat(result).isPresent();
        assertThat(result.get().getModel_name()).isEqualTo(savedCooler.getModel_name());
    }

    @Test
    void findByTowerHeightLessThanEqual_ShouldReturnMatchingCoolers() {
        List<CpuCooler> result = cpuCoolerRepository.findByTowerHeightLessThanEqual(160);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModel_name()).isEqualTo("Cooler Master Hyper 212");
        assertThat(result.get(0).getTowerHeight()).isLessThanOrEqualTo(160);
    }

    @Test
    void findByTowerHeight_ShouldReturnExactMatches() {
        List<CpuCooler> result = cpuCoolerRepository.findByTowerHeight(165);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModel_name()).isEqualTo("Noctua NH-D15");
        assertThat(result.get(0).getTowerHeight()).isEqualTo(165);
    }

    @Test
    void findByCoolingCapacityIsGreaterThanEqual_ShouldReturnMatchingCoolers() {
        List<CpuCooler> result = cpuCoolerRepository.findByCoolingCapacityIsGreaterThanEqual(200);

        assertThat(result).hasSize(2);
        assertThat(result).extracting(CpuCooler::getCoolingCapacity)
                .allMatch(capacity -> capacity >= 200);
    }

    @Test
    void findByCoolingCapacity_ShouldReturnExactMatches() {
        List<CpuCooler> result = cpuCoolerRepository.findByCoolingCapacity(220);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getModel_name()).isEqualTo("Noctua NH-D15");
        assertThat(result.get(0).getCoolingCapacity()).isEqualTo(220);
    }

    @Test
    void save_ShouldPersistNewCpuCooler() {
        CpuCooler newCooler = new CpuCooler();
        newCooler.setModel_name("Arctic Liquid Freezer II 280");
        newCooler.setBrand_name("Arctic");
        newCooler.setCooler_type("Liquid");
        newCooler.setRadiator_size(280);
        newCooler.setCoolingCapacity(280);
        newCooler.setAvg_price(129.99);

        CpuCooler savedCooler = cpuCoolerRepository.save(newCooler);

        assertThat(savedCooler.getId()).isNotNull();
        assertThat(savedCooler.getModel_name()).isEqualTo("Arctic Liquid Freezer II 280");
        
        // Verify it was actually saved
        List<CpuCooler> allCoolers = cpuCoolerRepository.findAll();
        assertThat(allCoolers).hasSize(4);
    }

    @Test
    void deleteById_ShouldRemoveCpuCooler() {
        CpuCooler savedCooler = cpuCoolerRepository.findAll().get(0);
        Long coolerId = savedCooler.getId();

        cpuCoolerRepository.deleteById(coolerId);

        Optional<CpuCooler> result = cpuCoolerRepository.findById(coolerId);
        assertThat(result).isEmpty();
    }

    @Test
    void count_ShouldReturnCorrectCount() {
        long count = cpuCoolerRepository.count();
        assertThat(count).isEqualTo(3);
    }

    @Test
    void existsById_ShouldReturnTrueForExistingCooler() {
        CpuCooler savedCooler = cpuCoolerRepository.findAll().get(0);
        
        boolean exists = cpuCoolerRepository.existsById(savedCooler.getId());
        
        assertThat(exists).isTrue();
    }

    @Test
    void existsById_ShouldReturnFalseForNonExistentCooler() {
        boolean exists = cpuCoolerRepository.existsById(999L);
        
        assertThat(exists).isFalse();
    }

    @Test
    void findByTowerHeightLessThanEqual_WithNoMatches_ShouldReturnEmpty() {
        List<CpuCooler> result = cpuCoolerRepository.findByTowerHeightLessThanEqual(100);

        assertThat(result).isEmpty();
    }

    @Test
    void findByCoolingCapacityIsGreaterThanEqual_WithNoMatches_ShouldReturnEmpty() {
        List<CpuCooler> result = cpuCoolerRepository.findByCoolingCapacityIsGreaterThanEqual(400);

        assertThat(result).isEmpty();
    }
}
