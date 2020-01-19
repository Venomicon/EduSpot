package com.eduspot.controller;

import com.eduspot.domain.*;
import com.eduspot.mapper.CourseMapper;
import com.eduspot.repository.CredentialsRepository;
import com.eduspot.repository.UserRepository;
import com.eduspot.service.CourseService;
import com.eduspot.service.CredentialsService;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@NamedNativeQuery(
        name = "ProfileController.getUserByUsername",
        query = "SELECT * FROM CREDENTIALS WHERE USERNAME = :username;",
        resultClass = Credentials.class
)
@Controller
public class ProfileController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    UserService userService;
    @Autowired
    CredentialsRepository credentialsRepository;
    @Autowired
    CourseService courseService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loggedUser")
    public User getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        try {
            return userService.findUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    @ModelAttribute("yourCarriedCourses")
    public List<Course> getCarriedCourses() {
        User user = getUser();
        List<Course> carriedCourses = user.getCarriedCourses();
        if (carriedCourses.isEmpty()) {
            return new ArrayList<>();
        }
        return carriedCourses;
    }

    @ModelAttribute("yourTakenCourses")
    public List<Course> getTakenCourses() {
        User user = getUser();
        List<Course> takenCourses = user.getTakenCourses();
        if (takenCourses.isEmpty()) {
            return new ArrayList<>();
        }
        return takenCourses;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/profile")
    public String showProfile() {
        return "profile/profilePage";
    }































    /*@RequestMapping("/profile/edit")
    public String initialEdit(@ModelAttribute("user") User user, HttpSession session) {
        Enumeration<String> atrtibutes = session.getAttributeNames();
        while (atrtibutes.hasMoreElements()) {
            LOGGER.info(atrtibutes.nextElement());
        }
        return "profile/editInfo";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit")
    public String editInformation(@ModelAttribute("user") User user, HttpSession session) {
        try {
            userService.updateUser(user);
            return "redirect:/profile";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "profile/editInfo";
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/profile/edit/password")
    public String changePassword(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "profile/editInfo";
        }
    } */
}
