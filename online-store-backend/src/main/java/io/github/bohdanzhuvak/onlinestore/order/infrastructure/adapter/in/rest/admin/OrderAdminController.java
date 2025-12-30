package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.in.rest.resource.PagedResponse;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.in.WebOrderOrchestrator;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.OrderAdminResponse;
import io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.in.rest.resource.UpdateOrderStatusAdminRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "orders-admin", description = "Order management operations for administrators")
@RestController
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {
  private final WebOrderOrchestrator webOrderOrchestrator;

  public OrderAdminController(WebOrderOrchestrator webOrderOrchestrator) {
    this.webOrderOrchestrator = webOrderOrchestrator;
  }

  @Operation(operationId = "getAllOrders", summary = "Get all orders", description = "Get all orders with optional status filter (admin only)")
  @GetMapping
  public ResponseEntity<PagedResponse<OrderAdminResponse>> getAllOrders(
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "20") int perPage) {
    try {
      OrderStatus orderStatus = status != null ? OrderStatus.fromString(status) : null;
      PageResult<Order> pageResult = webOrderOrchestrator.getAllOrders(orderStatus, page, perPage);
      PagedResponse<OrderAdminResponse> response = PagedResponse.from(pageResult, OrderAdminResponse::from);
      return ResponseEntity.ok(response);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "updateOrderStatus", summary = "Update order status", description = "Update the status of an order (admin only)")
  @PutMapping("/{orderId}/status")
  public ResponseEntity<OrderAdminResponse> updateOrderStatus(@PathVariable String orderId,
                                                              @RequestBody UpdateOrderStatusAdminRequest request) {
    try {
      Order order = webOrderOrchestrator.updateOrderStatus(
          OrderId.of(orderId),
          OrderStatus.fromString(request.status())
      );
      return ResponseEntity.ok(OrderAdminResponse.from(order));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
