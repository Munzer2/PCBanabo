package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Benchmark;
import com.software_project.pcbanabo.model.SavedBuild;
import com.software_project.pcbanabo.service.BenchmarkService;
import com.software_project.pcbanabo.service.SavedBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benchmarks")
public class BenchmarkController {
    private final BenchmarkService benchmarkService;
    private final SavedBuildService savedBuildService;

    @Autowired
    public BenchmarkController (BenchmarkService benchmarkService, SavedBuildService savedBuildService) {
        this.benchmarkService = benchmarkService;
        this.savedBuildService = savedBuildService;
    }

    @GetMapping("/{id")
    public Benchmark getBenchmarkByBuildId (@PathVariable Integer id) {
        SavedBuild savedBuild = savedBuildService.getBuildById(id);
        Long cpu = (long) savedBuild.getCpuId();
        Long gpu = (long) savedBuild.getGpuId();
        return benchmarkService.getBenchmarkByCpuAndGpu(cpu, gpu);
    }
}
