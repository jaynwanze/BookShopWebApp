package com.example.bookshop.controller.api;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/cart/add/{bookId}")
    public String addItemToCart(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        // Add quantity parameter to the method signature
        int quantity = 1; // Default quantity to add
        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        cart.addItem(bookId, quantity);
        shoppingCartService.saveShoppingCart(userDetails.getId(), cart);

        // Sucess message to be displayed after redirect
        redirectAttributes.addFlashAttribute("success", "Item added to cart successfully");
        return "redirect:/customer/catalog"; // Redirect to the cart page after adding item
    }

    @PostMapping("/cart/remove/{bookId}")
    public String removeItemFromCart(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId,
            RedirectAttributes redirectAttributes) {

        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        cart.removeItem(bookId);
        shoppingCartService.saveShoppingCart(userDetails.getId(), cart);

        // Sucess message to be displayed after redirect
        redirectAttributes.addFlashAttribute("success", "Item removed from cart successfully");
        return "redirect:/customer/shopping-cart"; // Redirect to the cart page after removing item
    }

    @PostMapping("/update-checkout-info")
    public String updateCheckoutInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute("customer") Customer customerFromForm,
            RedirectAttributes redirectAttributes) {

        // Get the existing customer record
        Customer customer = customerService.getCustomerById(userDetails.getId());
        if (customer == null) {
            redirectAttributes.addFlashAttribute("error", "Customer not found.");
            return "redirect:/customer/checkout";
        }

        // Update shipping address if provided
        if (customerFromForm.getShippingAddress() != null) {
            customer.setShippingAddress(customerFromForm.getShippingAddress());
        }
        // Update payment method if provided
        if (customerFromForm.getPaymentMethod() != null) {
            customer.setPaymentMethod(customerFromForm.getPaymentMethod());
        }

        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("message", "Your information has been updated successfully.");
        return "redirect:/customer/checkout-page";
    }

}
