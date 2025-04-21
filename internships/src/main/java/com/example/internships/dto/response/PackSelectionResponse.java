package com.example.internships.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackSelectionResponse {
    private Long selectionId;
    private Long packId;
    private String packNom;
    private double packPrix;
    private int packDuree;
    private LocalDateTime dateSelection;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String commentaire;
    private boolean notificationActive;
    private String statut; // "ACTIF", "EXPIRE", "ANNULE"
}