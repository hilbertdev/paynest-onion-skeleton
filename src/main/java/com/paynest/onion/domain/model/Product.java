package com.paynest.onion.domain.model;

/**
 * DOMAIN LAYER (inner ring) - a product in the catalogue.
 * Pure Java: no imports from web, application or infrastructure.
 * The domain must never know how it is stored or served.
 */
public class Product {

    private final long id;
    private final String name;
    private final double price;

    public Product(long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
