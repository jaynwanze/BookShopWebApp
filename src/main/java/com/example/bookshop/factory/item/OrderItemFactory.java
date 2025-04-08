package com.example.bookshop.factory.item;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.OrderItem;
import com.example.bookshop.entity.Order;

public class OrderItemFactory implements ItemFactory {
    private final Order order;
    private final double price;

    public OrderItemFactory(Order order, double price) {
        this.order = order;
        this.price = price;
    }

    @Override
    public OrderItem createItem(Book book, int quantity) {
        return new OrderItem(book, quantity, price, order);
    }

}
