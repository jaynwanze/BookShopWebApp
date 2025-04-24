package com.example.bookshop.validation;

import org.springframework.beans.factory.annotation.Autowired;
import com.example.bookshop.service.AdministratorService;
import com.example.bookshop.service.CustomerService;

public class RegisterValidator extends AbstractValidator {
    private final String email, plainPwd, name;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdministratorService administratorService;

    public RegisterValidator(String email, String name, String plainPwd) {
        this.email = email;
        this.name = name;
        this.plainPwd = plainPwd;
    }

    @Override
    protected boolean basicChecks() {
        if (email == null || email.isBlank()
                || name == null || name.isBlank()
                || plainPwd == null || plainPwd.isBlank()) {
            invalidate("All fields are required");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {
        if (!email.matches("^[\\w.%+-]+@\\w+\\.\\w{2,}$")) {
            invalidate("Invalid e-mail address");
            return false;
        }
        if (plainPwd.length() < 6) {
            invalidate("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    @Override
    protected boolean domainSpecificChecks() {
        if (customerService.getCustomerByEmail(email) != null ||
                administratorService.getAdministratorByEmail(email) != null) {
            invalidate("An account with this e-mail already exists");
            return false;
        }
        return true;
    }

}
