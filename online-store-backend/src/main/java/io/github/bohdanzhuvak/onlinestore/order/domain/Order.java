package io.github.bohdanzhuvak.onlinestore.order.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {
  private final OrderId id;
  private final UserId userId;
  private final List<OrderItem> items;
  private final Money totalPrice;
  private final OrderStatus status;
  private final String deliveryAddressId;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  // Constructor for creating a new order
  public Order(OrderId id, UserId userId, List<OrderItem> items, String deliveryAddressId) {
    if (id == null) {
      throw new IllegalArgumentException("Order ID cannot be null");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }
    if (items == null || items.isEmpty()) {
      throw new IllegalArgumentException("Order must have at least one item");
    }
    if (deliveryAddressId == null || deliveryAddressId.trim().isEmpty()) {
      throw new IllegalArgumentException("Delivery address ID cannot be null or empty");
    }

    this.id = id;
    this.userId = userId;
    this.items = new ArrayList<>(items);
    this.deliveryAddressId = deliveryAddressId.trim();
    this.status = OrderStatus.PENDING;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();

    // Calculate total price
    this.totalPrice = calculateTotalPrice();
  }

  // Constructor for restoring from database
  private Order(OrderId id, UserId userId, List<OrderItem> items, Money totalPrice,
                OrderStatus status, String deliveryAddressId,
                LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.items = new ArrayList<>(items);
    this.totalPrice = totalPrice;
    this.status = status;
    this.deliveryAddressId = deliveryAddressId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static Order restore(OrderId id, UserId userId, List<OrderItem> items, Money totalPrice,
                              OrderStatus status, String deliveryAddressId,
                              LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Order(id, userId, items, totalPrice, status, deliveryAddressId, createdAt, updatedAt);
  }

  // Business methods
  public void pay() {
    if (!status.canBePaid()) {
      throw new IllegalStateException("Order cannot be paid in current status: " + status);
    }
    // In a real implementation, this would update the status and set updatedAt
    // For now, we'll just validate the state
  }

  public void cancel() {
    if (!status.canBeCancelled()) {
      throw new IllegalStateException("Order cannot be cancelled in current status: " + status);
    }
    // In a real implementation, this would update the status and set updatedAt
    // For now, we'll just validate the state
  }

  public void ship() {
    if (!status.canBeShipped()) {
      throw new IllegalStateException("Order cannot be shipped in current status: " + status);
    }
    // In a real implementation, this would update the status and set updatedAt
    // For now, we'll just validate the state
  }

  public void deliver() {
    if (!status.canBeDelivered()) {
      throw new IllegalStateException("Order cannot be delivered in current status: " + status);
    }
    // In a real implementation, this would update the status and set updatedAt
    // For now, we'll just validate the state
  }

  private Money calculateTotalPrice() {
    return items.stream()
        .map(OrderItem::getTotalPrice)
        .reduce(Money.zero("USD"), Money::add);
  }

  public int getTotalItems() {
    return items.stream()
        .mapToInt(OrderItem::quantity)
        .sum();
  }

  public boolean belongsTo(UserId userId) {
    return this.userId.equals(userId);
  }

  // Getters
  public OrderId getId() {
    return id;
  }

  public UserId getUserId() {
    return userId;
  }

  public List<OrderItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public Money getTotalPrice() {
    return totalPrice;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public String getDeliveryAddressId() {
    return deliveryAddressId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return Objects.equals(id, order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Order{" +
        "id=" + id +
        ", userId=" + userId +
        ", totalPrice=" + totalPrice +
        ", status=" + status +
        ", itemsCount=" + items.size() +
        '}';
  }
}
