package com.example.internships.dao.repository;

import com.example.internships.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    // Méthode personnalisée pour vérifier si l'email existe déjà
    boolean existsByEmail(String email);

    List<User> findByRole(User.Role role);
    Optional<User> findByEmail(String email);
}
