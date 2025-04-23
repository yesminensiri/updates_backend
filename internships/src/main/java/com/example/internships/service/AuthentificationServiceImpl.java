package com.example.internships.service;

import com.example.internships.dao.entity.User;
import com.example.internships.dto.request.SignUpRequest;
import com.example.internships.dto.request.SigninRequest;
import com.example.internships.dto.response.JwtAuthenticationResponse;
import com.example.internships.dao.repository.UserRepository;
import com.example.internships.mapper.UserDTO;
import com.example.internships.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserMapper userMapper;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        User user = new User();
        user.setUsername(request.getFirstName() + " " + request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.valueOf(request.getRole().name()));

        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new JwtAuthenticationResponse(token, user.getId(), user.getRole().toString());
    }

    @Override
    public JwtAuthenticationResponse signIn(SigninRequest request) {
        // Authentifier l'utilisateur
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Récupérer l'utilisateur
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Générer le token
        String token = jwtService.generateToken(user);

        // Retourner la réponse
        return JwtAuthenticationResponse.builder()
                .token(token)
                .role(user.getRole().name())
                .userId(user.getId())
                .build();
    }

    @Override
    public UserDTO getCurrentUserDetails(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }
}
