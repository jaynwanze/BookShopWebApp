package com.example.bookshop.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class ShippingAddress implements java.io.Serializable {
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    public ShippingAddress() {}

    public ShippingAddress(String street, String city, String state, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }
}
