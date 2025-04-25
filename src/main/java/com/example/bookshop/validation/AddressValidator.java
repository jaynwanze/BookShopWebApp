// src/main/java/com/example/bookshop/validation/ShippingAddressValidator.java
package com.example.bookshop.validation;

import org.springframework.stereotype.Component;

import com.example.bookshop.entity.ShippingAddress;

@Component
public class AddressValidator extends AbstractValidator {

    private ShippingAddress addr;

    public void initialize(ShippingAddress addr) {
        this.addr = addr;
    }

    @Override
    protected boolean basicChecks() {

        if (addr == null) {
            invalidate("Shipping address is required");
            return false;
        }

        if (isBlank(addr.getStreet()) ||
                isBlank(addr.getCity()) ||
                isBlank(addr.getPostalCode()) ||
                isBlank(addr.getCountry())) {

            invalidate("Street, city, postal code and country are mandatory");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {
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
