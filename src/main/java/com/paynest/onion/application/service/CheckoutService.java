package com.paynest.onion.application.service;

import com.paynest.onion.application.port.OrderRepository;
import com.paynest.onion.application.port.PaymentGateway;
import com.paynest.onion.domain.model.Order;
import com.paynest.onion.domain.service.PricingService;

/**
 * APPLICATION LAYER (middle ring) - the checkout use case.
 * Orchestrates the flow: load order, price it, charge it, mark paid.
 * Depends only on domain objects and ports - never on web or database code.
 * The ports are injected through the constructor (dependency inversion).
 */
public class CheckoutService {

    private final OrderRepository orders;
    private final PaymentGateway payments;
    private final PricingService pricing;

    public CheckoutService(OrderRepository orders, PaymentGateway payments, PricingService pricing) {
        this.orders = orders;
        this.payments = payments;
        this.pricing = pricing;
    }

    /**
     * Runs checkout for an existing order.
     *
     * @param orderId the order to check out
     * @return true if payment succeeded and the order was marked paid
     * @throws IllegalArgumentException if the order does not exist
     */
    public boolean checkout(long orderId) {
        Order order = orders.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Unknown order " + orderId));

        double total = pricing.calculateTotal(order);

        if (!payments.charge(total)) {
            return false;
        }

        order.markPaid();
        orders.save(order);
        return true;
    }
}
