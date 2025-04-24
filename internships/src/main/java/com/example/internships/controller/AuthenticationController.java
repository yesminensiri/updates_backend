package com.example.internships.controller;

import com.example.internships.dao.entity.User;
import com.example.internships.dao.repository.UserRepository;
import com.example.internships.dto.request.SignUpRequest;
import com.example.internships.dto.request.SigninRequest;
import com.example.internships.dto.response.JwtAuthenticationResponse;
import com.example.internships.service.AuthentificationService;
import com.example.internships.service.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthentificationService authenticationService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SigninRequest request, HttpServletResponse response) {
        System.out.println("Received login request for: " + request.getEmail());
        System.out.println("Password length: " + request.getPassword().length());
        try {
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:4200");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            Authentication authentication = authenticationManager.authenticate
                    (
                            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtService.generateToken(userDetails);

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(()-> new UsernameNotFoundException("User not found"));
            JwtAuthenticationResponse authResponse = JwtAuthenticationResponse.builder()
                    .token(token)
                    .userId(user.getId())
                    .role(user.getRole().name())
                    .build();
            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(authResponse);

        } catch (AuthenticationException e) {
            logger.error("Authentication failed for email: {}", request.getEmail(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        logger.info("Received request for current user");
        logger.info("Authorization Header: {}", authHeader);

        try {
            // Extract token from header (bearer token)
            String token = authHeader.replace("Bearer ", "").trim();

            logger.info("Extracted Token: {}", token);

            // Now, validate the token, check if it's correct and extract user info
            User user = jwtService.verifyToken(token);  // Use jwtService instead of tokenService
            logger.info("User retrieved: {}", user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("Error while processing the request", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
