package com.eduspot.controller;

import com.eduspot.domain.Course;
import com.eduspot.domain.CourseDto;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.repository.CourseRepository;
import com.eduspot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BrowseController {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseService courseService;
    @Autowired
    CourseRepository courseRepository;

    @ModelAttribute("availableCourses")
    public List<Course> getAvailableCourses() {
        return courseService.findAllCourses();
    }

    @RequestMapping("/browse")
    public String getAllCourses() {
        return "browsePage";
    }
}
