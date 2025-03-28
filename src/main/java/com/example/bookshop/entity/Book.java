package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String publisher;
    private double price;
    private String category;
    private String isbn;

    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    public Book() { }

    // Private constructor for the builder
    private Book(Builder builder) {
        this.title = builder.title;
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.price = builder.price;
        this.category = builder.category;
        this.isbn = builder.isbn;
        this.image = builder.image;
    }

    public static class Builder {
        private String title;
        private String author;
        private String publisher;
        private double price;
        private String category;
        private String isbn;
        private String image;

        public Builder title(String title) {
            this.title = title;
            return this;
        }
        public Builder author(String author) {
            this.author = author;
            return this;
        }
        public Builder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }
        public Builder price(double price) {
            this.price = price;
            return this;
        }
        public Builder category(String category) {
            this.category = category;
            return this;
        }
        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }
        public Builder image(String image) {
            this.image = image;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}