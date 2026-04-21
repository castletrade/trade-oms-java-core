package com.castletrade.oms.core.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Domain model representing an institutional order.
 */
@Data
@Builder
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
