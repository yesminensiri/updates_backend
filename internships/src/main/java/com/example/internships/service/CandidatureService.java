package com.example.internships.service;

import com.example.internships.dao.entity.Candidature;
import com.example.internships.dao.repository.CandidatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidatureService {

    @Autowired
    private CandidatureRepository candidatureRepository;

    // CREATE
    public Candidature createCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    // READ
    public Candidature getCandidatureById(Long id) {
        return candidatureRepository.findById(id).orElse(null);
    }

    public List<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    // UPDATE
    public Candidature updateCandidature(Long id, Candidature updatedCandidature) {
        if (candidatureRepository.existsById(id)) {
            updatedCandidature.setId(id);
            return candidatureRepository.save(updatedCandidature);
        }
        return null;
    }

    // DELETE
    public boolean deleteCandidature(Long id) {
        if (candidatureRepository.existsById(id)) {
            candidatureRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
