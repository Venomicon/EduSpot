package com.eduspot.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "COURSES")

@NamedNativeQueries({
        @NamedNativeQuery(name = "Course.searchByString", query = "SELECT * FROM courses WHERE title LIKE :search OR description LIKE :search OR day_of_week LIKE :search OR level LIKE :search", resultClass = Course.class),
        @NamedNativeQuery(name = "Course.getAllTakenByUser", query = "SELECT * FROM users_taken_courses WHERE users_user_id = :userId", resultClass = Course.class),
        @NamedNativeQuery(name = "Course.getAllCarriedByUser", query = "SELECT * FROM users_carried_courses WHERE users_user_id = :userId", resultClass = Course.class),
        @NamedNativeQuery(name = "Course.getFinishedCourses", query = "SELECT * FROM courses WHERE end_date - CURDATE() <= 0", resultClass = Course.class)
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

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "end_date")
    private LocalDate endDate;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
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

    public LocalDate getEndOfCourse() {
        setEndDate(startDate.plus(weeks, ChronoUnit.WEEKS));
        return this.endDate;
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
