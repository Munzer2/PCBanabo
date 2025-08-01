package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.model.CpuCooler;
import com.software_project.pcbanabo.service.CpuCoolerService;

@RestController
@RequestMapping("/api/components/cpu-coolers")
public class CpuCoolerController {
    private final CpuCoolerService cpuCoolerService;

    @Autowired
    public CpuCoolerController(CpuCoolerService cpuCoolerService) {
        this.cpuCoolerService = cpuCoolerService;
    }

    @GetMapping
    public List<CpuCooler> getAllCpuCoolers() {
        return cpuCoolerService.getAllCpuCoolers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CpuCooler> getCpuCoolerById(@PathVariable Long id) {
        CpuCooler c = cpuCoolerService.getCpuCoolerById(id);
        return (c != null) ? ResponseEntity.ok(c) : ResponseEntity.notFound().build();
    }

    @GetMapping("/tower_height/{towerHeight}")
    public List<CpuCooler> getCpuCoolersByTowerHeight(@PathVariable Integer towerHeight) {
        return cpuCoolerService.getCpuCoolersByTowerHeight(towerHeight);
    }

    @GetMapping("/cooling_capacity/{coolingCapacity}")
    public List<CpuCooler> getCpuCoolersByCoolingCapacity(@PathVariable Integer coolingCapacity) {
        return cpuCoolerService.getCpuCoolersByCoolingCapacity(coolingCapacity);
    }

    @GetMapping("/filtered")
    public List<CpuCooler> getFilteredCpuCoolers(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String coolerType,
            @RequestParam(required = false) Integer towerHeight,
            @RequestParam(required = false) Integer radiatorSize,
            @RequestParam(required = false) Integer coolingCapacity,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String socket
    ) {
        return cpuCoolerService.getFilteredCpuCoolers(brandName, coolerType, towerHeight, 
                                                     radiatorSize, coolingCapacity, 
                                                     minPrice, maxPrice, socket);
    }
}
