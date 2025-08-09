package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.user.dto.order.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@NoArgsConstructor
public class OrderController {

  @GetMapping
  public List<OrderResponse> getOrders() {
    throw new UnsupportedOperationException();
  }

  @PostMapping
  public OrderResponse createOrder(CreateOrderRequest request) {
    throw new UnsupportedOperationException();
  }
}
