package com.castletrade.oms.core.service;

import com.castletrade.oms.core.domain.model.Order;
import com.castletrade.oms.core.domain.model.OrderPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executors;

/**
 * Implementation of the Order submission use case using Reactive Streams,
 * Java 21 Virtual Threads, and an Object Pool for memory optimization.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService implements SubmitOrderUseCase {

    private final OrderPool orderPool;

    // Virtual Thread executor for high-concurrency blocking tasks
    private final java.util.concurrent.ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();

    @Override
    public Mono<Order> submitOrder(Order order) {
        // In a real high-throughput scenario, the 'order' passed here 
        // would be a DTO, and we would borrow from the pool here.
        log.debug("Entering order submission pipeline for symbol: {}", order.getSymbol());
        
        return Mono.just(order)
                .map(this::initializeOrder)
                .flatMap(this::validateOrder)
                .publishOn(Schedulers.fromExecutor(virtualThreadExecutor))
                .doOnNext(this::processExecution)
                .doFinally(signalType -> {
                    // Demonstrating the return to pool after the reactive stream completes/errors
                    log.debug("Returning order resources to pool. Signal: {}", signalType);
                    orderPool.returnOrder(order);
                })
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
