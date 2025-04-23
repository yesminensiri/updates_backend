package com.example.internships.dto.request;

import com.example.internships.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String firstName;   // Prénom de l'utilisateur
    private String lastName;    // Nom de l'utilisateur

    @Email(message = "L'email doit être valide")
    private String email;       // Email de l'utilisateur

    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;    // Mot de passe

    private String confirmPassword; // Confirmer le mot de passe (ne sera peut-être pas stocké, juste pour validation)

    @NotNull(message = "Le rôle est obligatoire")
    @Enumerated(EnumType.STRING)
    private Role role;          // Rôle de l'utilisateur (ADMIN, STUDENT, etc.)

    // Champs spécifiques à l'étudiant
    private String birthDate;   // Date de naissance
    private String sex;         // Sexe
    private String university;  // Université
    private String domain;      // Domaine
    private String diplomas;    // Diplômes
    private String address;     // Adresse
    private String phoneNumber; // Numéro de téléphone
    private String secondaryEmail; // Email secondaire
    private String cv;          // Fichier CV (URL ou chemin)
    private String profilePhoto; // Photo de profil (URL ou chemin)

    // Champs spécifiques à l'entreprise
    private String companyName;      // Nom de l’entreprise
    private String companyLocation;  // Localisation
    private String companyDomain;    // Secteur
    private String companyPhone;     // Téléphone
    private String companyEmail;     // Email de l’entreprise
    private String companyAddress;   // Adresse de l’entreprise

    // Vous pouvez ajouter des champs supplémentaires comme `selectedPack` si nécessaire
    private String selectedPack; // Si vous voulez gérer la sélection du pack
}
