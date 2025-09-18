package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.time.LocalDateTime;

public class DeliveryStatusUpdated {
  private final DeliveryId deliveryId;
  private final OrderId orderId;
  private final UserId userId;
  private final DeliveryStatus oldStatus;
  private final DeliveryStatus newStatus;
  private final TrackingNumber trackingNumber;
  private final LocalDateTime occurredAt;

  public DeliveryStatusUpdated(DeliveryId deliveryId, OrderId orderId, UserId userId,
                               DeliveryStatus oldStatus, DeliveryStatus newStatus,
                               TrackingNumber trackingNumber) {
    this.deliveryId = deliveryId;
    this.orderId = orderId;
    this.userId = userId;
    this.oldStatus = oldStatus;
    this.newStatus = newStatus;
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

  public DeliveryStatus getOldStatus() {
    return oldStatus;
  }

  public DeliveryStatus getNewStatus() {
    return newStatus;
  }

  public TrackingNumber getTrackingNumber() {
    return trackingNumber;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
