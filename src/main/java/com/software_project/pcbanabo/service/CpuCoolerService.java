package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.CpuCooler;
import com.software_project.pcbanabo.repository.CpuCoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpuCoolerService {
    private final CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    public CpuCoolerService(CpuCoolerRepository cpuCoolerRepository) {
        this.cpuCoolerRepository = cpuCoolerRepository;
    }

    public List<CpuCooler> getAllCpuCoolers() {
        return cpuCoolerRepository.findAll();
    }

    public CpuCooler getCpuCoolerById(Long id) {
        return cpuCoolerRepository.findById(id).orElse(null);
    }

    public List<CpuCooler> getCpuCoolersByTowerHeightLessThanEqual(Integer towerHeight) {
        return cpuCoolerRepository.findByTowerHeightLessThanEqual(towerHeight);
    }

    public List<CpuCooler> getCpuCoolersByTowerHeight(Integer towerHeight) {
        return cpuCoolerRepository.findByTowerHeight(towerHeight);
    }

    public List<CpuCooler> getCpuCoolersByCoolingCapacityIsGreaterThanEqual(Integer coolingCapacity) {
        return cpuCoolerRepository.findByCoolingCapacityIsGreaterThanEqual(coolingCapacity);
    }

    public List<CpuCooler> getCpuCoolersByCoolingCapacity(Integer coolingCapacity) {
        return cpuCoolerRepository.findByCoolingCapacity(coolingCapacity);
    }

    public void insertCpuCooler(CpuCooler cpuCooler) {
        cpuCoolerRepository.save(cpuCooler);
    }

    public void deleteCpuCoolerById(Long id) {
        cpuCoolerRepository.deleteById(id);
    }

    public void updateCpuCooler(CpuCooler cpuCooler, Long id) {
        cpuCoolerRepository.save(cpuCooler);
    }

    public List<CpuCooler> getFilteredCpuCoolers(
            String brandName, String coolerType, Integer towerHeight,
            Integer radiatorSize, Integer coolingCapacity,
            Double minPrice, Double maxPrice) {

        Specification<CpuCooler> spec = Specification.where(null);

        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("cooler_type", coolerType));
        spec = spec.and(integerEquals("towerHeight", towerHeight));
        spec = spec.and(integerEquals("radiator_size", radiatorSize));
        spec = spec.and(integerEquals("coolingCapacity", coolingCapacity));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));

        return cpuCoolerRepository.findAll(spec);
    }

    // Helper methods
    public static Specification<CpuCooler> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static Specification<CpuCooler> integerEquals(String fieldName, Integer value) {
        return (root, query, builder) -> {
            if (value == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static <T extends Comparable<T>> Specification<CpuCooler> rangeBetween(String fieldName, T min, T max) {
        return (root, query, builder) -> {
            if (min == null && max == null)
                return builder.conjunction();
            if (min == null)
                return builder.lessThanOrEqualTo(root.get(fieldName), max);
            if (max == null)
                return builder.greaterThanOrEqualTo(root.get(fieldName), min);
            return builder.between(root.get(fieldName), min, max);
        };
    }
}
