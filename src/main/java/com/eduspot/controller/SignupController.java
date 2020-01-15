package com.eduspot.controller;

import com.eduspot.domain.User;
import com.eduspot.exception.UserAlreadyExistException;
import com.eduspot.exception.UserNotFoundException;
import com.eduspot.mapper.UserMapper;
import com.eduspot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class SignupController {
    private UserService userService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/signup")
    public String signUp(User user) {
        return "signupPage";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        if (bindingResult.hasErrors()) {
            return "signupPage";
        }
        userService.doesExistInDatabase(user);
        userService.signupUser(user);
        try {
            request.login(user.getCredentials().getUsername(), user.getCredentials().getPassword());
        } catch (ServletException e) {
            throw new ServletException(e.getMessage());
        }
        return "redirect:/";
    }
}
