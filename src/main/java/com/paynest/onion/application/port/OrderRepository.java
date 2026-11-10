package com.paynest.onion.application.port;

import com.paynest.onion.domain.model.Order;

import java.util.Optional;

/**
 * APPLICATION LAYER - output port (a "hole" the application needs filled).
 * The application says WHAT it needs (save, find); it does not say HOW.
 * Infrastructure implements this interface (H2 today, Postgres later).
 * Dependencies point inward: the port lives here, the database lives outside.
 */
public interface OrderRepository {

    void save(Order order);

    Optional<Order> findById(long orderId);
}
