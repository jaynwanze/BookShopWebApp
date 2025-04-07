package com.example.bookshop.service;

import com.example.bookshop.entity.OrderItem;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    public void updateStockLevels(Iterable<OrderItem> items) {
        // For each order item, deduct the quantity purchased from the current stock.
        // to be implemented
    }
}
