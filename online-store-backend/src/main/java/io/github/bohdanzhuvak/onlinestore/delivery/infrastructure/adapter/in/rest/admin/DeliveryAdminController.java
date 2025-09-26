package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.admin;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource.UpdateDeliveryStatusRequest;
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
@RequestMapping("/api/admin/deliveries")
public class DeliveryAdminController {
  private final WebDeliveryOrchestrator webDeliveryOrchestrator;

  public DeliveryAdminController(WebDeliveryOrchestrator webDeliveryOrchestrator) {
    this.webDeliveryOrchestrator = webDeliveryOrchestrator;
  }

  @GetMapping
  public ResponseEntity<List<Delivery>> getAllDeliveries(
      @RequestParam(required = false) String userId,
      @RequestParam(required = false) String status,
      @RequestParam(defaultValue = "0") int offset,
      @RequestParam(defaultValue = "20") int limit) {
    try {
      UserId userIdObj = userId != null ? UserId.of(userId) : null;
      DeliveryStatus statusObj = status != null ? DeliveryStatus.fromString(status) : null;

      List<Delivery> deliveries = webDeliveryOrchestrator.getAllDeliveries(
          userIdObj, statusObj, offset, limit);
      return ResponseEntity.ok(deliveries);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @PutMapping("/{deliveryId}/status")
  public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable String deliveryId,
                                                       @RequestBody UpdateDeliveryStatusRequest request) {
    try {
      Delivery delivery = webDeliveryOrchestrator.updateDeliveryStatus(
          DeliveryId.of(deliveryId),
          DeliveryStatus.fromString(request.status()),
          request.notes()
      );
      return ResponseEntity.ok(delivery);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }
}
