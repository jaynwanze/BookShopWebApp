package com.example.bookshop.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class PaymentMethod implements java.io.Serializable {
    private String type;         
    private String maskedNumber; 
    private String expiryDate;   
    private String cardholderName;
    
    public PaymentMethod() {
    }

    public PaymentMethod(String type, String maskedNumber, String expiryDate, String cardholderName) {
        this.type = type;
        this.maskedNumber = maskedNumber;
        this.expiryDate = expiryDate;
        this.cardholderName = cardholderName;
    }
}
