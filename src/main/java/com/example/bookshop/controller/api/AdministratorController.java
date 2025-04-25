package com.example.bookshop.controller.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.entity.Book;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.AdministratorService;
import com.example.bookshop.service.AuthService;
import com.example.bookshop.service.BookService;
import com.example.bookshop.validation.BookValidator;

@Controller
@RequestMapping("/administrators")
public class AdministratorController {

    private final AdministratorService administratorService;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthService authService;

    @Autowired
    private BookValidator bookValidator;

    AdministratorController(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

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
            return "redirect:/administrator/add-book";
        }

        Book newBook = bookService.createBook(title, author, category, publisher, isbn, stockLevel,
                price, image);

        if (newBook == null) {
            redirect.addFlashAttribute("error", "Error creating book. Please try again.");
            return "redirect:/administrator/add-book";
        }
        // Validate the book object using the BookValidator
        bookValidator.intialize(newBook);
        if (bookValidator.validate()) {
            redirect.addFlashAttribute("error", bookValidator.getError());
            return "redirect:/administrator/add-book";
        }

        model.addAttribute("success", "Book created successfully!");
        return "redirect:/administrator/manage-books";
    }

    @PutMapping("/books/{id}")
    public String updateBook(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @ModelAttribute("book") Book book, Model model,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirect) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }

        try {
            /* keep existing image unless a new one was chosen */
            if (imageFile != null && !imageFile.isEmpty()) {
                book.setImage(imageFile.getBytes());
            } else {
                byte[] current = bookService.getBookById(id).getImage();
                book.setImage(current);
            }
        } catch (

        IOException ex) {
            redirect.addFlashAttribute("error", "Could not read image: " + ex.getMessage());
            return "redirect:/administrator/edit-book/" + id;
        }
        bookService.updateBook(id, book);
        redirect.addFlashAttribute("success", "Book updated successfully!");
        return "redirect:/administrator/edit-book/" + id;
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

    // Edit customer details
    @PutMapping("/{id}")
    public String updateAdmin(@PathVariable Long id, @ModelAttribute("administrator") Administrator admin,
            BindingResult binding,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login";
        }

        if (binding.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error updating customer details.");
            return "redirect:/customer/profile";
        }

        // Update the administrator details
        administratorService.updateAdministrator(admin);
        redirectAttributes.addFlashAttribute("success", "Administrator details updated successfully.");
        return "redirect:/administrator/profile";
    }

    // Show customer profile page
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String newPwd,
            @RequestParam String repeatPwd,
            RedirectAttributes ra) {

        if (user == null)
            return "redirect:/login";

        authService.changePassword(user.getUsername(), newPwd, repeatPwd, ra);
        return "redirect:/customer/profile";
    }

}
