package com.eduspot.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "POSTS")
public class Post {
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long postId;

    @ManyToOne
    private User author;

    @ManyToOne
    private Course course;

    @NotNull
    @Size(min = 1, max = 150)
    private String message;

    @Builder.Default
    private LocalDateTime createTime = LocalDateTime.now();

    public String convertCreationTime() {
        String date = createTime.getDayOfMonth() + "-" + createTime.getMonthValue() + "-" + createTime.getYear();
        String time = createTime.getHour() + ":" + createTime.getMinute() + ":" + createTime.getSecond();
        return date + " " + time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return course.equals(post.course) &&
                message.equalsIgnoreCase(post.message);
    }

    public static Comparator<Post> PostCreationTimeComparator = new Comparator<Post>() {
        @Override
        public int compare(Post o1, Post o2) {
            LocalDateTime time1 = o1.getCreateTime();
            LocalDateTime time2 = o2.getCreateTime();

            return time2.compareTo(time1);
        }
    };
}
