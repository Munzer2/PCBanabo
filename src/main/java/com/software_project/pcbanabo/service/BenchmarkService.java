package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Benchmark;
import com.software_project.pcbanabo.repository.BenchmarkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenchmarkService {
    private final BenchmarkRepository benchmarkRepository;

    @Autowired
    public BenchmarkService(BenchmarkRepository benchmarkRepository) {
        this.benchmarkRepository = benchmarkRepository;
    }

    public Benchmark getBenchmarkByCpuId(Long cpuId) {
        // For CPU-only benchmarks, GPU ID should be 0
        return benchmarkRepository.findByCpuIdAndGpuId(cpuId, 0L);
    }

    public Benchmark getBenchmarkByGpuId(Long gpuId) {
        // For GPU-only benchmarks, CPU ID should be 0
        return benchmarkRepository.findByCpuIdAndGpuId(0L, gpuId);
    }

    public Benchmark getBenchmarkByCpuAndGpu (Long cpuId, Long gpuId) {
        System.out.println("Fetching benchmark for CPU ID: " + cpuId + " and GPU ID: " + gpuId);
        
        // First try to find exact CPU+GPU combination
        Benchmark exactMatch = benchmarkRepository.findByCpuIdAndGpuId(cpuId, gpuId);
        if (exactMatch != null) {
            System.out.println("Found exact benchmark match for CPU " + cpuId + " and GPU " + gpuId);
            return exactMatch;
        }
        
        // If no exact match, try to find separate CPU and GPU benchmarks to combine
        Benchmark b1 = getBenchmarkByCpuId(cpuId);
        System.out.println("Fetched benchmark for CPU ID: " + cpuId + " (with GPU ID 0)");
        System.out.println(b1);
        Benchmark b2 = getBenchmarkByGpuId(gpuId);
        System.out.println("Fetched benchmark for GPU ID: " + gpuId + " (with CPU ID 0)");
        System.out.println(b2);
        
        // Check if we have any benchmark data at all
        if (b1 == null && b2 == null) {
            System.out.println("No benchmark data found for CPU " + cpuId + " or GPU " + gpuId);
            return null;
        }
        
        Benchmark b = new Benchmark();
        b.setCpuId(cpuId);
        b.setGpuId(gpuId);
        
        // Safely get CPU benchmark data
        if (b1 != null) {
            b.setCinebenchSingle(b1.getCinebenchSingle());
            b.setCinebenchMulti(b1.getCinebenchMulti());
            b.setGeekbench(b1.getGeekbench());
        }
        
        // Safely get GPU benchmark data
        if (b2 != null) {
            b.setBlender(b2.getBlender());
        }
        
        // For performance-dependent scores, use the minimum (bottleneck) if both exist
        if (b1 != null && b2 != null) {
            b.setPhotoshop(safeMin(b1.getPhotoshop(), b2.getPhotoshop()));
            b.setPremierePro(safeMin(b1.getPremierePro(), b2.getPremierePro()));
            b.setLightroom(safeMin(b1.getLightroom(), b2.getLightroom()));
            b.setDavinci(safeMin(b1.getDavinci(), b2.getDavinci()));
            b.setHorizonZeroDawn(safeMin(b1.getHorizonZeroDawn(), b2.getHorizonZeroDawn()));
            b.setF12024(safeMin(b1.getF12024(), b2.getF12024()));
            b.setValorant(safeMin(b1.getValorant(), b2.getValorant()));
            b.setOverwatch(safeMin(b1.getOverwatch(), b2.getOverwatch()));
            b.setCsgo(safeMin(b1.getCsgo(), b2.getCsgo()));
            b.setFc2025(safeMin(b1.getFc2025(), b2.getFc2025()));
            b.setBlackMythWukong(safeMin(b1.getBlackMythWukong(), b2.getBlackMythWukong()));
        } else if (b1 != null) {
            // Use CPU benchmark values if available
            b.setPhotoshop(b1.getPhotoshop());
            b.setPremierePro(b1.getPremierePro());
            b.setLightroom(b1.getLightroom());
            b.setDavinci(b1.getDavinci());
            b.setHorizonZeroDawn(b1.getHorizonZeroDawn());
            b.setF12024(b1.getF12024());
            b.setValorant(b1.getValorant());
            b.setOverwatch(b1.getOverwatch());
            b.setCsgo(b1.getCsgo());
            b.setFc2025(b1.getFc2025());
            b.setBlackMythWukong(b1.getBlackMythWukong());
        } else if (b2 != null) {
            // Use GPU benchmark values if available
            b.setPhotoshop(b2.getPhotoshop());
            b.setPremierePro(b2.getPremierePro());
            b.setLightroom(b2.getLightroom());
            b.setDavinci(b2.getDavinci());
            b.setHorizonZeroDawn(b2.getHorizonZeroDawn());
            b.setF12024(b2.getF12024());
            b.setValorant(b2.getValorant());
            b.setOverwatch(b2.getOverwatch());
            b.setCsgo(b2.getCsgo());
            b.setFc2025(b2.getFc2025());
            b.setBlackMythWukong(b2.getBlackMythWukong());
        }
        
        System.out.println("Benchmark created: " + b);
        return b;
    }
    
    private Integer safeMin(Integer a, Integer b) {
        if (a == null) return b;
        if (b == null) return a;
        return Math.min(a, b);
    }
}
