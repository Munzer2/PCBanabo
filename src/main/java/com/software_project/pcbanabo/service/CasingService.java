package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.repository.CasingRepository;
import com.software_project.pcbanabo.repository.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasingService {
    private final CasingRepository casingRepository;
    private final MotherboardRepository motherboardRepository;

    @Autowired
    public CasingService(CasingRepository casingRepository, MotherboardRepository motherboardRepository) {
        this.casingRepository = casingRepository;
        this.motherboardRepository = motherboardRepository;
    }

    public List<Casing> getAllCasings() {
        return casingRepository.findAll();
    }

    public Casing getCasingById(Long id) {
        return casingRepository.findById(id).orElse(null);
    }

    public void insertCasing(Casing casing) {
        casingRepository.save(casing);
    }

    public void deleteCasingById(Long id) {
        casingRepository.deleteById(id);
    }

    public void updateCasing(Casing casing, Long id) {
        casingRepository.save(casing);
    }

    public List<Casing> getFilteredCasings(String motherboardSupport, Integer psuClearance, Integer gpuClearance, Integer cpuClearance) {
        if (motherboardSupport != null && psuClearance != null && gpuClearance != null && cpuClearance != null) {
            return casingRepository.findByMotherboardSupportAndPsuClearanceGreaterThanEqualAndGpuClearanceGreaterThanEqualAndCpuClearanceGreaterThanEqual(
                    motherboardSupport, psuClearance, gpuClearance, cpuClearance);
        } else if (motherboardSupport != null) {
            return casingRepository.findByMotherboardSupport(motherboardSupport);
        } else {
            return casingRepository.findAll(); // Add more combinations if needed
        }
    }
}
