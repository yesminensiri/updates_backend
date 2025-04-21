package com.example.internships.controller;

import com.example.internships.dto.request.PackRequest;
import com.example.internships.dto.response.PackResponse;
import com.example.internships.service.PackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/packs")
@RequiredArgsConstructor
public class PackController {

    private final PackService packService;

    @GetMapping
    public ResponseEntity<List<PackResponse>> getAllPacks() {
        return ResponseEntity.ok(packService.getAllPacks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PackResponse> getPackById(@PathVariable Long id) {
        return ResponseEntity.ok(packService.getPackById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Corrigé pour utiliser hasAuthority
    public ResponseEntity<PackResponse> createPack(@Valid @RequestBody PackRequest request) {
        PackResponse createdPack = packService.createPack(request);
        return new ResponseEntity<>(createdPack, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<PackResponse> updatePack(
            @PathVariable Long id,
            @Valid @RequestBody PackRequest request) {
        return ResponseEntity.ok(packService.updatePack(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deletePack(@PathVariable Long id) {
        packService.deletePack(id);
        return ResponseEntity.noContent().build();
    }
}