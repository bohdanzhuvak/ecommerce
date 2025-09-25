package io.github.bohdanzhuvak.onlinestore.order.domain.events;

import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.time.LocalDateTime;

public class OrderDelivered {
  private final OrderId orderId;
  private final UserId userId;
  private final LocalDateTime occurredAt;

  public OrderDelivered(OrderId orderId, UserId userId) {
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
