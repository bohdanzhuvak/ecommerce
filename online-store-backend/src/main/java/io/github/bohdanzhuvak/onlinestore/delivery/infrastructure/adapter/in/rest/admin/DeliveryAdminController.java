package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource.DeliveryResponse;
import io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource.UpdateDeliveryStatusRequest;
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

import java.util.List;

@Tag(name = "delivery-admin", description = "Delivery management operations for administrators")
@RestController
@RequestMapping("/api/v1/admin/deliveries")
public class DeliveryAdminController {
  private final WebDeliveryOrchestrator webDeliveryOrchestrator;

  public DeliveryAdminController(WebDeliveryOrchestrator webDeliveryOrchestrator) {
    this.webDeliveryOrchestrator = webDeliveryOrchestrator;
  }

  @Operation(operationId = "getAllDeliveries", summary = "Get all deliveries", description = "Get all deliveries with optional filters (admin only)")
  @GetMapping
  public ResponseEntity<List<DeliveryResponse>> getAllDeliveries(
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserId userIdObj = userId != null ? UserId.of(userId) : null;
      DeliveryStatus statusObj = status != null ? DeliveryStatus.fromString(status) : null;

      List<Delivery> deliveries = webDeliveryOrchestrator.getAllDeliveries(
          userIdObj, statusObj, offset, limit);
      return ResponseEntity.ok(DeliveryResponse.from(deliveries));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @Operation(operationId = "updateDeliveryStatus", summary = "Update delivery status", description = "Update the status of a delivery (admin only)")
  @PutMapping("/{deliveryId}/status")
  public ResponseEntity<DeliveryResponse> updateDeliveryStatus(@PathVariable String deliveryId,
                                                       @RequestBody UpdateDeliveryStatusRequest request) {
    try {
      Delivery delivery = webDeliveryOrchestrator.updateDeliveryStatus(
          DeliveryId.of(deliveryId),
          DeliveryStatus.fromString(request.status()),
          request.notes()
      );
      return ResponseEntity.ok(DeliveryResponse.from(delivery));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
