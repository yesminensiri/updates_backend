package com.example.internships.dao.repository;

import com.example.internships.dao.entity.Candidature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    // Tu peux ajouter des méthodes de recherche personnalisées ici si nécessaire
}
