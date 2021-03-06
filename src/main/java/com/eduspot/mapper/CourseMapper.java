package com.eduspot.mapper;

import com.eduspot.domain.*;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.service.PostService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    private static Logger LOGGER = LoggerFactory.getLogger(CourseMapper.class);

    UserService userService;
    PostService postService;

    @Autowired
    public CourseMapper(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    public Course mapToCourse(CourseDto courseDto) {
        Course course = Course.builder()
                .courseId(courseDto.getCourseId())
                .title(courseDto.getTitle())
                .description(courseDto.getDescription())
                .level(courseDto.getLevel())
                .startTime(courseDto.getStartTime())
                .startDate(courseDto.getStartDate())
                .dayOfWeek(courseDto.getDayOfWeek())
                .weeks(courseDto.getWeeks())
                .build();
        try {
            course.setTeacher(userService.findUserById(courseDto.getTeacherId()));
            List<User> students = new ArrayList<>();
            for (Long id : courseDto.getStudentIds()) {
                User student = userService.findUserById(id);
                students.add(student);
            }
            course.setStudents(students);
            List<Post> posts = new ArrayList<>();
            for (Long id : courseDto.getPostIds()) {
                Post post = postService.findPostById(id);
                posts.add(post);
            }
            course.setPosts(posts);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return course;
    }

    public CourseDto mapToCourseDto(Course course) {
        CourseDto courseDto = CourseDto.builder()
                .courseId(course.getCourseId())
                .title(course.getTitle())
                .description(course.getDescription())
                .teacherId(course.getTeacher().getUserId())
                .level(course.getLevel())
                .startTime(course.getStartTime())
                .startDate(course.getStartDate())
                .dayOfWeek(course.getDayOfWeek())
                .weeks(course.getWeeks())
                .build();
        courseDto.setStudentIds(course.getStudents().stream()
                .map(User::getUserId)
                .collect(Collectors.toList()));
        courseDto.setPostIds(course.getPosts().stream()
                .map(Post::getPostId)
                .collect(Collectors.toList()));
        return courseDto;
    }

    public List<Course> mapToCourseList(List<CourseDto> courseDtos) {
        return courseDtos.stream()
                .map(this::mapToCourse)
                .collect(Collectors.toList());
    }

    public List<CourseDto> mapToCourseDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::mapToCourseDto)
                .collect(Collectors.toList());
    }

}
