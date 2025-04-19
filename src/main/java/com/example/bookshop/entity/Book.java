package com.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
public class Book extends Product {

    private String author;
    private String publisher;
    private String category;
    private String isbn;

    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] image;

    public Book() {
        super();
    }

    // Builder-based or direct constructor usage
    private Book(Builder builder) {
        super(builder.title, builder.price, builder.stockLevel);
        this.author = builder.author;
        this.publisher = builder.publisher;
        this.category = builder.category;
        this.isbn = builder.isbn;
        this.image = builder.image;
    }

    // Builder pattern
    public static class Builder {
        private String title;
        private double price;
        private int stockLevel; // from Product
        private String author;
        private String publisher;
        private String category;
        private String isbn;
        private byte[] image; // <- change to byte[]

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public Builder stockLevel(int stockLevel) {
            this.stockLevel = stockLevel;
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

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public Builder image(byte[] image) {
            this.image = image;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    // Getters & Setters
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}