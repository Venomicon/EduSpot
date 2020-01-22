package com.eduspot.mapper;

import com.eduspot.domain.Course;
import com.eduspot.domain.Post;
import com.eduspot.domain.User;
import com.eduspot.domain.UserDto;
import com.eduspot.service.CourseService;
import com.eduspot.service.CredentialsService;
import com.eduspot.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    private static Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);

    CredentialsService credentialsService;
    CourseService courseService;

    @Autowired
    PostService postService;

    @Autowired
    public UserMapper(CredentialsService credentialsService, CourseService courseService) {
        this.credentialsService = credentialsService;
        this.courseService = courseService;
    }

    public User mapToUser(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .birthDate(userDto.getBirthDate())
                .build();
        try {
            user.setCredentials(credentialsService.findById(userDto.getCredentialsId()));
            List<Course> carriedCourses = new ArrayList<>();
            for (Long id : userDto.getCarriedCourseIds()) {
                Course carried = courseService.findCourseById(id);
                carriedCourses.add(carried);
            }
            user.setCarriedCourses(carriedCourses);
            List<Course> takenCourses = new ArrayList<>();
            for (Long id : userDto.getTakenCourseIds()) {
                Course taken = courseService.findCourseById(id);
                takenCourses.add(taken);
            }
            user.setTakenCourses(takenCourses);
            List<Post> posts = new ArrayList<>();
            for (Long id : userDto.getPostIds()) {
                Post post = postService.findPostById(id);
                posts.add(post);
            }
            user.setPosts(posts);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return user;
    }

    public UserDto mapToUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .credentialsId(user.getCredentials().getCredentialsId())
                .build();
        userDto.setCarriedCourseIds(user.getCarriedCourses().stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList()));
        userDto.setTakenCourseIds(user.getTakenCourses().stream()
                .map(Course::getCourseId)
                .collect(Collectors.toList()));
        userDto.setPostIds(user.getPosts().stream()
                .map(Post::getPostId)
                .collect(Collectors.toList()));
        return userDto;
    }

    public List<User> mapToUserList(List<UserDto> userDtos) {
        return userDtos.stream()
                .map(this::mapToUser)
                .collect(Collectors.toList());
    }

    public List<UserDto> mapToUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
