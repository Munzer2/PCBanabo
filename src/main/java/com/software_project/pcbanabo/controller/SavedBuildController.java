package com.software_project.pcbanabo.controller;

import com.software_project.pcbanabo.dto.SaveBuildRequestDTO;
import com.software_project.pcbanabo.model.SavedBuild;
import com.software_project.pcbanabo.service.SavedBuildService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/saved-builds")
    public ResponseEntity<?> saveBuild(
            @RequestBody SaveBuildRequestDTO requestDto
            /* TODO: Extract authenticated userId from JWT here */
    ) {
        int userId = 1; // Placeholder – extract from JWT in real use
        SavedBuild savedBuild = savedBuildService.saveBuild(userId, requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBuild);
    }



    // ✅ User-specific builds – authentication required
    @GetMapping("/{userId}")
    public ResponseEntity<List<SavedBuild>> getUserBuilds(@PathVariable Integer userId) {
        // TODO: Authenticate using JWT and verify userId matches token claims
        return ResponseEntity.ok(savedBuildService.getUserBuilds(userId));
    }
}
