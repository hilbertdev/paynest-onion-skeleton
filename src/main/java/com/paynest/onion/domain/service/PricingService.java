package com.paynest.onion.domain.service;

import com.paynest.onion.domain.model.Order;
import com.paynest.onion.domain.model.OrderItem;

/**
 * DOMAIN LAYER (inner ring) - pricing rules that belong to no single order.
 * Pure Java, no framework, easy to unit test.
 * Students can extend here: discounts, VAT, delivery fees.
 */
public class PricingService {

    /** Business rule: an order total is the sum of its line subtotals. */
    public double calculateTotal(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.subtotal();
        }
        return total;
    }
}
