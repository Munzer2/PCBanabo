package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Psu;
import com.software_project.pcbanabo.repository.PsuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PsuService {
    private final PsuRepository psuRepository;

    @Autowired
    public PsuService(PsuRepository psuRepository) {
        this.psuRepository = psuRepository;
    }

    public List<Psu> getAllPsus() {
        return psuRepository.findAll();
    }

    public Psu getPsuById(Long id) {
        return psuRepository.findById(id).orElse(null);
    }

    public List<Psu> getPsuByWattageGreaterThanEqual(Integer wattage) {
        return psuRepository.findByWattageGreaterThanEqual(wattage);
    }

    public void insertPsu(Psu psu) {
        psuRepository.save(psu);
    }

    public void deletePsuById(Long id) {
        psuRepository.deleteById(id);
    }

    public void updatePsu(Psu psu, Long id) {
        psuRepository.save(psu);
    }
}
