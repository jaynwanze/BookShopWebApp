package com.example.bookshop.validation;

import com.example.bookshop.entity.Book;
import com.example.bookshop.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookValidatorTest {

    @Autowired
    private BookValidator validator;

    @MockBean
    private BookService bookService;

    @Test
    void rejects_negativePrice() {
        Book b = new Book.Builder()
                .title("Test")
                .author("Someone")
                .price(-1)
                .stockLevel(5)
                .isbn("1234567890")
                .build();

        validator.intialize(b);
        boolean ok = validator.validate();

        assertThat(ok).isFalse();
        assertThat(validator.getError()).contains("Price");
    }

    @Test
    void accepts_validBook() {
        Book b = new Book.Builder()
                .title("Clean Code")
                .author("Robert C. Martin")
                .price(29.99)
                .stockLevel(10)
                .isbn("0132350882")
                .build();

        // Mock the behavior of bookService
        when(bookService.getBookByISBN("0132350882")).thenReturn(null);

        validator.intialize(b);
        assertThat(validator.validate()).isTrue();
    }
}