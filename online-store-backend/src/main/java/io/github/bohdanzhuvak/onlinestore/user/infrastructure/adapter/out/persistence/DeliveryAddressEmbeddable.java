package io.github.bohdanzhuvak.onlinestore.user.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Embeddable
public class DeliveryAddressEmbeddable {
  @Column(nullable = false)
  private String addressId;

  @Column(nullable = false)
  private String street;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String state;

  @Column(nullable = false)
  private String postalCode;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String recipientName;

  @Column(nullable = false)
  private boolean isDefault;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  public DeliveryAddressEmbeddable() {
  }

  public DeliveryAddressEmbeddable(String addressId, String street, String city, String state, String postalCode, String country,
                                   String recipientName, LocalDateTime createdAt) {
    this.addressId = addressId;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.country = country;
    this.recipientName = recipientName;
    this.createdAt = createdAt;
  }

  public String getAddressId() {
    return addressId;
  }

  public String getStreet() {
    return street;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public String getCountry() {
    return country;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public boolean isDefault() {
    return isDefault;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }
}
