package com.example.internships.service;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.repository.PackRepository;
import com.example.internships.dto.request.PackRequest;
import com.example.internships.dto.response.PackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PackService {

    private final PackRepository packRepository;

    public List<PackResponse> getAllPacks() {
        return packRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public PackResponse getPackById(Long id) {
        return packRepository.findById(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new RuntimeException("Pack non trouvé"));
    }

    public PackResponse createPack(PackRequest request) {
        Pack pack = convertToEntity(request);
        Pack savedPack = packRepository.save(pack);
        return convertToResponse(savedPack);
    }

    public PackResponse updatePack(Long id, PackRequest request) {
        return packRepository.findById(id)
                .map(pack -> {
                    updateEntity(pack, request);
                    return convertToResponse(packRepository.save(pack));
                })
                .orElseThrow(() -> new RuntimeException("Pack non trouvé"));
    }

    public void deletePack(Long id) {
        packRepository.deleteById(id);
    }

    // Méthodes de conversion
    private Pack convertToEntity(PackRequest request) {
        Pack pack = new Pack();
        pack.setNom(request.getNom());
        pack.setPrix(request.getPrix());
        pack.setDuree(request.getDuree());
        pack.setDescription(request.getDescription());
        return pack;
    }

    private void updateEntity(Pack pack, PackRequest request) {
        pack.setNom(request.getNom());
        pack.setPrix(request.getPrix());
        pack.setDuree(request.getDuree());
        pack.setDescription(request.getDescription());
    }

    private PackResponse convertToResponse(Pack pack) {
        return PackResponse.builder()
                .id(pack.getId())
                .nom(pack.getNom())
                .prix(pack.getPrix())
                .duree(pack.getDuree())
                .description(pack.getDescription())
                .build();
    }
}