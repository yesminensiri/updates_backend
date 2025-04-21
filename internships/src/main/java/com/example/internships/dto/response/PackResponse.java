package com.example.internships.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackResponse {
    private Long id;
    private String nom;
    private double prix;
    private int duree;
    private String description;
}