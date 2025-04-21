package com.example.internships.service;

import com.example.internships.dao.entity.Offre;
import com.example.internships.dao.repository.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;

    // CREATE
    public Offre createOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    // READ
    public Offre getOffreById(Long id) {
        return offreRepository.findById(id).orElse(null);
    }

    public List<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    // UPDATE
    public Offre updateOffre(Long id, Offre updatedOffre) {
        if (offreRepository.existsById(id)) {
            updatedOffre.setId(id);
            return offreRepository.save(updatedOffre);
        }
        return null;
    }

    // DELETE
    public boolean deleteOffre(Long id) {
        if (offreRepository.existsById(id)) {
            offreRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
