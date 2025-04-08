package com.example.bookshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.bookshop.entity.Order;
import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.OrderService;
@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/place-order")
    public String placeOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute String discount,
            RedirectAttributes redirectAttributes) {
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
}
