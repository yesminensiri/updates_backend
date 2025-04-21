package com.example.internships.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class JwtAuthenticationResponse {

    private String token;   // Le token JWT
    private Long userId;    // L'ID de l'utilisateur
    private String role;    // Le rôle de l'utilisateur

    // Constructeur avec les trois arguments : token, userId, role

}
