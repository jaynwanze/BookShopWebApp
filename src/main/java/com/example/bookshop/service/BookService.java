package com.example.bookshop.service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.factory.product.ProductFactory;
import com.example.bookshop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks(String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return bookRepository.findAll(sort);
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

    // search method with sorting (Strategy pattern)
    public List<Book> searchBooksByTitle(String title, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        return bookRepository.findAllByTitleContainingIgnoreCase(title, sort);
    }

    public Book createBook(String title, String author, String category, String publisher,
            String isbn, int stockLevel, double price) {
        ProductFactory productFactory = new ProductFactory();
        Book book = (Book) productFactory.createProduct("book", title, price, stockLevel, author, category, publisher, null, isbn);
        return bookRepository.save(book);
    }
}
