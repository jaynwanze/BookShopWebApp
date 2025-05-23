package com.example.bookshop.validation;

import org.springframework.stereotype.Component;

@Component
public class ChangePasswordValidator extends AbstractValidator {

    private String newPassword;
    private String repeat;

    public void initialize(String newPassword, String repeat) {
        this.newPassword = newPassword;
        this.repeat = repeat;
    }

    @Override
    protected boolean basicChecks() {
        if (newPassword == null || newPassword.isBlank() ||
                repeat == null || repeat.isBlank()) {
            invalidate("All fields are required.");
            return false;
        }
        if (!newPassword.equals(repeat)) {
            invalidate("New password and repeat password do not match.");
            return false;
        }

        return true;
    }

    @Override
    protected boolean formatChecks() {
        if (newPassword.length() < 6) {
            invalidate("Password must be at least 6 characters long.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean domainSpecificChecks() {
        return true; // No domain-specific rules for passwords currently
    }
}