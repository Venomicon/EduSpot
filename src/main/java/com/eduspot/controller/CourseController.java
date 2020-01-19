package com.eduspot.controller;

import com.eduspot.domain.Course;
import com.eduspot.domain.CourseDto;
import com.eduspot.exception.CourseNotFoundException;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.repository.CourseRepository;
import com.eduspot.repository.CredentialsRepository;
import com.eduspot.service.CourseService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    public static final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserService userService;

    @ModelAttribute("loggedUser")
    public com.eduspot.domain.User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/course/{courseId}")
    public void addCourseToTaken(@PathVariable Long courseId) {
        try {
            Course course = courseService.findCourseById(courseId);
            User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            com.eduspot.domain.User user = userService.findUserByUsername(username);
            course.getStudents().add(user);
            user.getTakenCourses().add(course);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @RequestMapping(value = "/course/new")
    public String createCourseInitial(Course course) {
        return "createCoursePage";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/course/new")
    public String createCourse(@ModelAttribute("course") @Valid Course course, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createCoursePage";
        }
        try {
            course.setTeacher(getUser());
            courseService.saveCourse(course);
            return "redirect:/success";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "createCoursePage";
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/course/{courseId}")
    public String getCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        ModelAndView modelAndView = new ModelAndView("courseDetails");
        Course course = courseService.findCourseById(courseId);
        modelAndView.addObject("course", course);
        return "courseDetails";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/course/{courseId}")
    public String deleteCourse(@PathVariable Long courseId) throws CourseNotFoundException {
        courseService.deleteCourseById(courseId);
        return "redirect:/layout/success";
    }
}
