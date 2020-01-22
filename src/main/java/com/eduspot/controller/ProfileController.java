package com.eduspot.controller;

import com.eduspot.domain.*;
import com.eduspot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ProfileController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    UserService userService;

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("loggedUser")
    public User getUser() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findUserByUsername(username);
    }

    @ModelAttribute("yourCarriedCourses")
    public List<Course> getCarriedCourses() {
        User user = getUser();
        return user.getCarriedCourses();
    }

    @ModelAttribute("yourTakenCourses")
    public List<Course> getTakenCourses() {
        User user = getUser();
        return user.getTakenCourses();
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
