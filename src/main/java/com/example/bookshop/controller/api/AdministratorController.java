package com.example.bookshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.bookshop.entity.Book;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books/new")
    public String showNewBookForm(@AuthenticationPrincipal CustomUserDetails userDetails, String title,
            String author, String category, String description, String imageUrl, double price, Model model) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        // bookService.createBook(title, author, category, description, imageUrl,
        // price);
        model.addAttribute("success", "Book created successfully!");
        return "redirect:/books";
    }

    @PostMapping("/books")
    public String saveBook(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute("book") Book book, Model model) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }

        bookService.saveBook(book);
        model.addAttribute("success", "Book saved successfully!");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        bookService.deleteBook(id);
        model.addAttribute("success", "Book deleted successfully!");
        return "redirect:/books";
    }

    // @GetMapping("/customers"){
    // // Logic to retrieve and display customers
    // return "administrator/customers";
    // }
}
