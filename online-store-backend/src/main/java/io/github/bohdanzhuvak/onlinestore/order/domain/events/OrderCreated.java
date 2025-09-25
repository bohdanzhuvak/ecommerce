package io.github.bohdanzhuvak.onlinestore.order.domain.events;

import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.time.LocalDateTime;

public class OrderCreated {
  private final OrderId orderId;
  private final UserId userId;
  private final Money totalPrice;
  private final String deliveryAddressId;
  private final LocalDateTime occurredAt;

  public OrderCreated(OrderId orderId, UserId userId, Money totalPrice, String deliveryAddressId) {
    this.orderId = orderId;
    this.userId = userId;
    this.totalPrice = totalPrice;
    this.deliveryAddressId = deliveryAddressId;
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

  public String getDeliveryAddressId() {
    return deliveryAddressId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
