package com.example.bookshop.entity;

import java.io.Serializable;

public class CartItem extends Item implements Serializable {

    public CartItem(Book book, int quantity) {
        super(book, quantity);
    }

    public CartItem() {
    }

}
