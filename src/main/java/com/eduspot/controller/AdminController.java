package com.eduspot.controller;

import com.eduspot.domain.CourseDto;
import com.eduspot.domain.CredentialsDto;
import com.eduspot.domain.UserDto;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.mapper.CredentialsMapper;
import com.eduspot.mapper.UserMapper;
import com.eduspot.service.CourseService;
import com.eduspot.service.CredentialsService;
import com.eduspot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    UserService userService;
    UserMapper userMapper;
    CredentialsMapper credentialsMapper;
    CredentialsService credentialsService;
    CourseMapper courseMapper;
    CourseService courseService;

    @Autowired
    public AdminController(UserService userService, UserMapper userMapper, CredentialsMapper credentialsMapper, CredentialsService credentialsService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.credentialsMapper = credentialsMapper;
        this.credentialsService = credentialsService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/credentials")
    public List<CredentialsDto> getAllCredentials() {
        return credentialsMapper.mapToCredentialsDtoList(credentialsService.findAllCredentials());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/credentials/{userId}")
    public CredentialsDto getUserCredentials(@PathVariable Long userId) throws UserNotFoundException {
        return credentialsMapper.mapToCredentialsDto(userService.findUserById(userId).getCredentials());
    }
}
