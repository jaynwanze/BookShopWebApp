package com.example.bookshop.validation;

import org.springframework.stereotype.Component;

@Component
public class LoginValidator extends AbstractValidator {

    private String email, plainPwd;

    public void initialize(String email, String plainPwd) {
        this.email = email;
        this.plainPwd = plainPwd;
    }

    @Override
    protected boolean basicChecks() {
        if (email == null || email.isBlank() || plainPwd == null || plainPwd.isBlank()) {
            invalidate("E-mail and password are required");
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
}
