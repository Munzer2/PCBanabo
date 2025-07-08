package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ssd;
import com.software_project.pcbanabo.repository.SsdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SsdService {
    private final SsdRepository ssdRepository;

    @Autowired
    public SsdService(SsdRepository ssdRepository) {
        this.ssdRepository = ssdRepository;
    }

    public List<Ssd> getAllSsds() {
        return ssdRepository.findAll();
    }

    public Ssd getSsdById(Long id) {
        return ssdRepository.findById(id).orElse(null);
    }

    public void insertSsd(Ssd ssd) {
        ssdRepository.save(ssd);
    }

    public void deleteSsdById(Long id) {
        ssdRepository.deleteById(id);
    }

    public void updateSsd(Ssd ssd, Long id) {
        ssdRepository.save(ssd);
    }

    // Filtering method
    public List<Ssd> getFilteredSsds(
            String brandName, String capacity, String formFactor,
            String pcieGen, Integer seqReadMin, Integer seqWriteMin,
            Boolean dramCache, Double minPrice, Double maxPrice) {

        Specification<Ssd> spec = Specification.where(null);

        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("capacity", capacity));
        spec = spec.and(stringEquals("form_factor", formFactor));
        spec = spec.and(stringEquals("pcie_gen", pcieGen));
        spec = spec.and(integerGreaterThanOrEqual("seq_read", seqReadMin));
        spec = spec.and(integerGreaterThanOrEqual("seq_write", seqWriteMin));
        spec = spec.and(booleanEquals("dram_cache", dramCache));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));

        return ssdRepository.findAll(spec);
    }

    // Helper methods
    public static Specification<Ssd> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static Specification<Ssd> integerGreaterThanOrEqual(String fieldName, Integer minValue) {
        return (root, query, builder) -> {
            if (minValue == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
        };
    }

    public static Specification<Ssd> booleanEquals(String fieldName, Boolean value) {
        return (root, query, builder) -> {
            if (value == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static <T extends Comparable<T>> Specification<Ssd> rangeBetween(String fieldName, T min, T max) {
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
