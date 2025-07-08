package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Psu;
import com.software_project.pcbanabo.repository.PsuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsuService {
    private final PsuRepository psuRepository;

    @Autowired
    public PsuService(PsuRepository psuRepository) {
        this.psuRepository = psuRepository;
    }

    public List<Psu> getAllPsus() {
        return psuRepository.findAll();
    }

    public Psu getPsuById(Long id) {
        return psuRepository.findById(id).orElse(null);
    }

    public List<Psu> getPsuByWattageGreaterThanEqual(Integer wattage) {
        return psuRepository.findByWattageGreaterThanEqual(wattage);
    }

    public List<Psu> getPsuByPsuLengthLessThanEqual(Integer psuLength) {
        return psuRepository.findByPsuLengthLessThanEqual(psuLength);
    }

    public void insertPsu(Psu psu) {
        psuRepository.save(psu);
    }

    public void deletePsuById(Long id) {
        psuRepository.deleteById(id);
    }

    public void updatePsu(Psu psu, Long id) {
        psuRepository.save(psu);
    }

    public List<Psu> getFilteredPsus(
            String brandName, String formFactor, Integer wattageMin,
            Integer psuLengthMax, String certification,
            Double minPrice, Double maxPrice) {

        Specification<Psu> spec = Specification.where(null);

        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("form_factor", formFactor));
        spec = spec.and(integerGreaterThanOrEqual("wattage", wattageMin));
        spec = spec.and(integerLessThanOrEqual("psuLength", psuLengthMax));
        spec = spec.and(stringEquals("certification", certification));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));

        return psuRepository.findAll(spec);
    }

    // Helper methods
    public static Specification<Psu> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static Specification<Psu> integerGreaterThanOrEqual(String fieldName, Integer minValue) {
        return (root, query, builder) -> {
            if (minValue == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
        };
    }

    public static Specification<Psu> integerLessThanOrEqual(String fieldName, Integer maxValue) {
        return (root, query, builder) -> {
            if (maxValue == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get(fieldName), maxValue);
        };
    }

    public static <T extends Comparable<T>> Specification<Psu> rangeBetween(String fieldName, T min, T max) {
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
