package com.example.internships.dao.repository;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.entity.PackSelection;
import com.example.internships.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PackSelectionRepository extends JpaRepository<PackSelection, Long> {
    List<PackSelection> findByUserId(Long userId);

    boolean existsByUserAndPack(User user, Pack pack);

    @Query("SELECT ps FROM PackSelection ps WHERE ps.user = :user AND ps.statut = 'ACTIF'")
    List<PackSelection> findActiveSelectionsByUser(@Param("user") User user);
}