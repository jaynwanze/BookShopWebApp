package com.example.bookshop.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Administrator extends User {

    private String jobTitle;
    private String department;

    public Administrator() {
        super();
    }

    public Administrator(String name, String email, String password, String jobTitle, String department) {
        super(name, email, password);
        this.jobTitle = jobTitle;
        this.department = department;
    }
}
