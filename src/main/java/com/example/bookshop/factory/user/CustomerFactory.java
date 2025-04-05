package com.example.bookshop.factory.user;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.User;

public class CustomerFactory {
    private final String shippingAddress;
    private final String paymentMethod;

    public CustomerFactory(String shippingAddress, String paymentMethod) {
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public User createUser(String name, String email, String password) {
        return new Customer(name, email, password, shippingAddress, paymentMethod);
    }
}
