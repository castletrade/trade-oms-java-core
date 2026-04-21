package com.castletrade.oms.core.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing an institutional order.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String symbol;
    private AssetClass assetClass;
    private OrderSide side;
    private BigDecimal quantity;
    private BigDecimal price;
    private ExecutionType executionType;
    private OrderStatus status;
    private LocalDateTime timestamp;

    /**
     * Resets the order state for reuse in an object pool.
     */
    public void clear() {
        this.id = null;
        this.symbol = null;
        this.assetClass = null;
        this.side = null;
        this.quantity = null;
        this.price = null;
        this.executionType = null;
        this.status = null;
        this.timestamp = null;
    }

    public enum AssetClass {
        EQUITY, FX, CRYPTO, FIXED_INCOME
    }

    public enum OrderSide {
        BUY, SELL
    }

    public enum ExecutionType {
        MARKET, LIMIT, TWAP, VWAP
    }

    public enum OrderStatus {
        PENDING, OPEN, PARTIAL, FILLED, CANCELLED, REJECTED
    }
}
