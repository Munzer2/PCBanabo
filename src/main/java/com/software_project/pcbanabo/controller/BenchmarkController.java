package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Benchmark;
import com.software_project.pcbanabo.model.SavedBuild;
import com.software_project.pcbanabo.service.BenchmarkService;
import com.software_project.pcbanabo.service.SavedBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/benchmarks")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://127.0.0.1:3000", "http://127.0.0.1:5173"})
public class BenchmarkController {
    private final BenchmarkService benchmarkService;
    private final SavedBuildService savedBuildService;

    @Autowired
    public BenchmarkController (BenchmarkService benchmarkService, SavedBuildService savedBuildService) {
        this.benchmarkService = benchmarkService;
        this.savedBuildService = savedBuildService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Benchmark> getBenchmarkByBuildId (@PathVariable Integer id) {
        try {
            SavedBuild savedBuild = savedBuildService.getBuildById(id);
            if (savedBuild == null) {
                return ResponseEntity.notFound().build();
            }
            
            Long cpu = (long) savedBuild.getCpuId();
            Long gpu = (long) savedBuild.getGpuId();
            
            Benchmark benchmark = benchmarkService.getBenchmarkByCpuAndGpu(cpu, gpu);
            if (benchmark == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(benchmark);
        } catch (Exception e) {
            System.err.println("Error getting benchmark for build ID " + id + ": " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
