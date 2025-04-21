package com.example.internships.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class PackRequest {
    @NotBlank(message = "Le nom est obligatoire")
    private String nom;  // Changé de 'name' à 'nom'

    private String description;

    @Positive(message = "Le prix doit être positif")
    private double prix; // Changé de 'price' à 'prix'

    @Positive(message = "La durée doit être positive")
    private int duree;   // Changé de 'durationMonths' à 'duree'

    // Getters et Setters
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
}