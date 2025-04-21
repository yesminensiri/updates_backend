package com.example.internships.service;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.List;

public interface JwtService {
    String extractUsername(String token);
    String extractRole(String token); // Nouvelle méthode
    List<String> extractRoles(String token); // Nouvelle méthode
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
}