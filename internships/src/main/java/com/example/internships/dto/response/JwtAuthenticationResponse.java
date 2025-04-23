package com.example.internships.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder

public class JwtAuthenticationResponse {

    private String token;
    private Long userId;
    private String role;

}
