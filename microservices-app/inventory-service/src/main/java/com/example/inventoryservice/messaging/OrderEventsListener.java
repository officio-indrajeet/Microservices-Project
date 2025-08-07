package com.example.inventoryservice.messaging;

import com.example.common.events.InventoryReservedEvent;
import com.example.common.events.OrderCreatedEvent;
import com.example.inventoryservice.repository.InventoryItemRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OrderEventsListener {
    private final InventoryItemRepository inventoryItemRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventsListener(InventoryItemRepository inventoryItemRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "order.created", groupId = "inventory-service")
    @Transactional
    public void onOrderCreated(OrderCreatedEvent event) {
        var item = inventoryItemRepository.findByProductId(event.getProductId());
        boolean reserved = item.isPresent() && item.get().getAvailableQuantity() >= event.getQuantity();
        String reason = reserved ? null : "Insufficient inventory";
        kafkaTemplate.send("inventory.reserved", new InventoryReservedEvent(event.getOrderId(), reserved, reason));
    }
}