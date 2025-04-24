package com.example.bookshop.validation;

import com.example.bookshop.entity.PaymentMethod;

public class CardValidator extends AbstractValidator {

    private final PaymentMethod pm;
    public CardValidator(PaymentMethod pm){ this.pm=pm; }

    @Override protected boolean basicChecks() {
        if (pm==null ||
            pm.getMaskedNumber()==null || pm.getMaskedNumber().isBlank() ||
            pm.getExpiryDate()==null   || pm.getExpiryDate().isBlank()) {
            invalidate("All card fields are mandatory");
            return false;
        }
        return true;
    }

    @Override protected boolean formatChecks() {
        if (!pm.getMaskedNumber().matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")){
            invalidate("Card number must be formatted xxxx-xxxx-xxxx-xxxx");
            return false;
        }
        if (!pm.getExpiryDate().matches("^(0[1-9]|1[0-2])/\\d{2}$")){
            invalidate("Expiry must be MM/YY");
            return false;
        }
        return true;
    }
}
