package com.example.bookshop.entity;

import jakarta.persistence.Entity;

@Entity
public class Administrator extends User {

    // For example, an administrator might have additional roles or privileges.
    private String adminRole;

    public Administrator() {
        super();
    }

    public Administrator(String name, String email, String password, String adminRole) {
        super(name, email, password);
        this.adminRole = adminRole;
    }

    // Getters and setters
    public String getAdminRole() {
        return adminRole;
    }

    public void setAdminRole(String adminRole) {
        this.adminRole = adminRole;
    }
}
