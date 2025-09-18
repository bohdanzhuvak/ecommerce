package io.github.bohdanzhuvak.onlinestore.order.domain;

import java.time.LocalDateTime;

public class OrderShipped {
  private final OrderId orderId;
  private final UserId userId;
  private final LocalDateTime occurredAt;

  public OrderShipped(OrderId orderId, UserId userId) {
    this.orderId = orderId;
    this.userId = userId;
    this.occurredAt = LocalDateTime.now();
  }

  public OrderId getOrderId() {
    return orderId;
  }

  public UserId getUserId() {
    return userId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
