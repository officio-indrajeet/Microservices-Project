package com.example.orderservice.messaging;

import com.example.common.events.InventoryReservedEvent;
import com.example.orderservice.domain.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryEventsListener {
    private final OrderRepository orderRepository;

    public InventoryEventsListener(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "inventory.reserved", groupId = "order-service")
    @Transactional
    public void onInventoryReserved(InventoryReservedEvent event) {
        var order = orderRepository.findById(event.getOrderId()).orElseThrow();
        order.setStatus(event.isReserved() ? Order.Status.RESERVED : Order.Status.REJECTED);
        orderRepository.save(order);
    }
}