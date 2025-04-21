package com.example.internships.dao.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Offre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeOffre type;

    @ManyToOne
    private User entreprise; // L'entreprise qui a créé l'offre

    @OneToMany(mappedBy = "offre")
    private List<Candidature> candidatures;

    @OneToMany(mappedBy = "offre")
    private List<Commentaire> commentaires;

    public enum TypeOffre {
        STAGE,
        TRAVAIL
    }

    // === Getters & Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TypeOffre getType() {
        return type;
    }

    public void setType(TypeOffre type) {
        this.type = type;
    }

    public User getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(User entreprise) {
        this.entreprise = entreprise;
    }

    public List<Candidature> getCandidatures() {
        return candidatures;
    }

    public void setCandidatures(List<Candidature> candidatures) {
        this.candidatures = candidatures;
    }

    public List<Commentaire> getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(List<Commentaire> commentaires) {
        this.commentaires = commentaires;
    }
}
