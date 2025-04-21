package com.example.internships.service;

import com.example.internships.dao.entity.User;
import com.example.internships.dto.request.SignUpRequest;
import com.example.internships.dto.request.SigninRequest;
import com.example.internships.dto.response.JwtAuthenticationResponse;
import com.example.internships.dao.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class AuthentificationServiceImpl implements AuthentificationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }

        // Créer un nouvel utilisateur
        User user = new User();
        user.setUsername(request.getFirstName() + " " + request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Convertir le rôle s’il vient d’un autre package
        user.setRole(User.Role.valueOf(request.getRole().name()));

        // Sauvegarder l'utilisateur
        userRepository.save(user);

        // Générer un token JWT pour l'utilisateur
        String token = jwtService.generateToken(user); // à condition que User implémente UserDetails

        // Récupérer l'ID et le rôle de l'utilisateur
        Long userId = user.getId();
        String role = user.getRole().toString();

        // Retourner la réponse avec le token, userId et role
        return new JwtAuthenticationResponse(token, userId, role);
    }

    @Override
    public JwtAuthenticationResponse signIn(SigninRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        authenticationManager.authenticate(authToken);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));

        // Générer un token JWT
        String token = jwtService.generateToken(user);

        // Récupérer l'ID et le rôle de l'utilisateur
        Long userId = user.getId();
        String role = user.getRole().toString();

        // Retourner la réponse avec le token, userId et role
        return new JwtAuthenticationResponse(token, userId, role);
    }

}
