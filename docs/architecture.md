# Institutional OMS Architecture

## Conceptual Design

The `trade-oms-java-core` is architected as a cloud-native microservice following the **Hexagonal Architecture (Ports and Adapters)** pattern. This ensures that the core trading logic remains independent of external technologies such as databases, messaging queues, or UI frameworks.

### Reactive Execution Pipeline

1. **Inbound Port**: Orders enter via REST/WebSocket (WebFlux).
2. **Domain Layer**: The `Order` entity undergoes institutional validation.
3. **Execution Service**: The `OrderService` handles state transitions.
4. **Concurrency Model**:
   - **Non-blocking I/O**: For all external network/DB calls.
   - **Virtual Threads**: For processing-intensive or legacy-blocking components, allowing for linear scalability without thread-pool exhaustion.

### Component Diagram (Conceptual)
- **API Adapter** (Spring WebFlux) -> **SubmitOrder Port** -> **OrderService** (Domain Logic) -> **Repository Port** (R2DBC Adapter).

## Scalability and Resilience
- **Horizontally Scalable**: Stateless design allows for immediate scaling behind a load balancer.
- **Resilience**: Integrated circuit breakers and back-pressure handling via Project Reactor.
