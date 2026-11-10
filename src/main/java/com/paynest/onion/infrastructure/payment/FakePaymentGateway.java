package com.paynest.onion.infrastructure.payment;

import com.paynest.onion.application.port.PaymentGateway;

/**
 * INFRASTRUCTURE LAYER (outer ring) - a fake payment adapter.
 * Always succeeds so the skeleton runs end to end.
 * Students can extend here: wire in the real card, EFT or wallet rails.
 */
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public boolean charge(double amount) {
        System.out.println("FakePaymentGateway: charged R" + String.format("%.0f", amount));
        return true;
    }
}
