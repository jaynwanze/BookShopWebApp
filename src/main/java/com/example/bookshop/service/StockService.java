package com.example.bookshop.service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.OrderItem;
import com.example.bookshop.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    @Autowired
    private BookRepository bookRepository;

    public void updateStockLevels(Iterable<OrderItem> items) {
        for (OrderItem item : items) {
            Book book = bookRepository.findById(item.getBook().getId()).orElseThrow(
                    () -> new RuntimeException("Book not found with ID: " + item.getBook().getId()));
            book.reduceStock(item.getQuantity());
            // Save the updated Book
            bookRepository.save(book);
        }
    }
}
