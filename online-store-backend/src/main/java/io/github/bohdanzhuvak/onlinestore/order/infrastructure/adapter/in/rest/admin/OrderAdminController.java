package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.UpdateOrderStatusRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {
  private final WebOrderOrchestrator webOrderOrchestrator;

  public OrderAdminController(WebOrderOrchestrator webOrderOrchestrator) {
    this.webOrderOrchestrator = webOrderOrchestrator;
  }

  @GetMapping
  public ResponseEntity<List<OrderResponse>> getAllOrders(
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      OrderStatus orderStatus = status != null ? OrderStatus.fromString(status) : null;
      List<Order> orders = webOrderOrchestrator.getAllOrders(orderStatus, offset, limit);
      return ResponseEntity.ok(OrderResponse.from(orders));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{orderId}/status")
  public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String orderId,
                                                 @RequestBody UpdateOrderStatusRequest request) {
    try {
      Order order = webOrderOrchestrator.updateOrderStatus(
          OrderId.of(orderId),
          OrderStatus.fromString(request.status())
      );
      return ResponseEntity.ok(OrderResponse.from(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
