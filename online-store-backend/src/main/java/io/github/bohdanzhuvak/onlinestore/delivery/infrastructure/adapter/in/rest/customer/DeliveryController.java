package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.customer;

import io.github.bohdanzhuvak.onlinestore.architecture.CurrentUserId;
import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource.CreateDeliveryRequest;
import io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.in.rest.resource.DeliveryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer/deliveries")
public class DeliveryController {
  private final WebDeliveryOrchestrator webDeliveryOrchestrator;

  public DeliveryController(WebDeliveryOrchestrator webDeliveryOrchestrator) {
    this.webDeliveryOrchestrator = webDeliveryOrchestrator;
  }

  @PostMapping
  public ResponseEntity<DeliveryResponse> createDelivery(@CurrentUserId String userId, @RequestBody CreateDeliveryRequest request) {
    try {
      DeliveryAddress address = new DeliveryAddress(
          request.street(),
          request.city(),
          request.state(),
          request.postalCode(),
          request.country(),
          request.recipientName(),
          request.phoneNumber()
      );

      Delivery delivery = webDeliveryOrchestrator.createDelivery(
          OrderId.of(request.orderId()),
          UserId.of(userId),
          address
      );
      return ResponseEntity.ok(DeliveryResponse.from(delivery));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable String deliveryId, @CurrentUserId String userId) {
    Optional<Delivery> delivery = webDeliveryOrchestrator.getDelivery(
        DeliveryId.of(deliveryId), UserId.of(userId));
    return delivery.map(d -> ResponseEntity.ok(DeliveryResponse.from(d)))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<DeliveryResponse>> getDeliveries(@CurrentUserId String userId,
                                                      @RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "20") int limit) {
    List<Delivery> deliveries = webDeliveryOrchestrator.getDeliveries(
        UserId.of(userId), offset, limit);
    return ResponseEntity.ok(DeliveryResponse.from(deliveries));
  }

  @GetMapping("/track/{trackingNumber}")
  public ResponseEntity<DeliveryResponse> trackDelivery(@PathVariable String trackingNumber, @CurrentUserId String userId) {
    Optional<Delivery> delivery = webDeliveryOrchestrator.trackDelivery(
        TrackingNumber.of(trackingNumber), UserId.of(userId));
    return delivery.map(d -> ResponseEntity.ok(DeliveryResponse.from(d)))
        .orElse(ResponseEntity.notFound().build());
  }
}
