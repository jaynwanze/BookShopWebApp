package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}