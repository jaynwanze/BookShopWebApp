package com.example.bookshop.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.bookshop.security.CustomUserDetails;
import com.example.bookshop.service.ReviewService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/new/{bookId}")
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
