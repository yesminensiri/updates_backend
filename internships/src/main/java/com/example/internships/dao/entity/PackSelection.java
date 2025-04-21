package com.example.internships.dao.entity;
import com.example.internships.dto.response.PackSelectionResponse;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pack_selections")
@EqualsAndHashCode(exclude = {"user", "pack"})
public class PackSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id", nullable = false)
    private Pack pack;

    @Column(nullable = false)
    private LocalDateTime dateSelection = LocalDateTime.now();

    private LocalDate dateDebut;

    private LocalDate dateFin;

    @Column(length = 500)
    private String commentaire;

    private boolean notificationActive = true;

    @Enumerated(EnumType.STRING)
    private StatutSelection statut = StatutSelection.ACTIF;

    public enum StatutSelection {
        ACTIF,
        EXPIRE,
        ANNULE
    }

    // Méthode utilitaire pour la conversion en DTO
    public PackSelectionResponse toDto() {
        PackSelectionResponse dto = new PackSelectionResponse();
        dto.setSelectionId(this.id);
        dto.setPackId(this.pack.getId());
        dto.setPackNom(this.pack.getNom());
        dto.setPackPrix(this.pack.getPrix());
        dto.setPackDuree(this.pack.getDuree());
        dto.setDateSelection(this.dateSelection);
        dto.setDateDebut(this.dateDebut);
        dto.setDateFin(this.dateFin);
        dto.setCommentaire(this.commentaire);
        dto.setNotificationActive(this.notificationActive);
        dto.setStatut(this.statut.name());
        return dto;
    }
}