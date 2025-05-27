package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.CpuCooler;
import com.software_project.pcbanabo.repository.CpuCoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CpuCoolerService {
    private final CpuCoolerRepository cpuCoolerRepository;

    @Autowired
    public CpuCoolerService(CpuCoolerRepository cpuCoolerRepository) {
        this.cpuCoolerRepository = cpuCoolerRepository;
    }

    public List<CpuCooler> getAllCpuCoolers() {
        return cpuCoolerRepository.findAll();
    }

    public CpuCooler getCpuCoolerById(Long id) {
        return cpuCoolerRepository.findById(id).orElse(null);
    }

    public List<CpuCooler> getCpuCoolersByTowerHeight(Integer towerHeight) {
        return cpuCoolerRepository.findByTowerHeightLessThanEqual(towerHeight);
    }

    public List<CpuCooler> getCpuCoolersByCoolingCapacity(Integer coolingCapacity) {
        return cpuCoolerRepository.findByCoolingCapacityIsGreaterThanEqual(coolingCapacity);
    }
    public void insertCpuCooler(CpuCooler cpuCooler) {
        cpuCoolerRepository.save(cpuCooler);
    }

    public void deleteCpuCoolerById(Long id) {
        cpuCoolerRepository.deleteById(id);
    }

    public void updateCpuCooler(CpuCooler cpuCooler, Long id) {
        cpuCoolerRepository.save(cpuCooler);
    }
}
