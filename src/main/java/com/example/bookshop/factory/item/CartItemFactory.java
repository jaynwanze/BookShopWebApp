package com.example.bookshop.factory.item;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.CartItem;

public class CartItemFactory implements ItemFactory {

    public CartItemFactory() {
        // Default constructor
    }

    @Override
    public CartItem createItem(Book book, int quantity) {
        return new CartItem(book, quantity);
    }

}
