package com.example.bookshop.controller.pages;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.Review;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.AdministratorService;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.ReviewService;

@Controller
@RequestMapping("/administrator")
public class AdministratorPageController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private AdministratorService adminService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/dashboard")
    public String administratorDashboardPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        String adminName = adminService.getAdministratorNameById(userDetails.getId());
        model.addAttribute("adminName", adminName);
        return "administrator/dashboard";
    }

    @GetMapping("/manage-books")
    public String manageBooksPage(
            @AuthenticationPrincipal CustomUserDetails user,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "title") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            Model model) {

        if (user == null || !user.hasRole("ROLE_ADMIN")) {
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

        return "administrator/books";
    }

    // 1) Display all customers (with optional search)
    @GetMapping("/manage-customers")
    public String manageCustomersPage(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) String search,
            Model model) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login";
        }

        // If "search" is provided, filter by name or email
        List<Customer> customers;
        if (search != null && !search.isEmpty()) {
            customers = customerService.searchCustomersByNameOrEmail(search);
        } else {
            customers = customerService.findAllCustomers();
        }
        model.addAttribute("customers", customers);
        return "administrator/customers"; // The template you showed
    }

    // 4) View details, including purchase history
    @GetMapping("customer/{id}")
    public String customerDetails(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            Model model) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return "redirect:/administrator/customers"; // or some error page
        }

        // Retrieve that customer’s purchase history
        List<Order> orders = orderService.findOrdersByCustomerId(id);

        model.addAttribute("customer", customer);
        model.addAttribute("orders", orders);
        return "administrator/customer-details";
    }

    @GetMapping("/add-book")
    public String addBookPage(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        return "administrator/book-form-add";
    }

    @GetMapping("/edit-book/{id}")
    public String editBookPage(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model,
            @RequestParam(name = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book); // Add the book to the model

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                book.setImage(imageFile.getBytes());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Could not read image: " + e.getMessage());
            return "redirect:/administrator/edit-book/" + id;
        }

        // Retrieve all reviews for this book
        List<Review> reviews = reviewService.getReviewsForBook(id);

        model.addAttribute("currentImage",
                book.getImage() == null ? null
                        : "data:image/png;base64," +
                                Base64.getEncoder().encodeToString(book.getImage()));
        model.addAttribute("reviews", reviews);
        return "administrator/book-form-edit";

    }

    @GetMapping("/customer-details/{id}")
    public String customerDetailsPage(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model, RedirectAttributes redirectAttributes) {
        if (userDetails == null || !userDetails.hasRole("ROLE_ADMIN")) {
            return "redirect:/login"; // Redirect to login page if not authenticated
        }
        // Check if the customer exists
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Customer not found.");
            return "redirect:/administrator/customers";
        }
        model.addAttribute("customer", customer);
        // Retrieve that customer’s purchase history
        List<Order> orders = orderService.findOrdersByCustomerId(id);
        model.addAttribute("orders", orders);
        return "administrator/customer-details";
    }

    @GetMapping("/profile")
    public String adminProfilePage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        // Retrieve the admin by ID
        Administrator admin = adminService.getAdministratorById(userDetails.getId());
        if (admin == null) {
            redirectAttributes.addFlashAttribute("error", "Administrator not found.");
            return "redirect:/administrator/dashboard";
        }

        // Add the admin to the model
        model.addAttribute("admin", admin);
        return "administrator/profile";
    }
}
