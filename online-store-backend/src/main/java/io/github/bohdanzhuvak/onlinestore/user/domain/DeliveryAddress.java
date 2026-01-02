package io.github.bohdanzhuvak.onlinestore.user.domain;

import java.time.LocalDateTime;

public record DeliveryAddress(DeliveryAddressId id, String street, String city, String state, String postalCode,
                              String country, String recipientName, LocalDateTime createdAt) {
  public DeliveryAddress(DeliveryAddressId id, String street, String city, String state,
                         String postalCode, String country, String recipientName, LocalDateTime createdAt) {
    if (id == null || id.getValue().trim().isEmpty()) {
      throw new IllegalArgumentException("ID cannot be null or empty");
    }
    if (street == null || street.trim().isEmpty()) {
      throw new IllegalArgumentException("Street cannot be null or empty");
    }
    if (city == null || city.trim().isEmpty()) {
      throw new IllegalArgumentException("City cannot be null or empty");
    }
    if (state == null || state.trim().isEmpty()) {
      throw new IllegalArgumentException("State cannot be null or empty");
    }
    if (postalCode == null || postalCode.trim().isEmpty()) {
      throw new IllegalArgumentException("Postal code cannot be null or empty");
    }
    if (country == null || country.trim().isEmpty()) {
      throw new IllegalArgumentException("Country cannot be null or empty");
    }
    if (recipientName == null || recipientName.trim().isEmpty()) {
      throw new IllegalArgumentException("Recipient name cannot be null or empty");
    }

    this.id = id;
    this.street = street.trim();
    this.city = city.trim();
    this.state = state.trim();
    this.postalCode = postalCode.trim();
    this.country = country.trim();
    this.recipientName = recipientName.trim();
    this.createdAt = createdAt;
  }

  public static DeliveryAddress create(String street, String city, String state, String postalCode,
                                       String country, String recipientName) {
    return new DeliveryAddress(DeliveryAddressId.generate(), street, city, state, postalCode, country, recipientName, LocalDateTime.now());
  }

  public static DeliveryAddress restore(DeliveryAddressId id, String street, String city, String state, String postalCode,
                                        String country, String recipientName) {
    return new DeliveryAddress(id, street, city, state, postalCode, country, recipientName, null);
  }
}
