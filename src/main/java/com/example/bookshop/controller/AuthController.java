package com.example.bookshop.controller;

import jakarta.servlet.http.HttpSession;

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
    public String register(@RequestParam String email, @RequestParam String name, @RequestParam String password,
            HttpSession session, RedirectAttributes redirectAttributes) {
        return authService.registerCustomer(email, name, password, session, redirectAttributes);
    }

    @PostMapping("/login/customer")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        return authService.loginCustomer(email, password, session, redirectAttributes);
    }

    @PostMapping("/register/administrator")
    public String registerAdministrator(@RequestParam String email, @RequestParam String name,
            @RequestParam String password, HttpSession session, RedirectAttributes redirectAttributes) {
        return authService.registerAdministrator(email, name, password, session, redirectAttributes);
    }

    @PostMapping("/login/administrator")
    public String loginAdministrator(@RequestParam String email, @RequestParam String password, HttpSession session,
            RedirectAttributes redirectAttributes) {
        return authService.loginAdministrator(email, password, session, redirectAttributes);
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        authService.logout(session);
        return "redirect:/login";
    }
}