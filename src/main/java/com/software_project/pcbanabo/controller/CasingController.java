package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.service.CasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/components/casings")
public class CasingController {
    private CasingService casingService;

    @Autowired
    public CasingController(CasingService casingService) {
        this.casingService = casingService;
    }

    @GetMapping
    public List<Casing> getAllCasings() {
        return casingService.getAllCasings();
    }

    @GetMapping("/{id}")
    public Casing getCasingById(@PathVariable Long id) {
        return casingService.getCasingById(id);
    }
}
