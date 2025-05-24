package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/components/cpus")
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
}
