package io.github.bohdanzhuvak.onlinestore.order.domain;

public enum OrderStatus {
  PENDING("PENDING"),
  PAID("PAID"),
  SHIPPED("SHIPPED"),
  DELIVERED("DELIVERED"),
  CANCELLED("CANCELLED");

  private final String value;

  OrderStatus(String value) {
    this.value = value;
  }

  public static OrderStatus fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Order status cannot be null");
    }
    for (OrderStatus status : values()) {
      if (status.value.equalsIgnoreCase(value.trim())) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid order status: " + value);
  }

  public String getValue() {
    return value;
  }

  public boolean isPending() {
    return this == PENDING;
  }

  public boolean isPaid() {
    return this == PAID;
  }

  public boolean isShipped() {
    return this == SHIPPED;
  }

  public boolean isDelivered() {
    return this == DELIVERED;
  }

  public boolean isCancelled() {
    return this == CANCELLED;
  }

  public boolean canBeCancelled() {
    return this == PENDING;
  }

  public boolean canBePaid() {
    return this == PENDING;
  }

  public boolean canBeShipped() {
    return this == PAID;
  }

  public boolean canBeDelivered() {
    return this == SHIPPED;
  }

  @Override
  public String toString() {
    return value;
  }
}
