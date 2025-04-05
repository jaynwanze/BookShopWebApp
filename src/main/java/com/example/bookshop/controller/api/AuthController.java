package com.example.bookshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/customer")
    public String registerCustomer(@RequestParam String username,
            @RequestParam String name,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        return authService.registerCustomer(username, name, password, redirectAttributes);
    }

    @PostMapping("/login/customer")
    public String loginCustomer(@RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        return authService.loginCustomer(username, password, redirectAttributes);
    }

    @PostMapping("/register/administrator")
    public String registerAdministrator(@RequestParam String username,
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String jobTitle,
            @RequestParam String department,
            RedirectAttributes redirectAttributes) {
        return authService.registerAdministrator(username, name, password, jobTitle, department, redirectAttributes);
    }

    @PostMapping("/login/administrator")
    public String loginAdministrator(@RequestParam String username,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {
        return authService.loginAdministrator(username, password, redirectAttributes);
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/login";
    }
}