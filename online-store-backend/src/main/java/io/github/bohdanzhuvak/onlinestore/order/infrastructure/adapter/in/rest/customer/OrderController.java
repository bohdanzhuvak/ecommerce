package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.CreateOrderRequest;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Tag(name = "orders-customer", description = "Order management for customers")
@RestController
@RequestMapping("/api/v1/customer/orders")
public class OrderController {
  private final WebOrderOrchestrator webOrderOrchestrator;

  public OrderController(WebOrderOrchestrator webOrderOrchestrator) {
    this.webOrderOrchestrator = webOrderOrchestrator;
  }

  @Operation(operationId = "createOrder", summary = "Create order", description = "Create a new order from the user's cart")
  @PostMapping
  public ResponseEntity<OrderResponse> createOrder(@CurrentUserId String userId, @RequestBody CreateOrderRequest request) {
    try {
      Order order = webOrderOrchestrator.createOrder(
          UserId.of(userId),
          DeliveryAddressId.of(request.deliveryAddressId())
      );
      return ResponseEntity.ok(OrderResponse.from(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "getOrders", summary = "Get user orders", description = "Get all orders for the authenticated user")
  @GetMapping
  public ResponseEntity<List<OrderResponse>> getOrders(@CurrentUserId String userId) {
    List<Order> orders = webOrderOrchestrator.getOrders(UserId.of(userId));
    return ResponseEntity.ok(OrderResponse.from(orders));
  }

  @Operation(operationId = "getOrder", summary = "Get order by ID", description = "Get a specific order by its ID")
  @GetMapping("/{orderId}")
  public ResponseEntity<OrderResponse> getOrder(@PathVariable String orderId, @CurrentUserId String userId) {
    Optional<Order> order = webOrderOrchestrator.getOrder(OrderId.of(orderId), UserId.of(userId));
    return order.map(o -> ResponseEntity.ok(OrderResponse.from(o)))
        .orElse(ResponseEntity.notFound().build());
  }

  @Operation(operationId = "payOrder", summary = "Pay for order", description = "Process payment for an order using user balance")
  @PostMapping("/{orderId}/pay")
  public ResponseEntity<OrderResponse> payOrder(@PathVariable String orderId, @CurrentUserId String userId) {
    try {
      Order order = webOrderOrchestrator.payOrder(OrderId.of(orderId), UserId.of(userId));
      return ResponseEntity.ok(OrderResponse.from(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "cancelOrder", summary = "Cancel order", description = "Cancel an existing order")
  @PutMapping("/{orderId}/cancel")
  public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderId, @CurrentUserId String userId) {
    try {
      Order order = webOrderOrchestrator.cancelOrder(OrderId.of(orderId), UserId.of(userId));
      return ResponseEntity.ok(OrderResponse.from(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
