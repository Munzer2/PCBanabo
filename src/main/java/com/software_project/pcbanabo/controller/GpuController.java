package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public Gpu getGpuById(@PathVariable Long id) {
        return gpuService.getGpuById(id);
    }

    @GetMapping("/card_length/{length}")
    public List<Gpu> getGpusByCardLengthLessThanEqual(@PathVariable int length) {
        return gpuService.getGpusByCardLengthLessThanEqual(length);
    }
}
