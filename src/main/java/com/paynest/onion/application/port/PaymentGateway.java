package com.paynest.onion.application.port;

/**
 * APPLICATION LAYER - output port for charging money.
 * The checkout flow does not know which rail is used or how it talks
 * to a bank. Infrastructure provides the real (or fake) implementation.
 */
public interface PaymentGateway {

    /**
     * Charges the given amount.
     *
     * @param amount the amount to charge in Rand
     * @return true if the charge succeeded
     */
    boolean charge(double amount);
}
