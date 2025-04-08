package com.example.bookshop.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected String title;
    protected double price;
    protected int stockLevel;

    protected Product() {
    }

    protected Product(String title, double price, int stockLevel) {
        this.title = title;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public void reduceStock(int quantity) {
        if (this.stockLevel < quantity) {
            throw new RuntimeException("Not enough stock available for product: " + this.title);
        }
        this.stockLevel -= quantity;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
}
