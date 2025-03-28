package com.example.bookshop.factory;

import com.example.bookshop.entity.Administrator;
import com.example.bookshop.entity.User;

public class AdministratorFactory implements UserFactory {
    private final String jobTitle;
    private final String department;

    public AdministratorFactory(String jobTitle, String department) {
        this.jobTitle = jobTitle;
        this.department = department;
    }

    @Override
    public User createUser(String name, String email, String password) {
        return new Administrator(name, email, password, jobTitle, department);
    }
}
