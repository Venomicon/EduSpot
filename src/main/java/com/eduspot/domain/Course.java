package com.eduspot.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

    @NotNull
    @Size(min = 3, max = 30)
    private String title;

    @Size(max = 200)
    private String description;

    @ManyToOne
    @JoinColumn
    private User teacher;

    @NotNull
    private String level;

    //!!!!!!!!!!!!!!!!!!!!!!  CONFIGURE MANY TO MANY
    @ManyToMany
    @JoinTable
    @Builder.Default
    private List<User> students = new ArrayList<>();

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime startTime;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Future
    private LocalDate startDate;

    @NotNull
    @Min(value = 1)
    @Max(value = 52)
    private Integer weeks;

    private String dayOfWeek;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return title.equals(course.title) &&
                teacher.equals(course.teacher) &&
                level.equals(course.level);
    }

    public void getDayOfClasses() {
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();
        setDayOfWeek(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
    }
}
