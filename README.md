# PayNest Onion Architecture Skeleton

A small, runnable reference for organising your PayNest capstone repo with
**onion architecture**. Keep this repo open next to your own project and mirror
its structure.

## The one rule

**Dependencies point inward only.**

```
+-------------------------------+
|  WEB (controllers, DTOs)      |  knows the application layer
|  INFRASTRUCTURE (db, payment) |  knows the application ports
+-------------------------------+
|  APPLICATION (use cases,      |  knows the domain only
|  ports)                       |
+-------------------------------+
|  DOMAIN (models, rules)       |  knows nothing outside itself
+-------------------------------+
```

If your domain imports anything from web or infrastructure, the rings are
broken. Main (the composition root) is the only class allowed to know
every layer.

## The layers in this repo

| Layer | Package | What lives here |
|---|---|---|
| Domain | `domain.model`, `domain.service` | `Order`, `OrderItem`, `Product`, `PricingService` - business rules, pure Java |
| Application | `application.port`, `application.service` | `CheckoutService` (the use case), `OrderRepository` and `PaymentGateway` (ports = holes the application needs filled) |
| Infrastructure | `infrastructure.persistence`, `infrastructure.payment` | Adapters that fill the ports: `InMemoryOrderRepository`, `FakePaymentGateway` |
| Web | `web`, `web.dto` | `CheckoutController`, request/response DTOs - no business logic |
| Composition root | `Main` | Wires adapters to ports and starts the app |

## Why ports?

`CheckoutService` never says "use H2" or "use card". It says "I need to save
an order" (`OrderRepository`) and "I need to charge money" (`PaymentGateway`).
That is dependency inversion: the application owns the interfaces, the outer
rings implement them. Swap H2 for Postgres, or the fake gateway for a real
rail, without touching the use case or the domain.

## Run it

```bash
mvn test        # run the unit tests (no database needed - ports are faked)
mvn compile exec:java   # run the demo checkout
```

Requires Java 21 and Maven.

## Using this for your capstone

1. Copy the package layout into your PayNest repo.
2. Move your business rules into `domain` (totals, order lifecycle, pricing).
3. Put each flow (checkout, refunds, routing) into an `application.service`
   class that depends only on ports.
4. Keep controllers thin and adapters replaceable.
5. Watch the imports: if an inner ring imports an outer ring, fix the
   direction - that is the most common review comment.
