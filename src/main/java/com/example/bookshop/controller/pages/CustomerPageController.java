package com.example.bookshop.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class CustomerPageController {

    @GetMapping("/dashboard")
    public String customerDashboardPage() {
        return "customer/dashboard";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCartPage() {
        return "customer/cart";
    }

    @GetMapping("/profile")
    public String customerProfilePage() {
        return "customer/profile";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "customer/checkout";
    }

    @GetMapping("/catalog")
    public String catalogPage() {
        return "customer/catalog";
    }
}
