package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Ram;
import com.software_project.pcbanabo.service.RamService;

@RestController
@RequestMapping("/api/components/rams")
public class RamController {
    private final RamService ramService;

    @Autowired
    public RamController(RamService ramService) {
        this.ramService = ramService;
    }

    @GetMapping
    public List<Ram> getAllRams() {
        return ramService.getAllRams();
    }

    @GetMapping("/{id}")
    public Ram getRamById(@PathVariable Long id) {
        return ramService.getRamById(id);
    }

    @GetMapping("/mem_type/{memType}")
    public List<Ram> getRamsByMemType(@PathVariable String memType) {
        return ramService.getRamsByMemType(memType);
    }
}
