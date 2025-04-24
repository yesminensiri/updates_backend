package com.example.internships.service;

import com.example.internships.dao.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public interface JwtService {

    String extractUsername(String token);
    String extractRole(String token);
    List<String> extractRoles(String token);
    boolean isTokenValid(String token);
    boolean isTokenValidForUser(String token, UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    User verifyToken(String token);
}