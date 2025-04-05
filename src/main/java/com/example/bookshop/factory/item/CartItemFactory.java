package com.example.bookshop.factory.item;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.CartItem;
import com.example.bookshop.entity.Item;

public class CartItemFactory implements ItemFactory {

    public CartItemFactory() {
        // Default constructor
    }

    @Override
    public Item createItem(Book book, int quantity) {
        return new CartItem(book, quantity);
    }

}
