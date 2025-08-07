package com.example.orderservice.web;

import com.example.common.api.ApiResponse;
import com.example.orderservice.domain.Order;
import com.example.orderservice.service.OrderService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    record CreateOrderRequest(@NotNull UUID productId, @Min(1) int quantity) {}

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> create(@RequestBody CreateOrderRequest request) {
        Order order = orderService.createOrder(request.productId(), request.quantity());
        return ResponseEntity.ok(ApiResponse.ok(order));
    }
}