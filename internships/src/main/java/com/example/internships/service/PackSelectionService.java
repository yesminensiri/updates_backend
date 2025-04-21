package com.example.internships.service;

import com.example.internships.dao.entity.Pack;
import com.example.internships.dao.entity.PackSelection;
import com.example.internships.dao.entity.User;
import com.example.internships.dao.repository.PackRepository;
import com.example.internships.dao.repository.PackSelectionRepository;
import com.example.internships.dao.repository.UserRepository;
import com.example.internships.dto.response.PackSelectionResponse;
import com.example.internships.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.internships.dto.request.PackSelectionRequest;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PackSelectionService {
    private final PackSelectionRepository packSelectionRepository;
    private final PackRepository packRepository;
    private final UserRepository userRepository;

    public PackSelectionResponse selectPack(Long userId, PackSelectionRequest request) {
        Pack pack = packRepository.findById(request.getPackId())
                .orElseThrow(() -> new ResourceNotFoundException("Pack non trouvé"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

        PackSelection selection = new PackSelection();
        selection.setUser(user);
        selection.setPack(pack);
        selection.setDateDebut(request.getDateDebut());
        selection.setDateFin(request.getDateDebut().plusMonths(pack.getDuree()));
        selection.setCommentaire(request.getCommentaire());

        PackSelection saved = packSelectionRepository.save(selection);
        return saved.toDto();
    }

    public List<PackSelectionResponse> getUserSelections(Long userId) {
        return packSelectionRepository.findByUserId(userId).stream()
                .map(PackSelection::toDto)
                .toList();
    }
}
