package com.example.bookshop.validation;

import com.example.bookshop.security.CustomUserDetails;

public abstract class AbstractValidator {

    private String error;

    public final boolean validate() {
        if (!basicChecks())
            return false;
        if (!formatChecks())
            return false;
        if (!domainSpecificChecks())
            return false;
        return true;
    }

    // Blank fields, length checks, etc.
    protected boolean basicChecks() {
        return true;
    }

    // e-mail, regex-based card number checks, password strength
    protected boolean formatChecks() {
        return true;
    }

    // Domain-specific checks, e.g. if a book is already in the cart
    protected boolean domainSpecificChecks() {
        return true;
    }

    // Provides message to the user if validation fails
    protected void invalidate(String msg) {
        this.error = msg;
    }

    public String getError() {
        return error;
    }
}
