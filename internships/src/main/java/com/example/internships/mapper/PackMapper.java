package com.example.internships.mapper;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dto.request.PackRequest;
import com.example.internships.dto.response.PackResponse;

public class PackMapper {

    public static Pack toEntity(PackRequest request) {
        return Pack.builder()
                .nom(request.getNom())
                .prix(request.getPrix())
                .duree(request.getDuree())
                .description(request.getDescription())
                .build();
    }

    public static PackResponse toResponse(Pack pack) {
        return PackResponse.builder()
                .id(pack.getId())
                .nom(pack.getNom())
                .prix(pack.getPrix())
                .duree(pack.getDuree())
                .description(pack.getDescription())
                .build();
    }

    public static void updateEntity(Pack pack, PackRequest request) {
        pack.setNom(request.getNom());
        pack.setPrix(request.getPrix());
        pack.setDuree(request.getDuree());
        pack.setDescription(request.getDescription());
    }
}