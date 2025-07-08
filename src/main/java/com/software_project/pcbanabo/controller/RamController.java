package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/filtered")
    public List<Ram> getFilteredRams(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String memType,
            @RequestParam(required = false) String memCapacity,
            @RequestParam(required = false) Integer speedMin,
            @RequestParam(required = false) Boolean rgb,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return ramService.getFilteredRams(brandName, memType, memCapacity, 
                                         speedMin, rgb, minPrice, maxPrice);
    }
}
