package com.paynest.onion;

import com.paynest.onion.application.port.OrderRepository;
import com.paynest.onion.application.port.PaymentGateway;
import com.paynest.onion.application.service.CheckoutService;
import com.paynest.onion.domain.model.Order;
import com.paynest.onion.domain.model.Product;
import com.paynest.onion.domain.service.PricingService;
import com.paynest.onion.infrastructure.payment.FakePaymentGateway;
import com.paynest.onion.infrastructure.persistence.InMemoryOrderRepository;
import com.paynest.onion.web.CheckoutController;
import com.paynest.onion.web.dto.CheckoutRequest;
import com.paynest.onion.web.dto.CheckoutResponse;

/**
 * COMPOSITION ROOT - the one place that knows every layer.
 * Main builds the adapters, plugs them into the ports, and hands the
 * wired service to the web layer. Every other class stays in its own ring.
 */
public class Main {

    public static void main(String[] args) {
        // Infrastructure adapters (outer ring)
        OrderRepository orders = new InMemoryOrderRepository();
        PaymentGateway payments = new FakePaymentGateway();

        // Application service with ports injected (middle ring)
        CheckoutService checkoutService = new CheckoutService(orders, payments, new PricingService());

        // Web entry point (outer ring)
        CheckoutController controller = new CheckoutController(checkoutService);

        // Seed a demo order (domain objects only)
        Product laptop = new Product(1, "Laptop", 12000);
        Product mouse = new Product(2, "Mouse", 200);
        Order order = new Order(1, "John Smith");
        order.addItem(laptop, 1);
        order.addItem(mouse, 2);
        orders.save(order);

        // Simulate a web request
        CheckoutResponse response = controller.checkout(new CheckoutRequest(1));
        System.out.println(response.isSuccess() + " - " + response.getMessage());
    }
}
