package com.eduspot.controller;

import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.DayOfWeek;
import java.util.Collection;
import java.util.List;

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

    @RequestMapping("/admin")
    public String adminPanel() {
        return "admin/adminPanel";
    }
}
