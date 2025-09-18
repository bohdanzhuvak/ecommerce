package io.github.bohdanzhuvak.onlinestore.delivery.domain;

public enum DeliveryStatus {
  PENDING("PENDING"),
  PICKED_UP("PICKED_UP"),
  IN_TRANSIT("IN_TRANSIT"),
  OUT_FOR_DELIVERY("OUT_FOR_DELIVERY"),
  DELIVERED("DELIVERED"),
  FAILED("FAILED"),
  RETURNED("RETURNED");

  private final String value;

  DeliveryStatus(String value) {
    this.value = value;
  }

  public static DeliveryStatus fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException("Delivery status cannot be null");
    }
    for (DeliveryStatus status : values()) {
      if (status.value.equalsIgnoreCase(value.trim())) {
        return status;
      }
    }
    throw new IllegalArgumentException("Invalid delivery status: " + value);
  }

  public String getValue() {
    return value;
  }

  public boolean isPending() {
    return this == PENDING;
  }

  public boolean isPickedUp() {
    return this == PICKED_UP;
  }

  public boolean isInTransit() {
    return this == IN_TRANSIT;
  }

  public boolean isOutForDelivery() {
    return this == OUT_FOR_DELIVERY;
  }

  public boolean isDelivered() {
    return this == DELIVERED;
  }

  public boolean isFailed() {
    return this == FAILED;
  }

  public boolean isReturned() {
    return this == RETURNED;
  }

  public boolean isActive() {
    return this != DELIVERED && this != FAILED && this != RETURNED;
  }

  public boolean canBePickedUp() {
    return this == PENDING;
  }

  public boolean canBeInTransit() {
    return this == PICKED_UP;
  }

  public boolean canBeOutForDelivery() {
    return this == IN_TRANSIT;
  }

  public boolean canBeDelivered() {
    return this == OUT_FOR_DELIVERY;
  }

  public boolean canBeFailed() {
    return this == OUT_FOR_DELIVERY || this == IN_TRANSIT;
  }

  public boolean canBeReturned() {
    return this == FAILED || this == OUT_FOR_DELIVERY;
  }

  @Override
  public String toString() {
    return value;
  }
}
