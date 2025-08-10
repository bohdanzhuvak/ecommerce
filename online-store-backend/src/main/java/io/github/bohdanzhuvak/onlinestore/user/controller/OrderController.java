package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.user.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  private final Long mockUserId = 0L; // Mock user ID for demonstration purposes

  @GetMapping
  public List<OrderResponse> getOrders() {
    return orderService.getOrders();
  }

  @PostMapping
  public OrderResponse createOrderFromCart() {
    return orderService.createOrder(mockUserId);
  }

  @PutMapping("{id}/cancel")
  public OrderResponse cancelOrder(@PathVariable Long id) {
    return orderService.cancelOrder(id);
  }
}
