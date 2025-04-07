package com.example.bookshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.ReviewService;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/new/{bookId}")
    public String addComment(@AuthenticationPrincipal CustomUserDetails customer, @RequestBody String comment,
            @RequestBody int rating,
            @PathVariable Long bookId) {
        if (customer == null) {
            return "redirect:/login"; // Redirect to login if not authenticated
        }
        reviewService.createAndSaveReview(bookId, customer.getId(), rating, comment);
        return "redirect:/books/" + bookId;
    }
}
