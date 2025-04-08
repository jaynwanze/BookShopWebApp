package com.example.bookshop.controller.pages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookshop.entity.Book;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;

@Controller
@RequestMapping("/administrator")
public class AdministratorPageController {

    @Autowired
    private BookService bookService;

    @GetMapping("/dashboard")
    public String administratorDashboardPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        return "administrator/dashboard";
    }

    @GetMapping("/manage-books")
    public String manageBooksPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        return "administrator/books";
    }

    @GetMapping("/manage-customers")
    public String manageCustomersPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        return "administrator/customers";
    }

    @GetMapping("/add-book")
    public String addBookPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        return "administrator/book-form-add";
    }

    @GetMapping("/edit-book{id}")
    public String editBookPage(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/administrator/books";
        }
        return "administrator/book-form-edit";
    }
}
