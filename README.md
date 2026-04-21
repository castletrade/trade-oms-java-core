# Castle Trade OMS Java Core

## Institutional Order Management System (OMS)

This repository contains the core Order Management System (OMS) for Castle Trade LLC, built using Java 21, Spring Boot 3.2+, and Reactive Programming (Project Reactor). The system is designed for high-throughput, low-latency institutional trade execution.

### Architectural Pillars

- **Java 21 Virtual Threads (Project Loom)**: Leveraged to handle massive concurrency without the overhead of carrier threads, ensuring the OMS remains responsive under peak load.
- **Reactive Stream Processing**: Full utilization of Spring WebFlux and R2DBC for non-blocking I/O operations across the entire stack.
- **Clean Architecture**: Domain-driven design (DDD) principles with clear separation of concerns through ports and adapters.

### Strategy & Concurrency

The OMS implements a decoupled execution strategy. Incoming orders are validated reactively and dispatched through a high-performance service layer that utilizes Virtual Threads for synchronous tasks where necessary, providing a hybrid model that maximizes hardware utilization.

### Build & Audit

The project is containerized using a multi-stage Docker build, ensuring a minimized attack surface and optimized runtime environment.

#### Local Build & Test
```bash
# Compile and run tests
mvn clean install

# Run the application
mvn spring-boot:run
```

#### Docker Deployment
```bash
# Build the production image
docker build -t castletrade-oms-core .

# Audit the container
docker run --rm castletrade-oms-core
```

---

### Intellectual Property Notice

This software and its documentation are the exclusive property of Castle Trade LLC. Unauthorized copying, distribution, or use of these materials constitutes a violation of intellectual property laws and contractual agreements. This code is provided for professional assessment purposes only.

© 2026 Castle Trade LLC. All rights reserved.
