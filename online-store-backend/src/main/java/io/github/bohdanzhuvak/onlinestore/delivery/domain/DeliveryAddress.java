package io.github.bohdanzhuvak.onlinestore.delivery.domain;

public record DeliveryAddress(String street, String city, String state, String postalCode, String country,
                              String recipientName, String phoneNumber) {
  public DeliveryAddress(String street, String city, String state, String postalCode,
                         String country, String recipientName, String phoneNumber) {
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
    if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
      throw new IllegalArgumentException("Phone number cannot be null or empty");
    }

    this.street = street.trim();
    this.city = city.trim();
    this.state = state.trim();
    this.postalCode = postalCode.trim();
    this.country = country.trim();
    this.recipientName = recipientName.trim();
    this.phoneNumber = phoneNumber.trim();
  }

  public String getFullAddress() {
    return String.format("%s, %s, %s %s, %s",
        street, city, state, postalCode, country);
  }

  @Override
  public String toString() {
    return "DeliveryAddress{" +
        "street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", postalCode='" + postalCode + '\'' +
        ", country='" + country + '\'' +
        ", recipientName='" + recipientName + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
