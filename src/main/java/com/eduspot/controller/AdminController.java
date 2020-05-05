package com.eduspot.controller;

import com.eduspot.domain.*;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.mapper.CredentialsMapper;
import com.eduspot.mapper.PostMapper;
import com.eduspot.mapper.UserMapper;
import com.eduspot.service.CourseService;
import com.eduspot.service.CredentialsService;
import com.eduspot.service.PostService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    public static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    UserService userService;
    UserMapper userMapper;
    CredentialsMapper credentialsMapper;
    CredentialsService credentialsService;
    CourseMapper courseMapper;
    CourseService courseService;
    PostMapper postMapper;
    PostService postService;

    @Autowired
    public AdminController(UserService userService, UserMapper userMapper, CredentialsMapper credentialsMapper,
                           CredentialsService credentialsService, CourseMapper courseMapper, CourseService courseService,
                           PostMapper postMapper, PostService postService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.credentialsMapper = credentialsMapper;
        this.credentialsService = credentialsService;
        this.courseMapper = courseMapper;
        this.courseService = courseService;
        this.postMapper = postMapper;
        this.postService = postService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    public List<UserDto> getAllUsers() {
        return userMapper.mapToUserDtoList(userService.findAllUsers());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/users/{userId}")
    public UserDto getUser(@PathVariable Long userId) throws UserNotFoundException {
        return userMapper.mapToUserDto(userService.findUserById(userId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/courses")
    public List<CourseDto> getAllCourses() {
        return courseMapper.mapToCourseDtoList(courseService.findAllCourses());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/courses/{courseId}")
    public CourseDto getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        return courseMapper.mapToCourseDto(courseService.findCourseById(courseId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/credentials")
    public List<CredentialsDto> getAllCredentials() {
        return credentialsMapper.mapToCredentialsDtoList(credentialsService.findAllCredentials());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/credentials/{userId}")
    public CredentialsDto getUserCredentials(@PathVariable Long userId) throws UserNotFoundException {
        return credentialsMapper.mapToCredentialsDto(userService.findUserById(userId).getCredentials());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/posts")
    public List<PostDto> getAllPosts() {
        return postMapper.mapToPostDtoList(postService.findAllPosts());
    }
}
