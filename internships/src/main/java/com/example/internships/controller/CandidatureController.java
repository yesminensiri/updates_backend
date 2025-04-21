package com.example.internships.controller;

import com.example.internships.dao.entity.Candidature;
import com.example.internships.service.CandidatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidatures")
public class CandidatureController {

    @Autowired
    private CandidatureService candidatureService;

    // Create
    @PostMapping
    public Candidature createCandidature(@RequestBody Candidature candidature) {
        return candidatureService.createCandidature(candidature);
    }

    // Read by ID
    @GetMapping("/{id}")
    public Candidature getCandidatureById(@PathVariable Long id) {
        return candidatureService.getCandidatureById(id);
    }

    // Read all
    @GetMapping
    public List<Candidature> getAllCandidatures() {
        return candidatureService.getAllCandidatures();
    }

    // Update
    @PutMapping("/{id}")
    public Candidature updateCandidature(@PathVariable Long id, @RequestBody Candidature updatedCandidature) {
        return candidatureService.updateCandidature(id, updatedCandidature);
    }

    // Delete
    @DeleteMapping("/{id}")
    public boolean deleteCandidature(@PathVariable Long id) {
        return candidatureService.deleteCandidature(id);
    }
}
