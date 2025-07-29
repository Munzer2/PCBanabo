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

    public Benchmark getBenchmarkByCpuId (Long cpuId) {
        return benchmarkRepository.findByCpuId(cpuId);
    }

    public Benchmark getBenchmarkByGpuId (Long gpuId) {
        return benchmarkRepository.findByGpuId(gpuId);
    }

    public Benchmark getBenchmarkByCpuAndGpu (Long cpuId, Long gpuId) {
        Benchmark b1 = getBenchmarkByCpuId(cpuId);
        Benchmark b2 = getBenchmarkByGpuId(gpuId);
        Benchmark b = new Benchmark();
        b.setCpuId(cpuId);
        b.setGpuId(gpuId);
        b.setCinebenchSingle(b1.getCinebenchSingle());
        b.setCinebenchMulti(b1.getCinebenchMulti());
        b.setBlender(b2.getBlender());
        b.setGeekbench(b1.getGeekbench());
        b.setPhotoshop(b1.getPhotoshop() < b2.getPhotoshop() ? b1.getPhotoshop() : b2.getPhotoshop());
        b.setPremierePro(b1.getPremierePro() < b2.getPremierePro() ? b1.getPremierePro() : b2.getPremierePro());
        b.setLightroom(b1.getLightroom() < b2.getLightroom() ? b1.getLightroom() : b2.getLightroom());
        b.setDavinci(b1.getDavinci() < b2.getDavinci() ? b1.getDavinci() : b2.getDavinci());
        b.setHorizonZeroDawn(b1.getHorizonZeroDawn() < b2.getHorizonZeroDawn() ? b1.getHorizonZeroDawn() : b2.getHorizonZeroDawn());
        b.setF12024(b1.getF12024() < b2.getF12024() ? b1.getF12024() : b2.getF12024());
        b.setValorant(b1.getValorant() < b2.getValorant() ? b1.getValorant() : b2.getValorant());
        b.setOverwatch(b1.getOverwatch() < b2.getOverwatch() ? b1.getOverwatch() : b2.getOverwatch());
        b.setCsgo(b1.getCsgo() < b2.getCsgo() ? b1.getCsgo() : b2.getCsgo());
        b.setFc2025(b1.getFc2025() < b2.getFc2025() ? b1.getFc2025() : b2.getFc2025());
        b.setBlackMythWukong(b1.getBlackMythWukong() < b2.getBlackMythWukong() ? b1.getBlackMythWukong() : b2.getBlackMythWukong());
        return b;
    }
}
