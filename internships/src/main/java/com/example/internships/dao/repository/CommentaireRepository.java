package com.example.internships.dao.repository;

import com.example.internships.dao.entity.Commentaire;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    // Tu peux ajouter des méthodes de recherche personnalisées ici si nécessaire
}
