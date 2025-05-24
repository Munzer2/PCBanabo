package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Gpu;
import com.software_project.pcbanabo.repository.GpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GpuService {
    private final GpuRepository gpuRepository;

    @Autowired
    public GpuService(GpuRepository gpuRepository) {
        this.gpuRepository = gpuRepository;
    }

    public List<Gpu> getAllGpus() {
        return gpuRepository.findAll();
    }

    public Gpu getGpuById(Long id) {
        return gpuRepository.findById(id).orElse(null);
    }

    public void insertGpu(Gpu gpu) {
        gpuRepository.save(gpu);
    }

    public void deleteGpuById(Long id) {
        gpuRepository.deleteById(id);
    }

    public void updateGpu(Gpu gpu, Long id) {
        gpuRepository.save(gpu);
    }
}
