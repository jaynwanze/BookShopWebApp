package com.example.bookshop.controller.pages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.CartItem;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Review;
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.ReviewService;
import com.example.bookshop.service.ShoppingCartService;

@Controller
@RequestMapping("/customer")

public class CustomerPageController {
    @Autowired
    BookService bookService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    CustomerService customerService;

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/dashboard")
    public String customerDashboardPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        return "customer/dashboard";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCartPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        List<CartItem> cartItems = shoppingCartService.getCartItems(cart);
        double total = shoppingCartService.calculateCartTotal(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "customer/cart";
    }

    @GetMapping("/profile")
    public String customerProfilePage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        return "customer/profile";
    }

    @GetMapping("/catalog")
    public String catalogPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        // Intial sort by title in ascending order
        List<Book> books = bookService.getAllBooks("title", "asc");
        model.addAttribute("books", books);
        return "customer/catalog";
    }

    @GetMapping("/checkout-page")
    public String checkoutPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Customer customer = customerService.getCustomerById(userDetails.getId());
        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        List<CartItem> cartItems = shoppingCartService.getCartItems(cart);
        double subtotal = shoppingCartService.calculateCartTotal(cartItems);

        // Retrieve any applied discount from the model
        Double discount = (Double) model.asMap().get("discount");
        if (discount == null) {
            discount = 0.0;
        }
        double total = subtotal * (1 - discount);

        model.addAttribute("customer", customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("total", total);
        model.addAttribute("discountCode", model.asMap().get("discountCode"));
        model.addAttribute("discount", discount);
        return "customer/checkout";
    }

    @GetMapping("/book/{id}")
    public String showBookDetails(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        // Retrieve the book by its ID
        Book book = bookService.getBookById(id);
        if (book == null) {
            model.addAttribute("error", "Book not found");
            return "redirect:/customer/catalog";
        }
        // Retrieve all reviews for this book
        List<Review> reviews = reviewService.getReviewsForBook(id);

        model.addAttribute("book", book);
        model.addAttribute("reviews", reviews);
        return "customer/book-details";
    }
}
