package io.github.bohdanzhuvak.onlinestore.order.domain.events;

import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.time.LocalDateTime;

public class OrderCancelled {
  private final OrderId orderId;
  private final UserId userId;
  private final Money totalPrice;
  private final LocalDateTime occurredAt;

  public OrderCancelled(OrderId orderId, UserId userId, Money totalPrice) {
    this.orderId = orderId;
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.occurredAt = LocalDateTime.now();
  }

  public OrderId getOrderId() {
    return orderId;
  }

  public UserId getUserId() {
    return userId;
  }

  public Money getTotalPrice() {
    return totalPrice;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
