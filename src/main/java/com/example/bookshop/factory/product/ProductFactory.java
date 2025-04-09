package com.example.bookshop.factory.product;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.Product;

public class ProductFactory {

    public Product createProduct(String productType, String title,
            double price, int stockLevel, String author, String category, String publisher,
            String image, String isbn) {
        {
            switch (productType.toLowerCase()) {
                case "book":
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
                default:
                    throw new IllegalArgumentException("Unknown product type: " + productType);
            }
        }
    }
}
