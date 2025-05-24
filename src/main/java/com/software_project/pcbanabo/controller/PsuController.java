package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Psu;
import com.software_project.pcbanabo.service.PsuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/components/psus")
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
}
