package com.example.bookshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.bookshop.entity.Book;
import com.example.bookshop.service.BookService;

@Component
public class BookValidator extends AbstractValidator {

    private Book book;

    @Autowired
    private BookService bookService;

    public void intialize(Book book) {
        this.book = book;
    }

    @Override
    protected boolean basicChecks() {
        if (book == null) {
            invalidate("Book must not be null");
            return false;
        }
        if (isBlank(book.getTitle()) || isBlank(book.getAuthor())) {
            invalidate("Title and author are required");
            return false;
        }
        if (book.getPrice() < 0) {
            invalidate("Price cannot be negative");
            return false;
        }
        if (book.getStockLevel() < 0) {
            invalidate("Stock level cannot be negative");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {
        /* ISBN-10 validation */
        if (!isBlank(book.getIsbn()) &&
            !book.getIsbn().matches("^\\d{9}[\\dX]$")) {
            invalidate("ISBN must be 10 digits");
            return false;
        }
        return true;
    }

    @Override
    protected boolean domainSpecificChecks() {
        /* ISBN must be unique */
        if (book.getId() == null) {
            Book existingBook = bookService.getBookByISBN(book.getIsbn());
            if (existingBook != null) {
                invalidate("ISBN must be unique");
                return false;
            }
        } else {
            Book existingBook = bookService.getBookById(book.getId());
            if (existingBook == null) {
                invalidate("Book not found");
                return false;
            }
        }
        return true;
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
