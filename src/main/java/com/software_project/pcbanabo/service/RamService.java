package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ram;
import com.software_project.pcbanabo.repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamService {
    private final RamRepository ramRepository;

    @Autowired
    public RamService(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    public List<Ram> getAllRams() {
        return ramRepository.findAll();
    }

    public Ram getRamById(Long id) {
        return ramRepository.findById(id).orElse(null);
    }

    public List<Ram> getRamsByMemType(String memType) {
        return ramRepository.findByMemType(memType);
    }

    public void insertRam(Ram ram) {
        ramRepository.save(ram);
    }

    public void deleteRamById(Long id) {
        ramRepository.deleteById(id);
    }

    public void updateRam(Ram ram, Long id) {
        ramRepository.save(ram);
    }

    // New filtered method
    public List<Ram> getFilteredRams(
            String brandName, String memType, String memCapacity,
            Integer speedMin, Boolean rgb,
            Double minPrice, Double maxPrice) {
        
        Specification<Ram> spec = Specification.where(null);
        
        // String filters (exact matches)
        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("memType", memType));
        spec = spec.and(stringEquals("capacity", memCapacity));
        
        // Range filters
        spec = spec.and(speedGreaterThanOrEqual("speed", speedMin));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));
        
        // Boolean filter
        spec = spec.and(booleanEquals("rgb", rgb));
        
        return ramRepository.findAll(spec);
    }

    public static Specification<Ram> speedGreaterThanOrEqual(String fieldName, Integer minValue) {
    return (root, query, builder) -> {
        if (minValue == null) {
            return builder.conjunction();
        }
        return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
    };
}

    // Helper methods
    public static Specification<Ram> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static <T extends Comparable<T>> Specification<Ram> rangeBetween(String fieldName, T min, T max) {
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

    public static Specification<Ram> booleanEquals(String fieldName, Boolean value) {
        return (root, query, builder) -> {
            if (value == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }
}
