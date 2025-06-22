package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Cpu;
import com.software_project.pcbanabo.repository.CpuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpuService {
    private final CpuRepository cpuRepository;

    @Autowired
    public CpuService(CpuRepository cpuRepository) {
        this.cpuRepository = cpuRepository;
    }

    public List<Cpu> getAllCpus() {
        return cpuRepository.findAll();
    }

    public Cpu getCpuById(Long id) {
        return cpuRepository.findById(id).orElse(null);
    }

    public void insertCpu(Cpu cpu) {
        cpuRepository.save(cpu);
    }

    public void deleteCpuById(Long id) {
        cpuRepository.deleteById(id);
    }

    public void updateCpu(Cpu cpu, Long id) {
        cpuRepository.save(cpu);
    }

    public void save(Cpu cpu) {
        // TODO: Implement saving logic, e.g., save to database or in-memory list
        // For now, just print to verify
        System.out.println("Saving CPU: " + cpu);
    }
}
