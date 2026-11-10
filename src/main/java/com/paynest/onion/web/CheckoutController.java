package com.paynest.onion.web;

import com.paynest.onion.application.service.CheckoutService;
import com.paynest.onion.web.dto.CheckoutRequest;
import com.paynest.onion.web.dto.CheckoutResponse;

/**
 * WEB LAYER (outer ring) - the entry point for HTTP-style calls.
 * Translates web requests into use-case calls and results into responses.
 * No business rules here: it delegates to the application layer.
 */
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    public CheckoutResponse checkout(CheckoutRequest request) {
        try {
            boolean success = checkoutService.checkout(request.getOrderId());
            if (success) {
                return new CheckoutResponse(true, "Order " + request.getOrderId() + " paid");
            }
            return new CheckoutResponse(false, "Payment failed for order " + request.getOrderId());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new CheckoutResponse(false, e.getMessage());
        }
    }
}
