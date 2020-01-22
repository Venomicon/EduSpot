package com.eduspot.mapper;

import com.eduspot.domain.Post;
import com.eduspot.domain.PostDto;
import com.eduspot.service.CourseService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostMapper.class);

    UserService userService;
    CourseService courseService;

    @Autowired
    public PostMapper(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    public Post mapToPost(PostDto postDto) {
        Post post = Post.builder()
                .message(postDto.getMessage())
                .postId(postDto.getPostId())
                .build();
        try {
            post.setAuthor(userService.findUserById(postDto.getAuthorId()));
            post.setCourse(courseService.findCourseById(postDto.getCourseId()));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return post;
    }

    public PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .authorId(post.getAuthor().getUserId())
                .courseId(post.getCourse().getCourseId())
                .message(post.getMessage())
                .postId(post.getPostId())
                .createTime(post.getCreateTime())
                .build();
    }

    public List<Post> mapToPostList(List<PostDto> postDtos) {
        return postDtos.stream()
                .map(this::mapToPost)
                .collect(Collectors.toList());
    }

    public List<PostDto> mapToPostDtoList(List<Post> posts) {
        return posts.stream()
                .map(this::mapToPostDto)
                .collect(Collectors.toList());
    }
}
