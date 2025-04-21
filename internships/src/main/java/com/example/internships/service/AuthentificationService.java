package com.example.internships.service;
import com.example.internships.dto.request.SigninRequest;  // Corrige ici l'orthographe

import com.example.internships.dto.request.SignUpRequest;
import com.example.internships.dto.response.JwtAuthenticationResponse;

public interface AuthentificationService {

    // Méthode d'inscription (SignUp)
    JwtAuthenticationResponse signUp(SignUpRequest request);

    // Méthode de connexion (SignIn)
    JwtAuthenticationResponse signIn(SigninRequest request);  // Corrige ici aussi l'orthographe
}
