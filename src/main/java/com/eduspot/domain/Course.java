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
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "COURSES")

@NamedNativeQueries({
        @NamedNativeQuery(name = "Course.searchByString", query = "SELECT * FROM courses WHERE title LIKE :search OR description LIKE :search", resultClass = Course.class)
})
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

    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return title.equals(course.title) &&
                teacher.equals(course.teacher) &&
                level.equals(course.level);
    }

    public String getDayOfClasses() {
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();
        setDayOfWeek(dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        return this.dayOfWeek;
    }

    public String timeAndDayToString() {
        String dayOfWeek = getDayOfClasses();
        String time = startTime.getHour() + ":" + startTime.getMinute();
        return dayOfWeek + ", " + time;
    }

    public List<Post> sortPostsByCreationTime() {
        List<Post> posts = this.getPosts();
        posts.sort(Post.PostCreationTimeComparator);
        return posts;
    }
}
