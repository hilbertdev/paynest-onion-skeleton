package com.paynest.onion.infrastructure.persistence;

import com.paynest.onion.application.port.OrderRepository;
import com.paynest.onion.domain.model.Order;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * INFRASTRUCTURE LAYER (outer ring) - an adapter that fills the
 * OrderRepository port with an in-memory map.
 * Students can extend here: replace with an H2 or Postgres adapter
 * without touching the application or domain layers.
 */
public class InMemoryOrderRepository implements OrderRepository {

    private final Map<Long, Order> store = new HashMap<>();

    @Override
    public void save(Order order) {
        store.put(order.getId(), order);
    }

    @Override
    public Optional<Order> findById(long orderId) {
        return Optional.ofNullable(store.get(orderId));
    }
}
