package com.example.internships.controller;

import com.example.internships.dao.entity.Offre;
import com.example.internships.service.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offres")
public class OffreController {

    @Autowired
    private OffreService offreService;

    // GET all offres
    @GetMapping("/")
    public List<Offre> getAllOffres() {
        return offreService.getAllOffres();
    }

    // GET an offre by ID
    @GetMapping("/{id}")
    public Offre getOffreById(@PathVariable Long id) {
        return offreService.getOffreById(id);
    }

    // POST a new offre
    @PostMapping("/")
    public Offre createOffre(@RequestBody Offre offre) {
        return offreService.createOffre(offre);
    }

    // PUT (update) an offre by ID
    @PutMapping("/{id}")
    public Offre updateOffre(@PathVariable Long id, @RequestBody Offre updatedOffre) {
        return offreService.updateOffre(id, updatedOffre);
    }

    // DELETE an offre by ID
    @DeleteMapping("/{id}")
    public boolean deleteOffre(@PathVariable Long id) {
        return offreService.deleteOffre(id);
    }
}
