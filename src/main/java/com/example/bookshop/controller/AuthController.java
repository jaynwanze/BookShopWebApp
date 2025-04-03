package com.example.bookshop.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.service.AuthService;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register/customer")
    public String registerCustomer(@RequestParam String email,
                                   @RequestParam String name,
                                   @RequestParam String password,
                                   RedirectAttributes redirectAttributes) {
        return authService.registerCustomer(email, name, password, redirectAttributes);
    }

    @PostMapping("/login/customer")
    public String loginCustomer(@RequestParam String email,
                                @RequestParam String password,
                                RedirectAttributes redirectAttributes) {
        return authService.loginCustomer(email, password, redirectAttributes);
    }

    @PostMapping("/register/administrator")
    public String registerAdministrator(@RequestParam String email,
                                        @RequestParam String name,
                                        @RequestParam String password,
                                        @RequestParam String jobTitle,
                                        @RequestParam String department,
                                        RedirectAttributes redirectAttributes) {
        return authService.registerAdministrator(email, name, password, jobTitle, department, redirectAttributes);
    }

    @PostMapping("/login/administrator")
    public String loginAdministrator(@RequestParam String email,
                                     @RequestParam String password,
                                     RedirectAttributes redirectAttributes) {
        return authService.loginAdministrator(email, password, redirectAttributes);
    }

    @GetMapping("/logout")
    public String logout() {
        authService.logout();
        return "redirect:/login";
    }
}