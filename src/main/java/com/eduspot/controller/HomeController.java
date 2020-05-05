package com.eduspot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
        return "layout/success";
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
