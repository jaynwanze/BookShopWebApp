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
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.BookService;
import com.example.bookshop.service.ShoppingCartService;

@Controller
@RequestMapping("/customer")

public class CustomerPageController {
    @Autowired
    BookService bookService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("/dashboard")
    public String customerDashboardPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        model.addAttribute("authCustomerId", String.valueOf(userDetails.getId()));
        return "customer/dashboard";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCartPage(@AuthenticationPrincipal CustomUserDetails userDetails , Model model) {
        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        List<CartItem> cartItems = shoppingCartService.getCartItems(cart);
        double total = shoppingCartService.calculateCartTotal(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "customer/cart";
    }

    @GetMapping("/profile")
    public String customerProfilePage() {
        return "customer/profile";
    }

    @GetMapping("/checkout")
    public String checkoutPage() {
        return "customer/checkout";
    }

    @GetMapping("/catalog")
    public String catalogPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        // Intial sort by title in ascending order
        List<Book> books = bookService.getAllBooks("title", "asc");
        model.addAttribute("books", books);
        return "customer/catalog";
    }
}
