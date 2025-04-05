package com.example.bookshop.factory.user;

import com.example.bookshop.entity.User;

public interface UserFactory {
    User createUser(String name, String email, String password);
}