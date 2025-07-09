package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Gpu;
import com.software_project.pcbanabo.service.GpuService;

@RestController
@RequestMapping("/api/components/gpus")
public class GpuController {
    private final GpuService gpuService;

    @Autowired
    public GpuController(GpuService gpuService) {
        this.gpuService = gpuService;
    }

    @GetMapping
    public List<Gpu> getAllGpus() {
        return gpuService.getAllGpus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gpu> getGpuById(@PathVariable Long id) {
        Gpu gpu = gpuService.getGpuById(id);
        return gpu != null ? ResponseEntity.ok(gpu) : ResponseEntity.notFound().build();
    }

    @GetMapping("/card_length/{length}")
    public List<Gpu> getGpusByCardLengthLessThanEqual(@PathVariable int length) {
        return gpuService.getGpusByCardLengthLessThanEqual(length);
    }

    @GetMapping("/filtered")
    public List<Gpu> getFilteredGpus(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String gpuCore,
            @RequestParam(required = false) Integer vramMin,
            @RequestParam(required = false) Integer tdpMax,
            @RequestParam(required = false) Integer cardLengthMax,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return gpuService.getFilteredGpus(brandName, gpuCore, vramMin, 
                                         tdpMax, cardLengthMax, 
                                         minPrice, maxPrice);
    }
}
