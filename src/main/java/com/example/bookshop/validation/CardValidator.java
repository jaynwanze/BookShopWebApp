package com.example.bookshop.validation;

import org.springframework.stereotype.Component;
import com.example.bookshop.entity.PaymentMethod;

@Component
public class CardValidator extends AbstractValidator {

    private PaymentMethod paymentMethod;

    public void initialize(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    protected boolean basicChecks() {
        if (paymentMethod == null ||
            paymentMethod.getMaskedNumber() == null || paymentMethod.getMaskedNumber().isBlank() ||
            paymentMethod.getExpiryDate() == null || paymentMethod.getExpiryDate().isBlank()) {
            invalidate("All card fields are mandatory.");
            return false;
        }
        return true;
    }

    @Override
    protected boolean formatChecks() {
        if (!paymentMethod.getMaskedNumber().matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) {
            invalidate("Card number must be formatted xxxx-xxxx-xxxx-xxxx.");
            return false;
        }
        if (!paymentMethod.getExpiryDate().matches("^(0[1-9]|1[0-2])/\\d{2}$")) {
            invalidate("Expiry must be in MM/YY format.");
            return false;
        }
        return true;
    }
}