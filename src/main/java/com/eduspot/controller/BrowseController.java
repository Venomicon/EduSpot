package com.eduspot.controller;

import com.eduspot.domain.Course;
import com.eduspot.domain.SearchForm;
import com.eduspot.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BrowseController {
    CourseService courseService;

    @Autowired
    public BrowseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ModelAttribute("courses")
    public List<Course> getAvailableCourses() {
        return courseService.findAllCourses();
    }

    @RequestMapping("/browse")
    public String getAllCourses(@ModelAttribute SearchForm searchForm) {
        return "browsePage";
    }
}
