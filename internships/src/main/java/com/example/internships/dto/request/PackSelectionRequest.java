package com.example.internships.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackSelectionRequest {

    @NotNull(message = "L'ID du pack est obligatoire")
    private Long packId;

    @Size(max = 500, message = "Le commentaire ne doit pas dépasser 500 caractères")
    private String commentaire;

    private boolean notificationActive = true;

    private LocalDate dateDebut;

    private LocalDate dateFin;

    // Ajoutez d'autres champs métier si nécessaire
}