package com.example.bookshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @GetMapping("/login")
    public String indexPage() {
        return "login";
    }

    @GetMapping("/login/customer")
    public String loginAdministartorPage() {
        return "/customer/login";
    }

    @GetMapping("/register/customer")
    public String registerCustomerPage() {
        return "/customer/register";
    }

    @GetMapping("/login/administrator")
    public String loginAdministratorPage() {
        return "/administrator/login";
    }

    @GetMapping("/register/administrator")
    public String registerAdministratorPage() {
        return "/administrator/register";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/customer/dashboard")
    public String customerDashboardPage() {
        return "customer/dashboard";
    }

    @GetMapping("/administrator/dashboard")
    public String administratorDashboardPage() {
        return "administrator/dashboard";
    }

}
