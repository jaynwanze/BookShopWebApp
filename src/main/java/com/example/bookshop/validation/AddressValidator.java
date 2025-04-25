// src/main/java/com/example/bookshop/validation/ShippingAddressValidator.java
package com.example.bookshop.validation;

import com.example.bookshop.entity.ShippingAddress;

public class AddressValidator extends AbstractValidator {

    private final ShippingAddress addr;

    public AddressValidator(ShippingAddress addr) {
        this.addr = addr;
    }

    @Override
    protected boolean basicChecks() {

        if (addr == null) {
            invalidate("Shipping address is required");
            return false;
        }

        if (isBlank(addr.getStreet())   ||
            isBlank(addr.getCity())     ||
            isBlank(addr.getPostalCode()) ||
            isBlank(addr.getCountry())) {

            invalidate("Street, city, postal code and country are mandatory");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {

        // Simple postal-code rule: 3-10 letters / digits / dash / space
        if (!addr.getPostalCode().matches("[A-Za-z0-9\\- ]{3,10}")) {
            invalidate("Postal code must be 3-10 alphanumerics");
            return false;
        }

        // Optional state but, if present, at least 2 chars
        if (!isBlank(addr.getState()) && addr.getState().trim().length() < 2) {
            invalidate("State / province must contain at least 2 characters");
            return false;
        }

        return true;
    }

    @Override
    protected boolean domainSpecificChecks() {
        return true;       
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
