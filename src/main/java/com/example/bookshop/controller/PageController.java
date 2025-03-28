package com.example.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/login/customer")
    public String loginPage() {
        return "login-customer";
    }

    @GetMapping("/login/customer")
    public String registerPage() {
        return "register-customer";
    }

    @GetMapping("/login/administrator")
    public String loginAdministratorPage() {
        return "login-administrator";
    }

    @GetMapping("/register/administrator")
    public String registerAdministratorPage() {
        return "register-administrator";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
