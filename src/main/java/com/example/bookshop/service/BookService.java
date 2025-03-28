package com.example.bookshop.service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    //search method with sorting (Strategy pattern)
    public List<Book> searchBooksByTitle(String title, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        return bookRepository.findAllByTitleContainingIgnoreCase(title, sort);
    }
}
