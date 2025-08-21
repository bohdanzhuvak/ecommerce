package io.github.bohdanzhuvak.onlinestore.user.controller;

import io.github.bohdanzhuvak.onlinestore.user.dto.order.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.user.service.OrderService;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.CurrentUser;
import io.github.bohdanzhuvak.onlinestore.common.auth.security.UserPrincipal;
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
    return orderService.getOrdersByUser(user.getId());
  }

  @GetMapping("{id}")
  public OrderResponse getOrder(@PathVariable Long id, @CurrentUser UserPrincipal user) {
    return orderService.getOrderById(id, user.getId());
  }

  @PostMapping
  public OrderResponse createOrderFromCart(@CurrentUser UserPrincipal user, @RequestBody CreateOrderRequest request) {
    return orderService.createOrder(user.getId(), request);
  }

  @PutMapping("{id}/cancel")
  public OrderResponse cancelOrder(@PathVariable Long id) {
    return orderService.cancelOrder(id);
  }

  @PostMapping("{id}/pay")
  public OrderResponse payOrder(@PathVariable Long id, @CurrentUser UserPrincipal user) {
    return orderService.payOrder(id, user.getId());
  }
}
