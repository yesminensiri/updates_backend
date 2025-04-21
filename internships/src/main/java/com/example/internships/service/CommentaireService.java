package com.example.internships.service;

import com.example.internships.dao.entity.Commentaire;
import com.example.internships.dao.repository.CommentaireRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentaireService {

    @Autowired
    private CommentaireRepository commentaireRepository;

    // CREATE
    public Commentaire createCommentaire(Commentaire commentaire) {
        return commentaireRepository.save(commentaire);
    }

    // READ
    public Commentaire getCommentaireById(Long id) {
        return commentaireRepository.findById(id).orElse(null);
    }

    public List<Commentaire> getAllCommentaires() {
        return commentaireRepository.findAll();
    }

    // UPDATE
    public Commentaire updateCommentaire(Long id, Commentaire updatedCommentaire) {
        if (commentaireRepository.existsById(id)) {
            updatedCommentaire.setId(id);
            return commentaireRepository.save(updatedCommentaire);
        }
        return null;
    }

    // DELETE
    public boolean deleteCommentaire(Long id) {
        if (commentaireRepository.existsById(id)) {
            commentaireRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
