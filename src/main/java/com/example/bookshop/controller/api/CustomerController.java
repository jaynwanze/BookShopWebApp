package com.example.bookshop.controller.api;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Order;
import com.example.bookshop.entity.ShoppingCart;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.AuthService;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.DiscountService;
import com.example.bookshop.service.OrderService;
import com.example.bookshop.service.ReviewService;
import com.example.bookshop.service.ShoppingCartService;
import com.example.bookshop.validation.AddressValidator;
import com.example.bookshop.validation.CardValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails.Address;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private AuthService authService;

    @Autowired
    private AddressValidator addressValidator;

    @Autowired
    private CardValidator cardValidator;

    // Process form submission to save a customer
    @PostMapping
    public String saveCustomer(Customer customer) {
        customerService.registerCustomer(customer);
        return "redirect:/customers";
    }

    // Show form to edit an existing customer
    @PutMapping("/{id}")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer,
            BindingResult binding,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            RedirectAttributes redirectAttributes) {

        // Validate customer details
        if (userDetails == null) {
            return "redirect:/login";
        }

        if (binding.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error updating customer details.");
            return "redirect:/customer/profile";
        }

        // Validate payment details
        cardValidator.initialize(customer.getPaymentMethod());
        if (!cardValidator.validate()) {
            redirectAttributes.addFlashAttribute("error", cardValidator.getError());
            return "redirect:/customer/profile";
        }
        // Validate shipping address
        addressValidator.initialize(customer.getShippingAddress());
        if (!addressValidator.validate()) {
            redirectAttributes.addFlashAttribute("error", addressValidator.getError());
            return "redirect:/customer/profile";
        }

        // Update customer details
        customerService.updateCustomer(customer);
        redirectAttributes.addFlashAttribute("success", "Customer details updated successfully.");
        return "redirect:/customer/profile";
    }

    // Delete a customer
    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customers";
    }

    // Show customer profile page
    @PostMapping("/change-password")
    public String changePassword(@AuthenticationPrincipal CustomUserDetails user,
            @RequestParam String currentPwd,
            @RequestParam String newPwd,
            @RequestParam String repeatPwd,
            RedirectAttributes ra) {

        if (user == null)
            return "redirect:/login";

        authService.changePassword(user.getUsername(), currentPwd, newPwd, repeatPwd, ra);
        return "redirect:/customer/profile";
    }

    // Add item to cart
    @PostMapping("/cart/add/{bookId}")
    public String addItemToCart(@AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }
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
        if (userDetails == null) {
            return "redirect:/login";
        }

        ShoppingCart cart = shoppingCartService.getShoppingCart(userDetails.getId());
        cart.removeItem(bookId);
        shoppingCartService.saveShoppingCart(userDetails.getId(), cart);

        // Sucess message to be displayed after redirect
        redirectAttributes.addFlashAttribute("success", "Item removed from cart successfully");
        return "redirect:/customer/shopping-cart"; // Redirect to the cart page after removing item
    }

    @PostMapping("/checkout/update-details")
    public String updateCheckoutInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute("customer") Customer customerFromForm,
            @RequestParam(required = false) String discountCode,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        // Update shipping and payment info
        Customer customer = customerService.getCustomerById(userDetails.getId());
        cardValidator.initialize(customerFromForm.getPaymentMethod());
        if (customerFromForm.getPaymentMethod() == null) {
            redirectAttributes.addFlashAttribute("error", "Payment method is required.");
            return "redirect:/customer/checkout-page";
        }
        addressValidator.initialize(customerFromForm.getShippingAddress());
        if (!addressValidator.validate()) {
            redirectAttributes.addFlashAttribute("error", addressValidator.getError());
            return "redirect:/customer/checkout-page";
        }
        customer.setShippingAddress(customerFromForm.getShippingAddress());
        customer.setPaymentMethod(customerFromForm.getPaymentMethod());
        customerService.updateCustomer(customer);

        // Validate discount code
        double discount = discountService.validateDiscountCode(discountCode);
        redirectAttributes.addFlashAttribute("discount", discount);
        redirectAttributes.addFlashAttribute("discountCode", discountCode);
        if (discount > 0) {
            redirectAttributes.addFlashAttribute("message", "Discount code applied! (" + (discount * 100) + "% off)");
        } else if (discountCode != null && !discountCode.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Invalid discount code.");
        }

        return "redirect:/customer/checkout-page";
    }

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute String discount,
            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        // Validate payment method
        Customer customer = customerService.getCustomerById(userDetails.getId());
        cardValidator.initialize(customer.getPaymentMethod());
        if (customer.getPaymentMethod() == null) {
            redirectAttributes.addFlashAttribute("error", "Payment method is required.");
            return "redirect:/customer/checkout-page";
        }
        try {
            Order order = orderService.placeOrder(userDetails.getId(), discount);
            redirectAttributes.addFlashAttribute("success",
                    "Order placed successfully! Your order ID is " + order.getId());
            return "redirect:/customer/dashboard";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/customer/checkout";
        }

    }

    @PostMapping("reviews/new/{bookId}")
    public String addReview(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long bookId,
            @RequestParam("rating") int rating,
            @RequestParam("comment") String comment,
            Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        reviewService.createAndSaveReview(bookId, userDetails.getId(), rating, comment);
        model.addAttribute("success", "Review added successfully!");
        return "redirect:/customer/book/" + bookId; // Redirect to the book page after adding the review
    }

}
