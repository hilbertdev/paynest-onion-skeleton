package com.paynest.onion.application;

import com.paynest.onion.application.port.OrderRepository;
import com.paynest.onion.application.port.PaymentGateway;
import com.paynest.onion.application.service.CheckoutService;
import com.paynest.onion.domain.model.Order;
import com.paynest.onion.domain.model.Product;
import com.paynest.onion.domain.service.PricingService;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for the checkout use case.
 * Ports are replaced with hand-written fakes, so the test runs without
 * any database or payment provider - that is the point of ports.
 */
class CheckoutServiceTest {

    /** Fake repository adapter, just for tests. */
    static class FakeOrderRepository implements OrderRepository {
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

    /** Fake payment adapter that can be told to fail. */
    static class FakePaymentGateway implements PaymentGateway {
        private final boolean succeeds;

        FakePaymentGateway(boolean succeeds) {
            this.succeeds = succeeds;
        }

        @Override
        public boolean charge(double amount) {
            return succeeds;
        }
    }

    private Order sampleOrder() {
        Order order = new Order(1, "John Smith");
        order.addItem(new Product(1, "Laptop", 12000), 1);
        order.addItem(new Product(2, "Mouse", 200), 2);
        return order; // total: R12400
    }

    @Test
    void checkoutChargesTotalAndMarksOrderPaid() {
        FakeOrderRepository orders = new FakeOrderRepository();
        orders.save(sampleOrder());
        CheckoutService service = new CheckoutService(orders, new FakePaymentGateway(true), new PricingService());

        assertTrue(service.checkout(1));
        assertTrue(orders.findById(1).orElseThrow().isPaid());
    }

    @Test
    void failedPaymentLeavesOrderUnpaid() {
        FakeOrderRepository orders = new FakeOrderRepository();
        orders.save(sampleOrder());
        CheckoutService service = new CheckoutService(orders, new FakePaymentGateway(false), new PricingService());

        assertFalse(service.checkout(1));
        assertFalse(orders.findById(1).orElseThrow().isPaid());
    }

    @Test
    void unknownOrderIsRejected() {
        FakeOrderRepository orders = new FakeOrderRepository();
        CheckoutService service = new CheckoutService(orders, new FakePaymentGateway(true), new PricingService());

        assertThrows(IllegalArgumentException.class, () -> service.checkout(99));
    }

    @Test
    void pricingSumsLineSubtotals() {
        PricingService pricing = new PricingService();
        assertEquals(12400, pricing.calculateTotal(sampleOrder()));
    }
}
