package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.repository.CasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CasingService {
    private final CasingRepository casingRepository;

    @Autowired
    public CasingService(CasingRepository casingRepository) {
        this.casingRepository = casingRepository;
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

}
