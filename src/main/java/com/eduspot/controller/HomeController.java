package com.eduspot.controller;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "homePage";
    }

    @RequestMapping("/login")
    public String login() {
        return "loginPage";
    }

    @RequestMapping("/success")
    public String successPage() {
        return "layout/successe";
    }

    @RequestMapping("/created")
    public String successCreatedPage() {
        return "layout/successComplete";
    }

    @RequestMapping("/admin")
    public String adminPanel() {
        return "admin/adminPanel";
    }
}
