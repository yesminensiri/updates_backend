package com.example.internships.controller;

import com.example.internships.dao.entity.Commentaire;
import com.example.internships.service.CommentaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireService commentaireService;

    // GET all commentaires
    @GetMapping("/")
    public List<Commentaire> getAllCommentaires() {
        return commentaireService.getAllCommentaires();
    }

    // GET a commentaire by ID
    @GetMapping("/{id}")
    public Commentaire getCommentaireById(@PathVariable Long id) {
        return commentaireService.getCommentaireById(id);
    }

    // POST a new commentaire
    @PostMapping("/")
    public Commentaire createCommentaire(@RequestBody Commentaire commentaire) {
        return commentaireService.createCommentaire(commentaire);
    }

    // PUT (update) a commentaire by ID
    @PutMapping("/{id}")
    public Commentaire updateCommentaire(@PathVariable Long id, @RequestBody Commentaire updatedCommentaire) {
        return commentaireService.updateCommentaire(id, updatedCommentaire);
    }

    // DELETE a commentaire by ID
    @DeleteMapping("/{id}")
    public boolean deleteCommentaire(@PathVariable Long id) {
        return commentaireService.deleteCommentaire(id);
    }
}
