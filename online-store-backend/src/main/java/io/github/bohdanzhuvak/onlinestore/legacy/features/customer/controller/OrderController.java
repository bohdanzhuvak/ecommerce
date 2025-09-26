package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.OrderService;
import io.github.bohdanzhuvak.onlinestore.legacy.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  public List<OrderResponse> getOrders(@CurrentUser UserPrincipal user) {
    return orderService.getOrdersByUser(user.id());
  }

  @GetMapping("{id}")
  public OrderResponse getOrder(@PathVariable Long id, @CurrentUser UserPrincipal user) {
    return orderService.getOrderById(id, user.id());
  }

  @PostMapping
  public OrderResponse createOrderFromCart(@CurrentUser UserPrincipal user, @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(user.id(), request);
  }

  @PutMapping("{id}/cancel")
  public OrderResponse cancelOrder(@CurrentUser UserPrincipal user, @PathVariable Long id) {
    return orderService.cancelOrder(id, user.id());
  }

  @PostMapping("{id}/pay")
  public OrderResponse payOrder(@PathVariable Long id, @CurrentUser UserPrincipal user) {
    return orderService.payOrder(id, user.id());
  }
}
