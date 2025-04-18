package com.example.bookshop.controller.api;

import com.example.bookshop.entity.Book;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;
import com.example.bookshop.utils.QueryHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.management.Query;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Display a list of books, with optional search and sorting
    @GetMapping
    public String listBooks(Model model,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "title") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @AuthenticationPrincipal CustomUserDetails user) {

        if (user == null || !(user.hasRole("ROLE_CUSTOMER") || user.hasRole("ROLE_ADMIN"))) {
            return "redirect:/login";
        }

        List<Book> books = bookService.search(title, author, publisher, category,
                sortField, sortDir);

        model.addAttribute("books", books);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("title", title);
        model.addAttribute("author", author);
        model.addAttribute("publisher", publisher);
        model.addAttribute("category", category);

        if (user.hasRole("ROLE_ADMIN")) {
            String qs = QueryHelper.getInstance().buildQuery(title, author, publisher, category,
                    sortField, sortDir);
            return "redirect:/administrator/manage-books" + qs;
        } else {
            return "customer/catalog";
        }
    }

    // Show book details
    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-details";
    }
}
