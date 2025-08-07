package com.example.orderservice.service;

import com.example.common.events.OrderCreatedEvent;
import com.example.orderservice.domain.Order;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderService(OrderRepository orderRepository, KafkaTemplate<String, Object> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public Order createOrder(UUID productId, int quantity) {
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        Order saved = orderRepository.save(order);
        kafkaTemplate.send("order.created", new OrderCreatedEvent(saved.getId(), productId, quantity));
        return saved;
    }
}