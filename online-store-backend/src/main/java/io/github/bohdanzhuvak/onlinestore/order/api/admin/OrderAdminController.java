package io.github.bohdanzhuvak.onlinestore.order.api.admin;

import io.github.bohdanzhuvak.onlinestore.order.application.OrderApplicationService;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
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
@RequestMapping("/api/admin/orders")
public class OrderAdminController {
  private final OrderApplicationService orderApplicationService;

  public OrderAdminController(OrderApplicationService orderApplicationService) {
    this.orderApplicationService = orderApplicationService;
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders(
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      OrderStatus orderStatus = status != null ? OrderStatus.fromString(status) : null;
      List<Order> orders = orderApplicationService.getAllOrders(orderStatus, offset, limit);
      return ResponseEntity.ok(orders);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{orderId}/status")
  public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderId,
                                                 @RequestBody UpdateOrderStatusRequest request) {
    try {
      Order order = orderApplicationService.updateOrderStatus(
          OrderId.of(orderId),
          OrderStatus.fromString(request.getStatus())
      );
      return ResponseEntity.ok(order);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  // DTO classes for requests
  public static class UpdateOrderStatusRequest {
    private String status;

    // Getters and setters
    public String getStatus() {
      return status;
    }

    public void setStatus(String status) {
      this.status = status;
    }
  }
}
