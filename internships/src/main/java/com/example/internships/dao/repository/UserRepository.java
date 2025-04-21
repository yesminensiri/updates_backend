package com.example.internships.dao.repository;

import com.example.internships.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode personnalisée pour vérifier si l'email existe déjà
    boolean existsByEmail(String email);

    // Méthode personnalisée pour trouver un utilisateur par son email
    Optional<User> findByUsername(String username);

    // Méthode pour trouver des utilisateurs par leur rôle
    List<User> findByRole(User.Role role);
    Optional<User> findByEmail(String email);
}
