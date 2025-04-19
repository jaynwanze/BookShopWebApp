package com.example.bookshop.service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.factory.product.ProductFactory;
import com.example.bookshop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(String sortField, String sortDir) {
        return bookRepository.findAll(buildSort(sortField, sortDir));
    }

    public List<Book> search(String title,
            String author,
            String publisher,
            String category,
            String sortField,
            String sortDir) {

        // normalise blanks to null
        title = (title != null && !title.isBlank()) ? title : null;
        author = (author != null && !author.isBlank()) ? author : null;
        publisher = (publisher != null && !publisher.isBlank()) ? publisher : null;
        category = (category != null && !category.isBlank()) ? category : null;

        // if all filters are empty, just list everything
        if (title == null && author == null && publisher == null && category == null) {
            return getAllBooks(sortField, sortDir);
        }

        return bookRepository.advancedSearch(title, author, publisher, category,
                buildSort(sortField, sortDir));
    }

    private Sort buildSort(String field, String dir) {
        return dir.equalsIgnoreCase("desc") ? Sort.by(field).descending()
                : Sort.by(field).ascending();
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = bookRepository.findById(id).orElse(null);
        if (existingBook != null) {
            existingBook.setTitle(book.getTitle());
            existingBook.setAuthor(book.getAuthor());
            existingBook.setCategory(book.getCategory());
            existingBook.setImage(book.getImage());
            existingBook.setPrice(book.getPrice());
            return bookRepository.save(existingBook);
        }
        return null;
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book createBook(String title, String author, String category, String publisher,
            String isbn, int stockLevel, double price, byte[] image) {
        ProductFactory productFactory = new ProductFactory();
        Book book = (Book) productFactory.createBook(title, price, stockLevel,
                author, isbn, category, publisher, image);
        return bookRepository.save(book);
    }
}
