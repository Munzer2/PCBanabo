package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.model.Casing;
import com.software_project.pcbanabo.service.CasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /*
    The following API endpoint's
    Usage: /filtered?motherboardSupport=<val>&psuClearance=<val>&...
    */

    @GetMapping("/filtered")
    public List<Casing> getFilteredCasings(
            @RequestParam(required = false) String motherboardSupport,
            @RequestParam(required = false) Integer psuClearance,
            @RequestParam(required = false) Integer gpuClearance,
            @RequestParam(required = false) Integer cpuClearance
            ){
        return casingService.getFilteredCasings(motherboardSupport, psuClearance, gpuClearance, cpuClearance);
    }
    @GetMapping("/{id}")
    public Casing getCasingById(@PathVariable Long id) {
        return casingService.getCasingById(id);
    }
}
