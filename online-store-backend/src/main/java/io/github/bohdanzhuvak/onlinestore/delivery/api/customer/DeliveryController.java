package io.github.bohdanzhuvak.onlinestore.delivery.api.customer;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.in.WebDeliveryOrchestrator;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
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
@RequestMapping("/api/customer/deliveries")
public class DeliveryController {
  private final WebDeliveryOrchestrator webDeliveryOrchestrator;

  public DeliveryController(WebDeliveryOrchestrator webDeliveryOrchestrator) {
    this.webDeliveryOrchestrator = webDeliveryOrchestrator;
  }

  @PostMapping
  public ResponseEntity<Delivery> createDelivery(@RequestBody CreateDeliveryRequest request) {
    try {
      DeliveryAddress address = new DeliveryAddress(
          request.getStreet(),
          request.getCity(),
          request.getState(),
          request.getPostalCode(),
          request.getCountry(),
          request.getRecipientName(),
          request.getPhoneNumber()
      );

      Delivery delivery = webDeliveryOrchestrator.createDelivery(
          OrderId.of(request.getOrderId()),
          UserId.of(request.getUserId()),
          address
      );
      return ResponseEntity.ok(delivery);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{deliveryId}")
  public ResponseEntity<Delivery> getDelivery(@PathVariable String deliveryId, @RequestParam String userId) {
    Optional<Delivery> delivery = webDeliveryOrchestrator.getDelivery(
        DeliveryId.of(deliveryId), UserId.of(userId));
    return delivery.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Delivery>> getDeliveries(@RequestParam String userId,
                                                      @RequestParam(defaultValue = "0") int offset,
                                                      @RequestParam(defaultValue = "20") int limit) {
    List<Delivery> deliveries = webDeliveryOrchestrator.getDeliveries(
        UserId.of(userId), offset, limit);
    return ResponseEntity.ok(deliveries);
  }

  @GetMapping("/track/{trackingNumber}")
  public ResponseEntity<Delivery> trackDelivery(@PathVariable String trackingNumber, @RequestParam String userId) {
    Optional<Delivery> delivery = webDeliveryOrchestrator.trackDelivery(
        TrackingNumber.of(trackingNumber), UserId.of(userId));
    return delivery.map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  // DTO classes for requests
  public static class CreateDeliveryRequest {
    private String orderId;
    private String userId;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String recipientName;
    private String phoneNumber;

    // Getters and setters
    public String getOrderId() {
      return orderId;
    }

    public void setOrderId(String orderId) {
      this.orderId = orderId;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getPostalCode() {
      return postalCode;
    }

    public void setPostalCode(String postalCode) {
      this.postalCode = postalCode;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getRecipientName() {
      return recipientName;
    }

    public void setRecipientName(String recipientName) {
      this.recipientName = recipientName;
    }

    public String getPhoneNumber() {
      return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
    }
  }
}
