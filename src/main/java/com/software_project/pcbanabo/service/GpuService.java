package com.software_project.pcbanabo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.software_project.pcbanabo.model.Gpu;
import com.software_project.pcbanabo.repository.GpuRepository;

@Service
public class GpuService {
    private final GpuRepository gpuRepository;

    @Autowired
    public GpuService(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    public List<Gpu> getAllGpus() {
        return gpuRepository.findAll();
    }

    public Optional<Gpu> getGpuByModelName(String modelName) {
        return gpuRepository.findByModelName(modelName); 
    }

    public Gpu getGpuById(Long id) {
        return gpuRepository.findById(id).orElse(null);
    }

    public List<Gpu> getGpusByCardLengthLessThanEqual(int cardLength) {
        return gpuRepository.findByCardLengthLessThanEqual(cardLength);
    }

    public void insertGpu(Gpu gpu) {
        gpuRepository.save(gpu);
    }

    public void deleteGpuById(Long id) {
        gpuRepository.deleteById(id);
    }

    public void updateGpu(Gpu gpu, Long id) {
        gpuRepository.save(gpu);
    }

    // New filtering method
    public List<Gpu> getFilteredGpus(
            String brandName, String gpuCore, Integer vramMin,
            Integer tdpMax, Integer cardLengthMax,
            Double minPrice, Double maxPrice) {

        Specification<Gpu> spec = Specification.where(null);

        spec = spec.and(stringEquals("brand_name", brandName));
        spec = spec.and(stringEquals("gpu_core", gpuCore));
        spec = spec.and(integerGreaterThanOrEqual("vram", vramMin));
        spec = spec.and(integerLessThanOrEqual("tdp", tdpMax));
        spec = spec.and(integerLessThanOrEqual("cardLength", cardLengthMax));
        spec = spec.and(rangeBetween("avg_price", minPrice, maxPrice));

        return gpuRepository.findAll(spec);
    }

    // Helper methods
    public static Specification<Gpu> stringEquals(String fieldName, String value) {
        return (root, query, builder) -> {
            if (value == null || value.trim().isEmpty()) {
                return builder.conjunction();
            }
            return builder.equal(root.get(fieldName), value);
        };
    }

    public static Specification<Gpu> integerGreaterThanOrEqual(String fieldName, Integer minValue) {
        return (root, query, builder) -> {
            if (minValue == null) {
                return builder.conjunction();
            }
            return builder.greaterThanOrEqualTo(root.get(fieldName), minValue);
        };
    }

    public static Specification<Gpu> integerLessThanOrEqual(String fieldName, Integer maxValue) {
        return (root, query, builder) -> {
            if (maxValue == null) {
                return builder.conjunction();
            }
            return builder.lessThanOrEqualTo(root.get(fieldName), maxValue);
        };
    }

    public static <T extends Comparable<T>> Specification<Gpu> rangeBetween(String fieldName, T min, T max) {
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
