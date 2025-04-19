package com.example.bookshop.factory.product;

import com.example.bookshop.entity.Book;

public class ProductFactory {
    public Book createBook(String title, double price, int stockLevel,
            String author, String isbn, String category,
            String publisher, byte[] image) {
        return new Book.Builder()
                .title(title)
                .price(price)
                .stockLevel(stockLevel)
                .author(author)
                .isbn(isbn)
                .category(category)
                .publisher(publisher)
                .image(image)
                .build();
    }
}
