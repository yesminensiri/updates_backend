package com.example.internships.controller;

import com.example.internships.dto.request.PackSelectionRequest;
import com.example.internships.dto.response.PackSelectionResponse;
import com.example.internships.service.CurrentUserService;
import com.example.internships.service.PackSelectionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/selections")
@RequiredArgsConstructor
public class PackSelectionController {
    private final PackSelectionService selectionService;
    private final CurrentUserService currentUserService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ENTREPRISE', 'ETUDIANT')")
    public ResponseEntity<PackSelectionResponse> selectPack(
            @Valid @RequestBody PackSelectionRequest request) {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(selectionService.selectPack(userId, request));
    }

    @GetMapping("/my-selections")
    @PreAuthorize("hasAnyRole('ENTREPRISE', 'ETUDIANT')")
    public ResponseEntity<List<PackSelectionResponse>> getUserSelections() {
        Long userId = currentUserService.getCurrentUserId();
        return ResponseEntity.ok(selectionService.getUserSelections(userId));
    }
}