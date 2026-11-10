package com.paynest.onion.domain.model;

/**
 * DOMAIN LAYER (inner ring) - one line of an order.
 * The subtotal rule lives here, in the domain, not in a controller.
 */
public class OrderItem {

    private final Product product;
    private final int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    /** Business rule: a line subtotal is price times quantity. */
    public double subtotal() {
        return product.getPrice() * quantity;
    }
}
