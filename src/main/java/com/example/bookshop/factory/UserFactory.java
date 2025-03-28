package com.example.bookshop.factory;

import com.example.bookshop.entity.User;

public interface UserFactory {
    User createUser(String name, String email, String password);
}