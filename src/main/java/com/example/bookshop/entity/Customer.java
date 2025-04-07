package com.example.bookshop.entity;

import jakarta.persistence.*;
@Entity
public class Customer extends User {


    @Embedded
    private ShippingAddress shippingAddress;
    @Embedded
    private PaymentMethod paymentMethod;

    public Customer() {
        super();
    }

    public Customer(String name, String email, String password, ShippingAddress shippingAddress, PaymentMethod paymentMethod) {
        super(name, email, password);
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
    }

    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}