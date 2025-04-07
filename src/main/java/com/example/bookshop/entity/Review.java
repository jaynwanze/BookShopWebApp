package com.example.bookshop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // Relationships
    @ManyToOne
    private Book book;
    
    @ManyToOne
    private Customer customer;
    
    private int rating; // e.g., scale 1-5
    @Column(length = 1000)
    private String comment;
    private Date reviewDate;

    // Private constructor used by the builder
    private Review(Builder builder) {
        this.book = builder.book;
        this.customer = builder.customer;
        this.rating = builder.rating;
        this.comment = builder.comment;
        this.reviewDate = builder.reviewDate != null ? builder.reviewDate : new Date();
    }
    
    // Default constructor for JPA
    public Review() {}

    // Getters (and setters if needed)

    public static class Builder {
        private Book book;
        private Customer customer;
        private int rating;
        private String comment;
        private Date reviewDate;

        public Builder book(Book book) {
            this.book = book;
            return this;
        }

        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public Builder rating(int rating) {
            this.rating = rating;
            return this;
        }

        public Builder comment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder reviewDate(Date reviewDate) {
            this.reviewDate = reviewDate;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    
}
