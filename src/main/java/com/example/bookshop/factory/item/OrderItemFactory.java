package com.example.bookshop.factory.item;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Item;
import com.example.bookshop.entity.OrderItem;

public class OrderItemFactory implements ItemFactory {
    private final Long orderId;
    private final double price;

    public OrderItemFactory(Long orderId, double price) {
        this.orderId = orderId;
        this.price = price;
    }

    @Override
    public Item createItem(Book book, int quantity) {
        return new OrderItem(book, quantity, price, orderId);
    }

}
