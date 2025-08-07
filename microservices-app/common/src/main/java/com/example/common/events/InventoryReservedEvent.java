package com.example.common.events;

import java.io.Serializable;
import java.util.UUID;

/**
 * Integration event notifying reservation of inventory.
 */
public class InventoryReservedEvent implements Serializable {
    private UUID orderId;
    private boolean reserved;
    private String reason;

    public InventoryReservedEvent() {}

    public InventoryReservedEvent(UUID orderId, boolean reserved, String reason) {
        this.orderId = orderId;
        this.reserved = reserved;
        this.reason = reason;
    }

    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }

    public boolean isReserved() { return reserved; }
    public void setReserved(boolean reserved) { this.reserved = reserved; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}