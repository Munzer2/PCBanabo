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

    public List<Motherboard> getMotherboardsBySocket(String socketName) {
        return motherboardRepository.findBySocket(socketName);
    }

    public List<Motherboard> getMotherboardsByFormFactor(String formFactor) {
        return motherboardRepository.findByFormFactor(formFactor);
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

    public List<Motherboard> getFilteredMotherboards(String socket, String formFactor) {
        if (socket != null && formFactor != null) {
            return motherboardRepository.findBySocketAndFormFactor(socket, formFactor);
        } else if (socket != null) {
            return motherboardRepository.findBySocket(socket);
        } else if (formFactor != null) {
            return motherboardRepository.findByFormFactor(formFactor);
        } else {
            return motherboardRepository.findAll();
        }
    }
}
