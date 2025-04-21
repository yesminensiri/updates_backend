package com.example.internships.mapper;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.entity.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserMapper {

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

    // Convertir UserDTO → User (avec les Packs déjà récupérés du repo)
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
