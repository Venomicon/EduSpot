package com.eduspot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "homePage";
    }

    @RequestMapping("/another")
    public String another() {
        return "anotherPage";
    }

    @RequestMapping("/login")
    public String login() {
        return "loginPage";
    }

    @RequestMapping("/profile")
    public String profilePage() {
        return "profilePage";
    }
}
