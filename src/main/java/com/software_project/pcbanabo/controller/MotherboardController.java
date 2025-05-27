package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Motherboard;
import com.software_project.pcbanabo.service.MotherboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/components/motherboards")
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
    public List<Motherboard> getMotherboards(
            @RequestParam(required = false) String socket,
            @RequestParam(required = false) String formFactor
    ) {
        return motherboardService.getFilteredMotherboards(socket, formFactor);
    }


    @GetMapping("/id/{id}")
    public Motherboard getMotherboardById(@PathVariable Long id) {
        return motherboardService.getMotherboardById(id);
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
