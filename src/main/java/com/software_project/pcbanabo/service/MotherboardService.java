package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Motherboard;
import com.software_project.pcbanabo.repository.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotherboardService {
    private final MotherboardRepository motherboardRepository;

    @Autowired
    public MotherboardService(MotherboardRepository motherboardRepository) {
        this.motherboardRepository = motherboardRepository;
    }

    public List<Motherboard> getAllMotherboards() {
        return motherboardRepository.findAll();
    }

    public Motherboard getMotherboardById(Long id) {
        return motherboardRepository.findById(id).orElse(null);
    }

    public List<Motherboard> getMotherboardsBySocket(String socketName) {
        return motherboardRepository.findBySocket(socketName);
    }

    public List<Motherboard> getMotherboardsByFormFactor(String formFactor) {
        return motherboardRepository.findByFormFactor(formFactor);
    }

    public void insertMotherboard(Motherboard motherboard) {
        motherboardRepository.save(motherboard);
    }

    public void deleteMotherboardById(Long id) {
        motherboardRepository.deleteById(id);
    }

    public void updateMotherboard(Motherboard motherboard, Long id) {
        motherboardRepository.save(motherboard);
    }

    public List<Motherboard> getFilteredMotherboards(String socket, String formFactor) {
        if (socket != null && formFactor != null) {
            return motherboardRepository.findBySocketAndFormFactor(socket, formFactor);
        } else if (socket != null) {
            return motherboardRepository.findBySocket(socket);
        } else if (formFactor != null) {
            return motherboardRepository.findByFormFactor(formFactor);
        } else {
            return motherboardRepository.findAll();
        }
    }

    public List<Motherboard> getFilteredMotherboards2(
            String brandName, String chipset, String socket, String formFactor, String memType,
            Integer memSlotMin, Integer memSlotMax,
            Integer maxMemSpeedMin,
            Integer maxPowerMin, Integer maxPowerMax,
            Double minPrice, Double maxPrice) {

        Specification<Motherboard> spec = Specification.where(null);

        // String filters
        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("chipset", chipset));
        spec = spec.and(stringEquals("socket", socket));
        spec = spec.and(stringEquals("formFactor", formFactor));
        spec = spec.and(stringEquals("mem_type", memType));

        // Range filters
        spec = spec.and(rangeBetween("mem_slot", memSlotMin, memSlotMax));
        spec = spec.and(integerGreaterThanOrEqual("max_mem_speed", maxMemSpeedMin));
        spec = spec.and(rangeBetween("max_power", maxPowerMin, maxPowerMax));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));

        return motherboardRepository.findAll(spec);
    }

    public static Specification<Motherboard> integerGreaterThanOrEqual(String fieldName, Integer minValue) {
        return (root, query, builder) -> {
            if (minValue == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
        };
    }

    public static Specification<Motherboard> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static <T extends Comparable<T>> Specification<Motherboard> rangeBetween(String fieldName, T min, T max) {
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
