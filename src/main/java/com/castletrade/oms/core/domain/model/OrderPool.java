package com.castletrade.oms.core.domain.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * High-performance object pool for Order objects.
 * Designed to minimize GC pressure in high-frequency trading scenarios.
 */
@Slf4j
@Component
public class OrderPool {
    private final BlockingQueue<Order> pool;
    private static final int DEFAULT_POOL_SIZE = 1000;

    public OrderPool() {
        this(DEFAULT_POOL_SIZE);
    }

    public OrderPool(int size) {
        this.pool = new ArrayBlockingQueue<>(size);
        for (int i = 0; i < size; i++) {
            pool.add(new Order());
        }
        log.info("Initialized OrderPool with {} pre-allocated objects", size);
    }

    /**
     * Borrows an order from the pool.
     */
    public Order borrowOrder() {
        Order order = pool.poll();
        if (order == null) {
            log.warn("OrderPool exhausted, creating new transient object. Consider increasing pool size.");
            return new Order();
        }
        return order;
    }

    /**
     * Returns an order to the pool after resetting its state.
     */
    public void returnOrder(Order order) {
        if (order == null) return;
        order.clear();
        boolean accepted = pool.offer(order);
        if (!accepted) {
            log.debug("OrderPool full, discarding order object.");
        }
    }

    /**
     * Returns the current number of available objects in the pool.
     */
    public int getAvailableCount() {
        return pool.size();
    }
}
