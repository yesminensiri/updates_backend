package com.example.internships.dto.response;

import com.example.internships.dao.entity.User;
import com.example.internships.model.Role; // Assurez-vous que le chemin est correct
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser {

    private Long id;             // ID de l'utilisateur
    private String fullName;     // Nom complet de l'utilisateur
    private String email;        // Email de l'utilisateur
    private User.Role role;;           // Rôle de l'utilisateur
}
