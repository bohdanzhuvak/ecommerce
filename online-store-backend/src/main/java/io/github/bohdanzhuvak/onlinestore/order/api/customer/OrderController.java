package io.github.bohdanzhuvak.onlinestore.order.api.customer;

import io.github.bohdanzhuvak.onlinestore.order.application.service.WebOrderOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customer/orders")
public class OrderController {
  private final WebOrderOrchestratorService webOrderOrchestratorService;

  public OrderController(WebOrderOrchestratorService webOrderOrchestratorService) {
    this.webOrderOrchestratorService = webOrderOrchestratorService;
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
    try {
      Order order = webOrderOrchestratorService.createOrder(
          UserId.of(request.getUserId()),
          request.getDeliveryAddressId()
      );
      return ResponseEntity.ok(order);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<Order>> getOrders(@RequestParam String userId) {
    List<Order> orders = webOrderOrchestratorService.getOrders(UserId.of(userId));
    return ResponseEntity.ok(orders);
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<Order> getOrder(@PathVariable String orderId, @RequestParam String userId) {
    Optional<Order> order = webOrderOrchestratorService.getOrder(OrderId.of(orderId), UserId.of(userId));
    return order.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("/{orderId}/pay")
  public ResponseEntity<Order> payOrder(@PathVariable String orderId, @RequestParam String userId) {
    try {
      Order order = webOrderOrchestratorService.payOrder(OrderId.of(orderId), UserId.of(userId));
      return ResponseEntity.ok(order);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<Order> cancelOrder(@PathVariable String orderId, @RequestParam String userId) {
    try {
      Order order = webOrderOrchestratorService.cancelOrder(OrderId.of(orderId), UserId.of(userId));
      return ResponseEntity.ok(order);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class CreateOrderRequest {
    private String userId;
    private String deliveryAddressId;

    // Getters and setters
    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getDeliveryAddressId() {
      return deliveryAddressId;
    }

    public void setDeliveryAddressId(String deliveryAddressId) {
      this.deliveryAddressId = deliveryAddressId;
    }
  }
}
