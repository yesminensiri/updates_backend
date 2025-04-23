package com.example.internships.dto.request;

import lombok.*;

@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
    private String email;
    @Getter
    private String password;

}
