package com.example.bookshop.factory.item;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Item;

public interface ItemFactory {
    Item createItem(Book book, int quantity);
}