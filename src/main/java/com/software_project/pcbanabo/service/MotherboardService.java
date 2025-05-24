package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Motherboard;
import com.software_project.pcbanabo.repository.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotherboardService {
    private final MotherboardRepository motherboardRepository;

    @Autowired
    public MotherboardService(MotherboardRepository motherboardRepository) {
        this.motherboardRepository = motherboardRepository;
    }

    public List<Motherboard> getAllMotherboards() {
        return motherboardRepository.findAll();
    }

    public Motherboard getMotherboardById(Long id) {
        return motherboardRepository.findById(id).orElse(null);
    }

    public void insertMotherboard(Motherboard motherboard) {
        motherboardRepository.save(motherboard);
    }

    public void deleteMotherboardById(Long id) {
        motherboardRepository.deleteById(id);
    }

    public void updateMotherboard(Motherboard motherboard, Long id) {
        motherboardRepository.save(motherboard);
    }
}
