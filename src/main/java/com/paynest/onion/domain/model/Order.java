package com.paynest.onion.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DOMAIN LAYER (inner ring) - an order and its lifecycle.
 * Rules that are always true (an order starts unpaid, only a new
 * order can be paid) are enforced here, not in the web layer.
 */
public class Order {

    private final long id;
    private final String customerName;
    private final List<OrderItem> items = new ArrayList<>();
    private boolean paid;

    public Order(long id, String customerName) {
        this.id = id;
        this.customerName = customerName;
        this.paid = false;
    }

    public long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isPaid() {
        return paid;
    }

    public void addItem(Product product, int quantity) {
        if (paid) {
            throw new IllegalStateException("Cannot change a paid order");
        }
        items.add(new OrderItem(product, quantity));
    }

    /** Business rule: payment can only happen once. */
    public void markPaid() {
        if (paid) {
            throw new IllegalStateException("Order " + id + " is already paid");
        }
        paid = true;
    }
}
