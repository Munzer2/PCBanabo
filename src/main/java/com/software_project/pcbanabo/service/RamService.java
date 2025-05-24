package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ram;
import com.software_project.pcbanabo.repository.RamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RamService {
    private final RamRepository ramRepository;

    @Autowired
    public RamService(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    public List<Ram> getAllRams() {
        return ramRepository.findAll();
    }

    public Ram getRamById(Long id) {
        return ramRepository.findById(id).orElse(null);
    }

    public void insertRam(Ram ram) {
        ramRepository.save(ram);
    }

    public void deleteRamById(Long id) {
        ramRepository.deleteById(id);
    }

    public void updateRam(Ram ram, Long id) {
        ramRepository.save(ram);
    }
}
