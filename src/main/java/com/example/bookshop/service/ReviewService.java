package com.example.bookshop.service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.Review;
import com.example.bookshop.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private CustomerService customerService;

    public Review saveReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getReviewsForBook(Long bookId) {
        return reviewRepository.findByBookId(bookId);
    }

    // Example method using the Builder to create a review
    public Review createAndSaveReview(Long bookId, Long customerId, int rating, String comment) {
        Book book = bookService.getBookById(bookId);
        Customer customer = customerService.getCustomerById(customerId);
        if (book == null || customer == null) {
            throw new IllegalArgumentException("Book or Customer not found");
        }

        Review review = new Review.Builder()
                .book(book)
                .customer(customer)
                .rating(rating)
                .comment(comment)
                .reviewDate(new java.sql.Date(System.currentTimeMillis()))
                .build();
        return saveReview(review);
    }
}
