package com.paynest.onion.web.dto;

/**
 * WEB LAYER (outer ring) - what the outside world sends us.
 * A DTO is a plain data carrier: it keeps web shapes out of the domain.
 */
public class CheckoutRequest {

    private final long orderId;

    public CheckoutRequest(long orderId) {
        this.orderId = orderId;
    }

    public long getOrderId() {
        return orderId;
    }
}
