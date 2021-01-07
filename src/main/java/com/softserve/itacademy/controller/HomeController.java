package com.softserve.itacademy.controller;

import com.softserve.itacademy.AuthenticationToken;
import com.softserve.itacademy.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final UserService userService;
    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/", "home"})
    public String home(Model model, Authentication authenticationToken) {
        //authenticationToken = (AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("users", userService.getAll());
        return "home";
    }
}
