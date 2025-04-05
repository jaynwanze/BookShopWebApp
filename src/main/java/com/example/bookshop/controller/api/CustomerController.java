package com.example.bookshop.controller.api;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.ShoppingCartService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Display a list of customers
    @GetMapping
    public String listCustomers(Model model) {
        // model.addAttribute("customers", customerService.getAllCustomers());
        return "customer-list"; // Thymeleaf template name
    }

    // Show form to add a new customer
    @GetMapping("/new")
    public String showNewCustomerForm(Model model) {
        // model.addAttribute("customer", new Customer());
        return "customer-form";
    }

    // Process form submission to save a customer
    @PostMapping
    public String saveCustomer(Customer customer) {
        customerService.registerCustomer(customer);
        return "redirect:/customers";
    }

    // Show form to edit an existing customer
    @GetMapping("/edit/{id}")
    public String showEditCustomerForm(@PathVariable Long id, Model model) {
        // Customer customer = customerService.getCustomerById(id);
        // model.addAttribute("customer", customer);
        return "customer-form";
    }

    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    // Add item to cart
    @PostMapping("/cart/add/{customerId}/{bookId}")
    public ResponseEntity<Map<String, Object>> addItemToCart(@PathVariable Long customerId, @PathVariable Long bookId) {
        // Add quantity parameter to the method signature
        int quantity = 1; // Default quantity to add
        ShoppingCart cart = shoppingCartService.getShoppingCart(customerId);
        cart.addItem(bookId, quantity);
        shoppingCartService.saveShoppingCart(customerId, cart);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Item added to cart successfully");
        response.put("cart", cart);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
