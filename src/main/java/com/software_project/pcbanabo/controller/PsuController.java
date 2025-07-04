package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Psu;
import com.software_project.pcbanabo.service.PsuService;

@RestController
@RequestMapping("/api/components/psus")
public class PsuController {
    private final PsuService psuService;

    @Autowired
    public PsuController(PsuService psuService) {
        this.psuService = psuService;
    }

    @GetMapping
    public List<Psu> getAllPsus() {
        return psuService.getAllPsus();
    }

    @GetMapping("/{id}")
    public Psu getPsuById(@PathVariable Long id) {
        return psuService.getPsuById(id);
    }

    @GetMapping("/wattage/{wattage}")
    public List<Psu> getPsuByWattageGreaterThanEqual(@PathVariable Integer wattage) {
        return psuService.getPsuByWattageGreaterThanEqual(wattage);
    }

    @GetMapping("/psu_length/{length}")
    public List<Psu> getPsuByPsuLengthLessThanEqual(@PathVariable Integer length) {
        return psuService.getPsuByPsuLengthLessThanEqual(length);
    }
}
