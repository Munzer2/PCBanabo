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

    // @Test
    // void findByBrandIn_ShouldReturnCpusWithSpecifiedBrands() {
    //     List<Cpu> result = cpuRepository.findByBrandIn(Arrays.asList("Intel"));

    //     assertThat(result).hasSize(1);
    //     assertThat(result.get(0).getBrand()).isEqualTo("Intel");
    //     assertThat(result.get(0).getName()).isEqualTo("Intel Core i7-12700K");
    // }

    // @Test
    // void findByBrandIn_MultipleBrands_ShouldReturnAllMatching() {
    //     List<Cpu> result = cpuRepository.findByBrandIn(Arrays.asList("Intel", "AMD"));

    //     assertThat(result).hasSize(3);
    //     assertThat(result).extracting(Cpu::getBrand).containsOnly("Intel", "AMD");
    // }

    // @Test
    // void findByPriceBetween_ShouldReturnCpusInPriceRange() {
    //     List<Cpu> result = cpuRepository.findByPriceBetween(300.0, 400.0);

    //     assertThat(result).hasSize(2);
    //     assertThat(result).allMatch(cpu -> cpu.getPrice() >= 300.0 && cpu.getPrice() <= 400.0);
    //     assertThat(result).extracting(Cpu::getName).contains("Intel Core i7-12700K", "AMD Ryzen 7 5800X");
    // }

    // @Test
    // void findBySocketIn_ShouldReturnCpusWithSpecifiedSockets() {
    //     List<Cpu> result = cpuRepository.findBySocketIn(Arrays.asList("AM4"));

    //     assertThat(result).hasSize(2);
    //     assertThat(result).allMatch(cpu -> cpu.getSocket().equals("AM4"));
    //     assertThat(result).extracting(Cpu::getName).contains("AMD Ryzen 7 5800X", "AMD Ryzen 5 3600");
    // }

    // @Test
    // void findByCoresBetween_ShouldReturnCpusInCoreRange() {
    //     List<Cpu> result = cpuRepository.findByCoresBetween(6, 10);

    //     assertThat(result).hasSize(2);
    //     assertThat(result).allMatch(cpu -> cpu.getCores() >= 6 && cpu.getCores() <= 10);
    //     assertThat(result).extracting(Cpu::getName).contains("AMD Ryzen 7 5800X", "AMD Ryzen 5 3600");
    // }

    // @Test
    // void findByThreadsBetween_ShouldReturnCpusInThreadRange() {
    //     List<Cpu> result = cpuRepository.findByThreadsBetween(12, 20);

    //     assertThat(result).hasSize(3);
    //     assertThat(result).allMatch(cpu -> cpu.getThreads() >= 12 && cpu.getThreads() <= 20);
    // }

    // @Test
    // void findByBrandAndSocket_ShouldReturnSpecificCombination() {
    //     List<Cpu> result = cpuRepository.findByBrandIn(Arrays.asList("AMD"));
    //     result = result.stream()
    //             .filter(cpu -> Arrays.asList("AM4").contains(cpu.getSocket()))
    //             .toList();

    //     assertThat(result).hasSize(2);
    //     assertThat(result).allMatch(cpu -> cpu.getBrand().equals("AMD") && cpu.getSocket().equals("AM4"));
    // }
}