package com.example.bookshop.entity;


public abstract class Item {

    private Book book;
    private int quantity;

    public Item() {
        super();
    }

    public Item(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    // Getter and Setter methods for book and quantity
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
