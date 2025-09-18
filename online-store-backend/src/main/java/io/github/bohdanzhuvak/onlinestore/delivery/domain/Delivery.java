package io.github.bohdanzhuvak.onlinestore.delivery.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Delivery {
  private final DeliveryId id;
  private final OrderId orderId;
  private final UserId userId;
  private final DeliveryAddress address;
  private final TrackingNumber trackingNumber;
  private final LocalDateTime createdAt;
  private DeliveryStatus status;
  private LocalDateTime updatedAt;
  private String notes;

  private Delivery(DeliveryId id, OrderId orderId, UserId userId, DeliveryAddress address,
                   TrackingNumber trackingNumber, DeliveryStatus status, LocalDateTime createdAt,
                   LocalDateTime updatedAt, String notes) {
    if (id == null) {
      throw new IllegalArgumentException("Delivery ID cannot be null");
    }
    if (orderId == null) {
      throw new IllegalArgumentException("Order ID cannot be null");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (address == null) {
      throw new IllegalArgumentException("Delivery address cannot be null");
    }
    if (trackingNumber == null) {
      throw new IllegalArgumentException("Tracking number cannot be null");
    }
    if (status == null) {
      throw new IllegalArgumentException("Delivery status cannot be null");
    }
    if (createdAt == null) {
      throw new IllegalArgumentException("Created at cannot be null");
    }
    if (updatedAt == null) {
      throw new IllegalArgumentException("Updated at cannot be null");
    }

    this.id = id;
    this.orderId = orderId;
    this.userId = userId;
    this.address = address;
    this.trackingNumber = trackingNumber;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.notes = notes;
  }

  public static Delivery create(OrderId orderId, UserId userId, DeliveryAddress address) {
    return new Delivery(
        DeliveryId.generate(),
        orderId,
        userId,
        address,
        TrackingNumber.generate(),
        DeliveryStatus.PENDING,
        LocalDateTime.now(),
        LocalDateTime.now(),
        null
    );
  }

  public static Delivery restore(DeliveryId id, OrderId orderId, UserId userId, DeliveryAddress address,
                                 TrackingNumber trackingNumber, DeliveryStatus status, LocalDateTime createdAt,
                                 LocalDateTime updatedAt, String notes) {
    return new Delivery(id, orderId, userId, address, trackingNumber, status, createdAt, updatedAt, notes);
  }

  // Business methods
  public void pickUp() {
    if (!status.canBePickedUp()) {
      throw new IllegalStateException("Delivery cannot be picked up in current status: " + status);
    }
    this.status = DeliveryStatus.PICKED_UP;
    this.updatedAt = LocalDateTime.now();
  }

  public void markInTransit() {
    if (!status.canBeInTransit()) {
      throw new IllegalStateException("Delivery cannot be marked in transit in current status: " + status);
    }
    this.status = DeliveryStatus.IN_TRANSIT;
    this.updatedAt = LocalDateTime.now();
  }

  public void markOutForDelivery() {
    if (!status.canBeOutForDelivery()) {
      throw new IllegalStateException("Delivery cannot be marked out for delivery in current status: " + status);
    }
    this.status = DeliveryStatus.OUT_FOR_DELIVERY;
    this.updatedAt = LocalDateTime.now();
  }

  public void deliver() {
    if (!status.canBeDelivered()) {
      throw new IllegalStateException("Delivery cannot be delivered in current status: " + status);
    }
    this.status = DeliveryStatus.DELIVERED;
    this.updatedAt = LocalDateTime.now();
  }

  public void markFailed(String reason) {
    if (!status.canBeFailed()) {
      throw new IllegalStateException("Delivery cannot be marked as failed in current status: " + status);
    }
    this.status = DeliveryStatus.FAILED;
    this.notes = reason;
    this.updatedAt = LocalDateTime.now();
  }

  public void markReturned(String reason) {
    if (!status.canBeReturned()) {
      throw new IllegalStateException("Delivery cannot be marked as returned in current status: " + status);
    }
    this.status = DeliveryStatus.RETURNED;
    this.notes = reason;
    this.updatedAt = LocalDateTime.now();
  }

  public void addNotes(String notes) {
    this.notes = notes;
    this.updatedAt = LocalDateTime.now();
  }

  public boolean belongsTo(UserId userId) {
    return this.userId.equals(userId);
  }

  public boolean isActive() {
    return status.isActive();
  }

  // Getters
  public DeliveryId getId() {
    return id;
  }

  public OrderId getOrderId() {
    return orderId;
  }

  public UserId getUserId() {
    return userId;
  }

  public DeliveryAddress getAddress() {
    return address;
  }

  public TrackingNumber getTrackingNumber() {
    return trackingNumber;
  }

  public DeliveryStatus getStatus() {
    return status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public String getNotes() {
    return notes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Delivery delivery = (Delivery) o;
    return Objects.equals(id, delivery.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Delivery{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", userId=" + userId +
        ", status=" + status +
        ", trackingNumber=" + trackingNumber +
        '}';
  }
}
