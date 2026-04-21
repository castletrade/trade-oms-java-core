package com.castletrade.oms.core.port.in;

import com.castletrade.oms.core.domain.model.Order;
import reactor.core.publisher.Mono;

/**
 * Port in interface for order submission from external adapters.
 */
public interface SubmitOrderUseCase {
    
    /**
     * Authenticates and submits an institutional order for execution.
     * @param order The order domain model.
     * @return A Mono of the processed order.
     */
    Mono<Order> submitOrder(Order order);
}
