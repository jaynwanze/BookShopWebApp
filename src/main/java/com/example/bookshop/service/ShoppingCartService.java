package com.example.bookshop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.bookshop.entity.Book;
import com.example.bookshop.entity.CartItem;
import com.example.bookshop.entity.ShoppingCart;

@Service
public class ShoppingCartService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private BookService bookService;

    private static final String ShoppingCart_KEY_PREFIX = "ShoppingCart:";

    public ShoppingCart getShoppingCart(Long customerId) {
        ShoppingCart cart = (ShoppingCart) redisTemplate.opsForValue().get(ShoppingCart_KEY_PREFIX + customerId);
        if (cart == null) {
            cart = new ShoppingCart(customerId);
        }
        return cart;
    }

    public void saveShoppingCart(Long customerId, ShoppingCart ShoppingCart) {
        redisTemplate.opsForValue().set(ShoppingCart_KEY_PREFIX + customerId, ShoppingCart);
    }

    public void clearShoppingCart(Long customerId) {
        redisTemplate.delete(ShoppingCart_KEY_PREFIX + customerId);
    }

    public List<CartItem> getCartItems(ShoppingCart cart) {
        List<CartItem> cartItems = cart.getItems().entrySet().stream()
                .map(entry -> {
                    Long bookId = entry.getKey();
                    int quantity = entry.getValue();
                    Book book = bookService.getBookById(bookId);
                    return new CartItem(book, quantity);
                })
                .toList();
        return cartItems;
    }

    public double calculateCartTotal(List<CartItem> cartItems) {
        double total = cartItems.stream()
        .mapToDouble(item -> item.getBook().getPrice() * item.getQuantity())
        .sum();
        return total;   
    }
}
