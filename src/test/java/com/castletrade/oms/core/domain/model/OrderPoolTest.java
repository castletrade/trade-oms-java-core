package com.castletrade.oms.core.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderPoolTest {

    @Test
    void testBorrowAndReturn() {
        OrderPool pool = new OrderPool(10);
        assertEquals(10, pool.getAvailableCount());

        Order order = pool.borrowOrder();
        assertNotNull(order);
        assertEquals(9, pool.getAvailableCount());

        order.setSymbol("BTC/USD");
        pool.returnOrder(order);

        assertEquals(10, pool.getAvailableCount());
        
        Order reborrowed = pool.borrowOrder();
        assertNull(reborrowed.getSymbol(), "Order should be cleared when returned to pool");
    }

    @Test
    void testPoolExhaustion() {
        OrderPool pool = new OrderPool(1);
        pool.borrowOrder();
        
        Order exhausted = pool.borrowOrder();
        assertNotNull(exhausted, "Should create fresh object when pool is empty");
        assertEquals(0, pool.getAvailableCount());
    }
}
