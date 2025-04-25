package com.example.bookshop.repository;

import com.example.bookshop.entity.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
                SELECT b
                  FROM Book b
                 WHERE (:title     IS NULL OR LOWER(b.title)     LIKE LOWER(CONCAT('%', :title, '%')))
                   AND (:author    IS NULL OR LOWER(b.author)    LIKE LOWER(CONCAT('%', :author, '%')))
                   AND (:publisher IS NULL OR LOWER(b.publisher) LIKE LOWER(CONCAT('%', :publisher, '%')))
                   AND (:category  IS NULL OR LOWER(b.category)  LIKE LOWER(CONCAT('%', :category, '%')))
            """)
    List<Book> advancedSearch(String title,
            String author,
            String publisher,
            String category,
            Sort sort);

    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByCategory(String category);

    List<Book> findAllByTitleContainingIgnoreCase(String title, Sort sort);

    Book findByIsbn(String isbn);
}
