package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public CpuCooler getCpuCoolerById(@PathVariable Long id) {
        return cpuCoolerService.getCpuCoolerById(id);
    }

    @GetMapping("/tower_height/{towerHeight}")
    public List<CpuCooler> getCpuCoolersByTowerHeight(@PathVariable Integer towerHeight) {
        return cpuCoolerService.getCpuCoolersByTowerHeight(towerHeight);
    }

    @GetMapping("/cooling_capacity/{coolingCapacity}")
    public List<CpuCooler> getCpuCoolersByCoolingCapacity(@PathVariable Integer coolingCapacity) {
        return cpuCoolerService.getCpuCoolersByCoolingCapacity(coolingCapacity);
    }
}
