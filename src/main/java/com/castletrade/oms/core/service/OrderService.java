package com.castletrade.oms.core.service;

import com.castletrade.oms.core.domain.model.Order;
import com.castletrade.oms.core.port.in.SubmitOrderUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * Implementation of the Order submission use case using Reactive Streams
 * and Java 21 Virtual Threads for specialized processing.
 */
@Slf4j
@Service
public class OrderService implements SubmitOrderUseCase {

    // Virtual Thread executor for high-concurrency blocking tasks (if any)
    private final java.util.concurrent.ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public Mono<Order> submitOrder(Order order) {
        return Mono.just(order)
                .map(this::initializeOrder)
                .flatMap(this::validateOrder)
                .publishOn(Schedulers.fromExecutor(virtualThreadExecutor)) // Offloading to Virtual Threads
                .doOnNext(this::processExecution)
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Order initializeOrder(Order order) {
        order.setId(UUID.randomUUID().toString());
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        return order;
    }

    private Mono<Order> validateOrder(Order order) {
        // Implementation of institutional validation rules
        if (order.getQuantity().doubleValue() <= 0) {
            return Mono.error(new IllegalArgumentException("Invalid order quantity"));
        }
        return Mono.just(order);
    }

    private void processExecution(Order order) {
        // Simulating high-concurrency execution task on a Virtual Thread
        log.info("Processing order {} for symbol {} on thread {}", 
                order.getId(), order.getSymbol(), Thread.currentThread());
        order.setStatus(Order.OrderStatus.OPEN);
    }
}
