package com.example.bookshop.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.HashMap;

@Service
public class DiscountService {

    private static final Map<String, Double> discountCodes = new HashMap<>();

    static {
        discountCodes.put("SAVE10", 0.10);
        discountCodes.put("SAVE20", 0.20);
    }

    // Validates a discount code and returns the discount percentage if valid,
    public double validateDiscountCode(String code) {
        if (code == null) {
            return 0.0;
        }
        Double discount = discountCodes.get(code.toUpperCase());
        return discount != null ? discount : 0.0;
    }
}
