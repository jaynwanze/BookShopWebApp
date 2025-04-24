package com.example.bookshop.entity;

import jakarta.persistence.Entity;


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

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
