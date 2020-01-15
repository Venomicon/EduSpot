package com.eduspot.domain;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CredentialsDto {
    private Long userId;
    private Long credentialsId;
    private String username;
    private String password;
    private String email;
}
