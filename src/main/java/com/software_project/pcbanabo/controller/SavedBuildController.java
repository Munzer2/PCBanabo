package com.software_project.pcbanabo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.software_project.pcbanabo.dto.SaveBuildRequestDTO;
import com.software_project.pcbanabo.model.SavedBuild;
import com.software_project.pcbanabo.service.SavedBuildService;

@RestController
@RequestMapping("/api/shared-builds")
public class SavedBuildController {

    private final SavedBuildService savedBuildService;

    public SavedBuildController(SavedBuildService savedBuildService) {
        this.savedBuildService = savedBuildService;
    }

    // ✅ Public builds – no authentication needed
    @GetMapping
    public ResponseEntity<List<SavedBuild>> getAllPublicBuilds() {
        return ResponseEntity.ok(savedBuildService.getPublicBuilds());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<SavedBuild>> getFilteredPublicBuilds(
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice
    ) {
        List<SavedBuild> filtered = savedBuildService.getPublicBuildsFilteredByPrice(minPrice, maxPrice);
        return ResponseEntity.ok(filtered);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<SavedBuild> saveBuild(
            @PathVariable Integer userId,
            @RequestBody SaveBuildRequestDTO requestDto
    ) {
        SavedBuild saved = savedBuildService.saveBuild(userId, requestDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{buildId}")
            .buildAndExpand(saved.getId()).toUri(); 
        return ResponseEntity.created(location).body(saved); 
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedBuild>> getUserBuilds(@PathVariable Integer userId) {
        // TODO: Authenticate using JWT and verify userId matches token claims
        List<SavedBuild> userBuilds = savedBuildService.getUserBuilds(userId);
        return ResponseEntity.ok(savedBuildService.getUserBuilds(userId));
    }

    @DeleteMapping("/{buildId}")
    public ResponseEntity<Void> deleteBuild(@PathVariable Integer buildId) {
        boolean deleted = savedBuildService.deleteBuild(buildId);
        if(deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
