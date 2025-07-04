package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Ssd;
import com.software_project.pcbanabo.service.SsdService;

@RestController
@RequestMapping("/api/components/ssds")
public class SsdController {
    private final SsdService ssdService;

    @Autowired
    public SsdController(SsdService ssdService) {
        this.ssdService = ssdService;
    }

    @GetMapping
    public List<Ssd> getAllSsds() {
        return ssdService.getAllSsds();
    }

    @GetMapping("/{id}")
    public Ssd getSsdById(@PathVariable Long id) {
        return ssdService.getSsdById(id);
    }
}
