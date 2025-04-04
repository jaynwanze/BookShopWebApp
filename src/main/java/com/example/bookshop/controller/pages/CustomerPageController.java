package com.example.bookshop.controller.pages;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.example.bookshop.entity.Book;
import com.example.bookshop.service.BookService;

@Controller
@RequestMapping("/customer")

public class CustomerPageController {
    @Autowired
    BookService bookService;

    @GetMapping("/dashboard")
    public String customerDashboardPage() {
        return "customer/dashboard";
    }

    @GetMapping("/shopping-cart")
    public String shoppingCartPage() {
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
    public String catalogPage(Model model) {
        //Intial sort by title in ascending order
        List<Book> books = bookService.getAllBooks( "title", "asc"); 
        model.addAttribute("books", books);
        return "customer/catalog";
    }
}
