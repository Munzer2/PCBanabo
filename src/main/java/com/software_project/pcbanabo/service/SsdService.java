package com.software_project.pcbanabo.service;

import com.software_project.pcbanabo.model.Ssd;
import com.software_project.pcbanabo.repository.SsdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SsdService {
    private final SsdRepository ssdRepository;

    @Autowired
    public SsdService(SsdRepository ssdRepository) {
        this.ssdRepository = ssdRepository;
    }

    public List<Ssd> getAllSsds() {
        return ssdRepository.findAll();
    }

    public Ssd getSsdById(Long id) {
        return ssdRepository.findById(id).orElse(null);
    }

    public void insertSsd(Ssd ssd) {
        ssdRepository.save(ssd);
    }

    public void deleteSsdById(Long id) {
        ssdRepository.deleteById(id);
    }

    public void updateSsd(Ssd ssd, Long id) {
        ssdRepository.save(ssd);
    }
}
