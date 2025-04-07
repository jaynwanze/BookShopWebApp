package com.example.bookshop.factory.user;

import com.example.bookshop.entity.Customer;
import com.example.bookshop.entity.PaymentMethod;
import com.example.bookshop.entity.ShippingAddress;
import com.example.bookshop.entity.User;

public class CustomerFactory {
    private final ShippingAddress shippingAddress;
    private final PaymentMethod paymentMethod;

    public CustomerFactory(ShippingAddress shippingAddress, PaymentMethod paymentMethod) {
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public User createUser(String name, String email, String password) {
        return new Customer(name, email, password, shippingAddress, paymentMethod);
    }
}
