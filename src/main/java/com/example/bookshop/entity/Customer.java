package com.example.bookshop.entity;

import jakarta.persistence.*;

@Entity
public class Customer extends User {

    private String shippingAddress;
    private String paymentMethod;

    public Customer() {
        super();
    }

    public Customer(String name, String email, String password, String shippingAddress, String paymentMethod) {
        super(name, email, password);
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters
    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}