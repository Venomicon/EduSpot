package com.eduspot.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "COURSES")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long courseId;

    @NotEmpty
    @Size(min = 3, max = 30)
    private String title;

    @Size(max = 200)
    private String description;

    @ManyToOne
    @JoinColumn
    private User teacher;

    @NotEmpty
    private String level;

    //!!!!!!!!!!!!!!!!!!!!!!  CONFIGURE MANY TO MANY
    @ManyToMany
    @JoinTable
    private List<User> students;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @NotEmpty
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate startDate;

    @NotEmpty
    private Integer weeks;

    @NotEmpty
    private DayOfWeek dayOfWeek;
}
