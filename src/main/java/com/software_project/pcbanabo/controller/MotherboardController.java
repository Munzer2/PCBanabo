package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Motherboard;
import com.software_project.pcbanabo.service.MotherboardService;

@RestController
@RequestMapping("/api/components/motherboards")
public class MotherboardController {
    private final MotherboardService motherboardService;

    @Autowired
    public MotherboardController(MotherboardService motherboardService) {
        this.motherboardService = motherboardService;
    }

    @GetMapping
    public List<Motherboard> getAllMotherboards() {
        return motherboardService.getAllMotherboards();
    }

    /*
    The following API endpoint's
    Usage: /filtered?socket=<val>&formFactor=<val>
    */

    @GetMapping("/filtered")
    public List<Motherboard> getFilteredMotherboards(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String chipset,
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String formFactor,
            @RequestParam(required = false) String memType,
            @RequestParam(required = false) Integer memSlotMin,
            @RequestParam(required = false) Integer memSlotMax,
            @RequestParam(required = false) Integer maxMemSpeedMin,
            @RequestParam(required = false) Integer maxPowerMin,
            @RequestParam(required = false) Integer maxPowerMax,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return motherboardService.getFilteredMotherboards2(
                brandName, chipset, socket, formFactor, memType,
                memSlotMin, memSlotMax, maxMemSpeedMin,
                maxPowerMin, maxPowerMax, minPrice, maxPrice
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Motherboard> getMotherboardById(@PathVariable Long id) {
        Motherboard m = motherboardService.getMotherboardById(id);
        return m != null ? ResponseEntity.ok(m) : ResponseEntity.notFound().build();
    }

    @GetMapping("/socket/{socket}")
    public List<Motherboard> getMotherboardsBySocket(@PathVariable String socket) {
        return motherboardService.getMotherboardsBySocket(socket);
    }

    @GetMapping("/form_factor/{formFactor}")
    public List<Motherboard> getMotherboardsByFormFactor(@PathVariable String formFactor) {
        return motherboardService.getMotherboardsByFormFactor(formFactor);
    }
}
