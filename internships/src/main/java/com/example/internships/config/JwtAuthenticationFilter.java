package com.example.internships.config;

import com.example.internships.service.JwtService;
import com.example.internships.service.UserService;
import io.jsonwebtoken.JwtException;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
        return path.startsWith("/api/v1/auth/signin") ||
                path.startsWith("/api/v1/auth/signup") ||
                path.startsWith("/api/v1/auth/refresh") ||
                path.equals("/") ||
                path.equals("/error") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String authHeader = request.getHeader("Authorization");

            if (!StringUtils.hasText(authHeader)) {
                handleMissingAuthHeader(request, response);
                return;
            }

            if (!authHeader.startsWith("Bearer ")) {
                handleInvalidAuthHeader(request, response);
                return;
            }

            final String jwt = authHeader.substring(7).trim();
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt) && jwtService.isTokenValidForUser(jwt, userDetails)) {
                    setSecurityContext(request, userDetails);
                } else {
                    handleInvalidToken(request, response);
                    return;
                }
            }

            filterChain.doFilter(request, response);

        } catch (JwtException e) {
            logger.error("JWT validation error: {}", e.getMessage());
            handleInvalidToken(request, response);
        } catch (UsernameNotFoundException e) {
            logger.error("User not found: {}", e.getMessage());
            handleUserNotFound(request, response, e);
        } catch (Exception e) {
            logger.error("Authentication error: {}", e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication failed");
        }
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
        logger.warn("Missing Authorization header for: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header required");
    }

    private void handleInvalidAuthHeader(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.warn("Invalid Authorization header format for: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header must start with 'Bearer '");
    }

    private void handleInvalidToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.warn("Invalid JWT token for: {}", request.getRequestURI());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
    }

    private void handleUserNotFound(HttpServletRequest request, HttpServletResponse response, UsernameNotFoundException e) throws IOException {
        logger.error("User not found: {}", e.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Account not found");
    }
}