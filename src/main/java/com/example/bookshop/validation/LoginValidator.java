package com.example.bookshop.validation;

public class LoginValidator extends AbstractValidator {

    private final String email, plainPwd;

    public LoginValidator(String email, String plainPwd) {
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
