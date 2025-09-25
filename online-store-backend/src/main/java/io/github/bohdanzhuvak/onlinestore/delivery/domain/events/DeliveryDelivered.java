package io.github.bohdanzhuvak.onlinestore.delivery.domain.events;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;

import java.time.LocalDateTime;

public class DeliveryDelivered {
  private final DeliveryId deliveryId;
  private final OrderId orderId;
  private final UserId userId;
  private final TrackingNumber trackingNumber;
  private final LocalDateTime occurredAt;

  public DeliveryDelivered(DeliveryId deliveryId, OrderId orderId, UserId userId, TrackingNumber trackingNumber) {
    this.deliveryId = deliveryId;
    this.orderId = orderId;
    this.userId = userId;
    this.trackingNumber = trackingNumber;
    this.occurredAt = LocalDateTime.now();
  }

  public DeliveryId getDeliveryId() {
    return deliveryId;
  }

  public OrderId getOrderId() {
    return orderId;
  }

  public UserId getUserId() {
    return userId;
  }

  public TrackingNumber getTrackingNumber() {
    return trackingNumber;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
