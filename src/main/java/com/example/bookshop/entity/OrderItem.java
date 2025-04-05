package com.example.bookshop.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItem extends Item implements Serializable {

    private Long orderId;
    private Book book;
    private int quantity;
    private double price;

    public OrderItem() {
        super();
    }

    public OrderItem(Book book, int quantity, double price, Long orderId) {
        super(book, quantity);
        this.book = book;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
    }

    // Getter and Setter methods for orderId price
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
