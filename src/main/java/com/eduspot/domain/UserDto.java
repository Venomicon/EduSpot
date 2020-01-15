package com.eduspot.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Long credentialsId;

    @Builder.Default
    private List<Long> carriedCourseIds = new ArrayList<>();

    @Builder.Default
    private List<Long> takenCourseIds = new ArrayList<>();


}
