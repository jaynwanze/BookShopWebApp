package com.example.bookshop.controller.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Book;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;

@Controller
@RequestMapping("/administrators")
public class AdministratorController {

    @Autowired
    private BookService bookService;

    @PostMapping("/books")
    public String createBook(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam(defaultValue = "0") int stockLevel,
            @RequestParam(defaultValue = "0.0") double price,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String isbn,
            @RequestParam("imageFile") MultipartFile imageFile, // ①
            Model model,
            RedirectAttributes redirect) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }

        byte[] image = null;
        try {
            if (!imageFile.isEmpty()) {
                image = imageFile.getBytes(); // ②
            }
        } catch (IOException ex) {
            redirect.addFlashAttribute("error", "Could not read image: " + ex.getMessage());
            return "redirect:/administrator/add‑book";
        }

        bookService.createBook(title, author, category, publisher, isbn, stockLevel,
                price, image);
        model.addAttribute("success", "Book created successfully!");
        return "redirect:/administrator/manage-books";
    }

    @PutMapping("/books/{id}")
    public String updateBook(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @ModelAttribute("book") Book book, Model model) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        bookService.updateBook(id, book);
        model.addAttribute("success", "Book updated successfully!");
        return "redirect:/administrator/manage-books";
    }

    @DeleteMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable Long id, Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        bookService.deleteBook(id);
        model.addAttribute("success", "Book deleted successfully!");
        return "redirect:/administrator/manage-books";
    }

    // @GetMapping("/customers"){
    // // Logic to retrieve and display customers
    // return "administrator/customers";
    // }
}
