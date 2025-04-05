package com.example.bookshop.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {
    private Long customerId;
    // Map of Book ID to Quantity
    private Map<Long, Integer> items = new HashMap<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Long customerId) {
        this.customerId = customerId;
    }

    public Long getcustomerId() {
        return customerId;
    }

    public void setcustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }

    public void addItem(Long bookId, int quantity) {
        items.merge(bookId, quantity, Integer::sum);
    }
  
    public void removeItem(Long bookId) {
        items.remove(bookId);
    }
}
