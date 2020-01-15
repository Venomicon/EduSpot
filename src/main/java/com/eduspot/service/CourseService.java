package com.eduspot.service;

import com.eduspot.domain.Course;
import com.eduspot.domain.User;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    CourseRepository courseRepository;
    UserService userService;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserService userService) {
        this.courseRepository = courseRepository;
        this.userService = userService;
    }

    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }

    public List<Course> findAllCoursesTakenByUser(Long userId) throws UserNotFoundException {
        return userService.findUserById(userId).getTakenCourses();
    }

    public List<Course> findAllCoursesCarriedByUser(Long userId) throws UserNotFoundException {
        return userService.findUserById(userId).getCarriedCourses();
    }

    public Course findCourseById(Long courseId) throws CourseNotFoundException {
        if (courseRepository.findById(courseId).isPresent()) {
            return courseRepository.findById(courseId).get();
        } else {
            throw new CourseNotFoundException("Course not found");
        }
    }
}
