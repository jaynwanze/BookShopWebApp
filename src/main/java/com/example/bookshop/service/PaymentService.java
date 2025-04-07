package com.example.bookshop.service;

import com.example.bookshop.entity.Order;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(Order order) {
        // Simulate a delay for payment processing
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }
}
