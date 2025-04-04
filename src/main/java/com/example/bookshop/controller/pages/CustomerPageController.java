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


}
