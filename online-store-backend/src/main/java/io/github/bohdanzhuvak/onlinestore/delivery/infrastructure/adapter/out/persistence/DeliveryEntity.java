package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class DeliveryEntity {
  @Id
  private String id;

  @Column(nullable = false)
  private String orderId;

  @Column(nullable = false)
  private String userId;

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
  private String phoneNumber;

  @Column(nullable = false, unique = true)
  private String trackingNumber;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private DeliveryStatus status;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @Column(length = 1000)
  private String notes;

  // Default constructor for JPA
  protected DeliveryEntity() {
  }

  // Constructor for creating new entity
  public DeliveryEntity(String id, String orderId, String userId, String street, String city, String state,
                        String postalCode, String country, String recipientName, String phoneNumber,
                        String trackingNumber, DeliveryStatus status, LocalDateTime createdAt,
                        LocalDateTime updatedAt, String notes) {
    this.id = id;
    this.orderId = orderId;
    this.userId = userId;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postalCode = postalCode;
    this.country = country;
    this.recipientName = recipientName;
    this.phoneNumber = phoneNumber;
    this.trackingNumber = trackingNumber;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.notes = notes;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getRecipientName() {
    return recipientName;
  }

  public void setRecipientName(String recipientName) {
    this.recipientName = recipientName;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getTrackingNumber() {
    return trackingNumber;
  }

  public void setTrackingNumber(String trackingNumber) {
    this.trackingNumber = trackingNumber;
  }

  public DeliveryStatus getStatus() {
    return status;
  }

  public void setStatus(DeliveryStatus status) {
    this.status = status;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }
}
