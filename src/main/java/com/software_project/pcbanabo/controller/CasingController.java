package com.software_project.pcbanabo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.service.CasingService;

@RestController
@RequestMapping("/api/components/casings")
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

    /*
    The following API endpoint's been changed.
    Usage: /filtered?motherboardSupport=<val>&psuClearance=<val>&...
    */

    @GetMapping("/filtered")
    public List<Casing> getFilteredCasings(
            // Price range
        @RequestParam(required = false) Double price_gte,
        @RequestParam(required = false) Double price_lte,
        
        // Clearance ranges
        @RequestParam(required = false) Integer psu_gte,
        @RequestParam(required = false) Integer psu_lte,
        @RequestParam(required = false) Integer gpu_gte,
        @RequestParam(required = false) Integer gpu_lte,
        @RequestParam(required = false) Integer cpu_gte,
        @RequestParam(required = false) Integer cpu_lte,

        
        // Lists of options
        @RequestParam(required = false) List<String> brands,
        @RequestParam(required = false) List<String> motherboard,
        @RequestParam(required = false) List<String> color,
        
        // Boolean features
        @RequestParam(required = false) Boolean rgb,
        @RequestParam(required = false) Boolean display
            ){
        return casingService.getFilteredCasings2(
            price_gte, price_lte,
            psu_gte, psu_lte,
            gpu_gte, gpu_lte,
            cpu_gte, cpu_lte,
            brands, motherboard, color,
            rgb, display
        );
    }
    @GetMapping("/{id}")
    public Casing getCasingById(@PathVariable Long id) {
        return casingService.getCasingById(id);
    }
}
