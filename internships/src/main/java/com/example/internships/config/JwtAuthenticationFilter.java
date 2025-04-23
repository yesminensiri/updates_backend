package com.example.internships.config;

import com.example.internships.service.JwtService;
import com.example.internships.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.core.userdetails.UsernameNotFoundException; // Import ajouté
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/v1/auth")
                || path.equals("/")
                || path.equals("/error")
                || path.startsWith("/swagger")
                || path.startsWith("/v3/api-docs");
    }


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        logger.debug("Processing JWT for path: {}", request.getRequestURI());

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        if (!StringUtils.hasText(authHeader)) {
            handleMissingAuthHeader(request, response);
            return;
        }

        if (!authHeader.startsWith("Bearer ")) {
            handleInvalidAuthHeader(request, response);
            return;
        }

        if (authHeader.length() > 7) {
            jwt = authHeader.substring(7);
        } else {
            handleInvalidAuthHeader(request, response);
            return;
        }
        userEmail = jwtService.extractUsername(jwt);
        logger.debug("JWT Token: {}", jwt);
        logger.debug("UserEmail from token: {}", userEmail);

        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            processAuthentication(request, response, filterChain, jwt, userEmail);
        } else {
            filterChain.doFilter(request, response);
        }
    }
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Get the token without "Bearer "
        }
        return null;
    }

    private void processAuthentication(HttpServletRequest request,
                                       HttpServletResponse response,
                                       FilterChain filterChain,
                                       String jwt,
                                       String userEmail) throws IOException, ServletException {
        try {
            UserDetails userDetails = userService.loadUserByUsername(userEmail);
            logger.debug("User authorities: {}", userDetails.getAuthorities());

            if (jwtService.isTokenValid(jwt, userDetails)) {
                setSecurityContext(request, userDetails);
            } else {
                handleInvalidToken(request, response);
                return;
            }
        } catch (UsernameNotFoundException e) {
            handleUserNotFound(request, response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void setSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }


    private void handleMissingAuthHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.warn("Missing Authorization header for path: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
    }

    private void handleInvalidAuthHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.warn("Invalid Auth header format for path: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header format");
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.warn("Invalid JWT token for path: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
    }

    private void handleUserNotFound(HttpServletRequest request, HttpServletResponse response, UsernameNotFoundException e) throws IOException {
        logger.error("User not found error: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials");
    }
}