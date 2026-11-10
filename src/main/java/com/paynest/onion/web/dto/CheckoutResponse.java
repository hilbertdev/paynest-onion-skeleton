package com.paynest.onion.web.dto;

/**
 * WEB LAYER (outer ring) - what we send back to the outside world.
 * The domain Order never leaks into a web response directly.
 */
public class CheckoutResponse {

    private final boolean success;
    private final String message;

    public CheckoutResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
