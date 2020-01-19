package com.eduspot.domain;

import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    private Long courseId;
    private String title;
    private String description;
    private Long teacherId;
    private String level;

    @Builder.Default
    private List<Long> studentIds = new ArrayList<>();

    private LocalTime startTime;
    private LocalDate startDate;
    private Integer weeks;
    private String dayOfWeek;
}

