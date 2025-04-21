package com.example.internships.dto.request;

import com.example.internships.model.Role; // Assurez-vous d'utiliser le bon modèle Role
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
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
    private String email;       // Email de l'utilisateur
    private String password;    // Mot de passe
    @NotNull(message = "Le rôle est obligatoire")
    @Enumerated(EnumType.STRING)
    private Role role;          // Rôle de l'utilisateur (ADMIN, STUDENT, etc.)
}
