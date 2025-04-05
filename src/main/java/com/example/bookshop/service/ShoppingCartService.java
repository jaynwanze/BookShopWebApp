package com.example.bookshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.bookshop.entity.ShoppingCart;

@Service
public class ShoppingCartService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String ShoppingCart_KEY_PREFIX = "ShoppingCart:";

    public ShoppingCart getShoppingCart(Long customerId) {
        ShoppingCart cart = (ShoppingCart) redisTemplate.opsForValue().get(ShoppingCart_KEY_PREFIX + customerId);
        if(cart == null) {
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
    
}
