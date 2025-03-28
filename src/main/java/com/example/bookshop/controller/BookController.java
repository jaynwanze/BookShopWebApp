package com.example.bookshop.controller;

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
    
    // Display a list of books
    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(required = false) String title,
                            @RequestParam(defaultValue = "title") String sortField,
                            @RequestParam(defaultValue = "asc") String sortDir) {
        List<Book> books;
        if (title != null && !title.isEmpty()) {
            books = bookService.searchBooksByTitle(title, sortField, sortDir);
        } else {
            books = bookService.getAllBooks();
        }
        model.addAttribute("books", books);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        return "book-list";
    }
    
    // Show form to add a new book
    @GetMapping("/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }
    
    // Process form submission to save a book
    @PostMapping
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }
    
    // Show form to edit an existing book
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        return "book-form";
    }
    
    // Delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
