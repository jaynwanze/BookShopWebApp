package com.example.bookshop.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorPageController {

    @GetMapping("/dashboard")
    public String administratorDashboardPage() {
        return "administrator/dashboard";
    }

    @GetMapping("/manage-books")
    public String manageBooksPage() {
        return "administrator/books";
    }

    @GetMapping("/manage-customers")
    public String manageCustomersPage() {
        return "administrator/customers";
    }

    @GetMapping("/restock")
    public String restockPage() {
        return "administrator/restock";
    }
}
