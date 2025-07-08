package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.repository.CasingRepository;
import com.software_project.pcbanabo.repository.MotherboardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

@Service
public class CasingService {
    private final CasingRepository casingRepository;
    private final MotherboardRepository motherboardRepository;

    @Autowired
    public CasingService(CasingRepository casingRepository, MotherboardRepository motherboardRepository) {
        this.casingRepository = casingRepository;
        this.motherboardRepository = motherboardRepository;
    }

    public List<Casing> getAllCasings() {
        return casingRepository.findAll();
    }

    public Casing getCasingById(Long id) {
        return casingRepository.findById(id).orElse(null);
    }

    public void insertCasing(Casing casing) {
        casingRepository.save(casing);
    }

    public void deleteCasingById(Long id) {
        casingRepository.deleteById(id);
    }

    public void updateCasing(Casing casing, Long id) {
        casingRepository.save(casing);
    }

    public Optional<Casing> getCasingByModelName(String modelName) {
        return casingRepository.findByModelName(modelName);
    }

    public List<Casing> getFilteredCasings(String motherboardSupport, Integer psuClearance, Integer gpuClearance,
            Integer cpuClearance) {
        if (motherboardSupport != null && psuClearance != null && gpuClearance != null && cpuClearance != null) {
            return casingRepository
                    .findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(
                            motherboardSupport, psuClearance, gpuClearance, cpuClearance);
        } else if (motherboardSupport != null) {
            return casingRepository.findByMotherboardSupport(motherboardSupport);
        } else {
            return casingRepository.findAll(); // Add more combinations if needed
        }
    }

    public List<Casing> getFilteredCasings2(
            Double priceMin, Double priceMax,
            Integer psuMin, Integer psuMax,
            Integer gpuMin, Integer gpuMax,
            Integer cpuMin, Integer cpuMax,
            List<String> brands, List<String> motherboardTypes, List<String> colors,
            Boolean rgb, Boolean display) {
        Specification<Casing> spec = Specification.where(null);
        // spec = spec.and(priceBetween(priceMin, priceMax));
        spec = spec.and(rangeBetween("avg_price", priceMin, priceMax));
        spec = spec.and(rangeBetween("psuClearance", psuMin, psuMax));
        spec = spec.and(rangeBetween("gpuClearance", gpuMin, gpuMax));
        spec = spec.and(rangeBetween("cpuClearance", cpuMin, cpuMax));

        spec = spec.and(listContains("brand_name", brands));
        spec = spec.and(anyMatch("motherboardSupport", motherboardTypes));
        spec = spec.and(anyMatch("color", colors));

        spec = spec.and(booleanEquals("rgb", rgb));
        spec = spec.and(booleanEquals("display", display));

        return casingRepository.findAll(spec);
    }

    // Generic method for numeric range filters (reusable for all numeric ranges)
    public static <T extends Comparable<T>> Specification<Casing> rangeBetween(String fieldName, T min, T max) {
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

    // Method for list containing filters (exact matches like brands)
    public static Specification<Casing> listContains(String fieldName, List<String> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.conjunction();
            }
            return root.get(fieldName).in(values);
        };
    }

    public static Specification<Casing> anyMatch(String fieldName, List<String> values) {
        return (root, query, builder) -> {
            if (values == null || values.isEmpty()) {
                return builder.conjunction();
            }

            return builder.or(values.stream()
                    .map(value -> builder.like(
                            builder.lower(root.get(fieldName)),
                            "%" + value.toLowerCase() + "%"))
                    .toArray(jakarta.persistence.criteria.Predicate[]::new));
        };
    }

    public static Specification<Casing> booleanEquals(String fieldName, Boolean value) {
        return (root, query, builder) -> {
            if (value == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }
}
