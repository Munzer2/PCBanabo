package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.service.CpuService;

@RestController
@RequestMapping("/api/components/cpus")
public class CpuController {
    private final CpuService cpuService;

    @Autowired
    public CpuController(CpuService cpuService) {
        this.cpuService = cpuService;
    }

    @GetMapping
    public List<Cpu> getAllCpus() {
        return cpuService.getAllCpus();
    }

    @GetMapping("/{id}")
    public Cpu getCpuById(@PathVariable Long id) {
        return cpuService.getCpuById(id);
    }

    @GetMapping("/filtered") 
    public List<Cpu> getFilteredCpus(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) Integer tdpMin,
            @RequestParam(required = false) Integer tdpMax,
            @RequestParam(required = false) String cacheSizeMin,
            @RequestParam(required = false) Boolean overclockable,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return cpuService.getFilteredCpus(brandName, socket, tdpMin, tdpMax, 
                                         cacheSizeMin, overclockable, 
                                         minPrice, maxPrice);
    }
}
