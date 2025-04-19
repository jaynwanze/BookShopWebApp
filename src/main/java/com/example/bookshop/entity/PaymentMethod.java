package com.example.bookshop.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMaskedNumber() {
        return maskedNumber;
    }

    public void setMaskedNumber(String maskedNumber) {
        this.maskedNumber = maskedNumber;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

}
