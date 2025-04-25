package com.example.bookshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.bookshop.service.CustomerService;
import com.example.bookshop.service.AdministratorService;

@Component
public class RegisterValidator extends AbstractValidator {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    private String email;
    private String name;
    private String plainPwd;

    public void initialize(String email, String name, String plainPwd) {
        this.email = email;
        this.name = name;
        this.plainPwd = plainPwd;
    }

    @Override
    protected boolean basicChecks() {
        if (email == null || email.isBlank() ||
                name == null || name.isBlank() ||
                plainPwd == null || plainPwd.isBlank()) {
            invalidate("All fields are required.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {
        if (!email.matches("^[\\w.%+-]+@[\\w.-]+\\.\\w{2,}$")) {
            invalidate("Invalid email format.");
            return false;
        }
        if (plainPwd.length() < 6) {
            invalidate("Password must be at least 6 characters long.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean domainSpecificChecks() {
        if (customerService.getCustomerByEmail(email) != null ||
                administratorService.getAdministratorByEmail(email) != null) {
            invalidate("An account with this email already exists.");
            return false;
        }
        return true;
    }
}