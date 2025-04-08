package com.example.bookshop.factory.product;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Product;

public class ProductFactory {

    public Product createProduct(String productType, String title, double price, int stockLevel) {
        switch (productType.toLowerCase()) {
            case "book":
                Book book = new Book.Builder()
                        .title(title)
                        .price(price)
                        .stockLevel(stockLevel)
                        .build();
                return book;
            default:
                throw new IllegalArgumentException("Unknown product type: " + productType);
        }
    }
}
