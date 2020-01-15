package com.eduspot.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long userId;

    @Valid
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "credentials_id", referencedColumnName = "credentials_id")
    private Credentials credentials;

    @Size(min = 2, max = 30)
    @NotEmpty
    private String firstName;

    @Size(min = 2, max = 30)
    @NotEmpty
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Past
    private LocalDate birthDate;

    @OneToMany
    @Builder.Default
    private List<Course> carriedCourses = new ArrayList<>();

    //!!!!!!!!!!!!!!!!!!!!!!  CONFIGURE MANY TO MANY
    @ManyToMany
    @Builder.Default
    private List<Course> takenCourses = new ArrayList<>();

}
