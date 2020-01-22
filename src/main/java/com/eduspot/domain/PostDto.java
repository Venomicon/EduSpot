package com.eduspot.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private Long authorId;
    private Long courseId;
    private String message;
    private LocalDateTime createTime;
}
