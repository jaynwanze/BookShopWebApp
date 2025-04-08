package com.example.bookshop.controller.api;

import com.example.bookshop.entity.Book;
import com.example.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;
    // Display a list of books, with optional search and sorting
    @GetMapping
    public String listBooks(Model model,
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "title") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir) {
        List<Book> books;
        if (title != null && !title.isEmpty()) {
            books = bookService.searchBooksByTitle(title, sortField, sortDir);
        } else {
            books = bookService.getAllBooks(sortField, sortDir);
        }
        model.addAttribute("books", books);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "customer/catalog";
    }
    // Show book details
    @GetMapping("/{id}")
    public String showBookDetails(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-details";
    }
}
