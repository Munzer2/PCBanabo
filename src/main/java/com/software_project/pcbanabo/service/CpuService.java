package com.software_project.pcbanabo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.repository.CpuRepository;
import jakarta.persistence.criteria.Expression;

@Service
public class CpuService {
    private final CpuRepository cpuRepository;

    @Autowired
    public CpuService(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    public Optional<Cpu> getCpuByModelName(String modelName) {
        return cpuRepository.findByModelName(modelName);
    }

    public List<Cpu> getAllCpus() {
        return cpuRepository.findAll();
    }

    public Cpu getCpuById(Long id) {
        return cpuRepository.findById(id).orElse(null);
    }

    public void insertCpu(Cpu cpu) {
        cpuRepository.save(cpu);
    }

    public void deleteCpuById(Long id) {
        cpuRepository.deleteById(id);
    }

    public void updateCpu(Cpu cpu, Long id) {
        cpuRepository.save(cpu);
    }

    public List<Cpu> getFilteredCpus(
            String brandName, String socket,
            Integer tdpMin, Integer tdpMax,
            String cacheSizeMin,
            Boolean overclockable,
            Double minPrice, Double maxPrice) {

        Specification<Cpu> spec = Specification.where(null);

        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("socket", socket));
        spec = spec.and(rangeBetween("tdp", tdpMin, tdpMax));
        spec = spec.and(cacheGreaterThan("cache_size", cacheSizeMin));
        spec = spec.and(booleanEquals("overclockable", overclockable));
        spec = spec.and(rangeBetween("average_price", minPrice, maxPrice));

        return cpuRepository.findAll(spec);
    }

   public static Specification<Cpu> cacheGreaterThan(String fieldName, String minValue) {
    return (root, query, builder) -> {
        if (minValue == null || minValue.trim().isEmpty()) {
            return builder.conjunction();
        }
        // String comparison: "20MB" > "16MB" (lexicographic order)
        return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
    };
}

    // Helper methods (add these if they don't exist)
    public static Specification<Cpu> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static <T extends Comparable<T>> Specification<Cpu> rangeBetween(String fieldName, T min, T max) {
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

    public static Specification<Cpu> booleanEquals(String fieldName, Boolean value) {
        return (root, query, builder) -> {
            if (value == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }
}
