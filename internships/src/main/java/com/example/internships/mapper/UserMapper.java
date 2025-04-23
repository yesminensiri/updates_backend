package com.example.internships.mapper;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.entity.User;
import com.example.internships.dto.response.DtoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

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

    public static User fromDTO(UserDTO dto, Set<Pack> packs) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRole(User.Role.valueOf(dto.getRole()));
        user.setPacks(packs != null ? packs : new HashSet<>());
        return user;
    }
    // Convertir User → DtoUser (pour la réponse API)
    public static DtoUser toDtoUser(User user) {
        return new DtoUser(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole() // Assuming Role is the same in both DTOs
        );
    }
    public static User fromDtoUser(DtoUser dtoUser, Set<Pack> packs) {
        User user = new User();
        user.setId(dtoUser.getId());
        user.setUsername(dtoUser.getFullName());  // Assuming fullName is mapped to username
        user.setEmail(dtoUser.getEmail());
        user.setRole(User.Role.valueOf(dtoUser.getRole().name()));  // Assuming Role is the same in both DTOs
        user.setPacks(packs != null ? packs : new HashSet<>());
        return user;
    }

}
