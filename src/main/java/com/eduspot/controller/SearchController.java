package com.eduspot.controller;

import com.eduspot.domain.Course;
import com.eduspot.domain.SearchForm;
import com.eduspot.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {
    @Autowired
    SearchService searchService;

    @RequestMapping(method = RequestMethod.GET, value = "/search")
    public ModelAndView resultPage(@ModelAttribute("searchForm") SearchForm searchForm) {
        ModelAndView modelAndView = new ModelAndView("browsePage");
        List<Course> foundCourses = searchService.findAllThatContain(searchForm.getSearch());
        modelAndView.addObject("courses", foundCourses);
        return modelAndView;
    }


}
