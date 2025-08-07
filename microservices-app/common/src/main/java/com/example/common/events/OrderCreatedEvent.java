package com.example.common.events;

import java.io.Serializable;
import java.util.UUID;

/**
 * Integration event raised when a new order is created.
 * Pattern: Integration Event (EDA).
 */
public class OrderCreatedEvent implements Serializable {
    private UUID orderId;
    private UUID productId;
    private int quantity;

    public OrderCreatedEvent() {}

    public OrderCreatedEvent(UUID orderId, UUID productId, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public UUID getProductId() { return productId; }
    public void setProductId(UUID productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}