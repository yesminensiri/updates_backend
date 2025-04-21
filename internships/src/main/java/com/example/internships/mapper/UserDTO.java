package com.example.internships.mapper;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.entity.User;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String role;
    private Set<Long> packIds;

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Set<Long> getPackIds() { return packIds; }
    public void setPackIds(Set<Long> packIds) { this.packIds = packIds; }

    // Méthodes de conversion

    // Convertir User → UserDTO
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().name());
        dto.setPackIds(user.getPacks()
                .stream()
                .map(Pack::getId)
                .collect(Collectors.toSet()));
        return dto;
    }

    // Convertir UserDTO → User (avec les Packs récupérés)
    public static User fromDTO(UserDTO dto, Set<Pack> packs) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(User.Role.valueOf(dto.getRole()));
        user.setPacks(packs != null ? packs : new HashSet<>());
        return user;
    }
}
