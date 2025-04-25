package com.example.internships.controller;

import com.example.internships.dao.entity.Commentaire;
import com.example.internships.dao.entity.Offre;
import com.example.internships.dao.entity.User;
import com.example.internships.dao.repository.CommentaireRepository;
import com.example.internships.dao.repository.OffreRepository;
import com.example.internships.dao.repository.UserRepository;
import com.example.internships.dto.request.CommentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/offers")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private OffreRepository offerRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/{offerId}/comments")
    public ResponseEntity<?> addComment(@PathVariable Long offerId, @RequestBody CommentRequest request) {
        Optional<Offre> offerOpt = offerRepository.findById(offerId);
        if (!offerOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Offre non trouvée");
        }

        // À adapter avec le vrai utilisateur connecté via sécurité
        User user = userRepository.findById(1L).orElseThrow();

        Commentaire comment = new Commentaire();
        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setOffre(offerOpt.get());
        comment.setAuteur(user);

        commentaireRepository.save(comment);

        return ResponseEntity.ok("Commentaire ajouté");
    }
}
