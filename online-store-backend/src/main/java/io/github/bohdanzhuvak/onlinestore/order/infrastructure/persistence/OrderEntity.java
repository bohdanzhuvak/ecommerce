package io.github.bohdanzhuvak.onlinestore.order.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
  @Id
  private String id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalPriceAmount;

  @Column(nullable = false, length = 3)
  private String totalPriceCurrency;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OrderStatus status;

  @Embedded
  private DeliveryAddressSnapshotEmbeddable deliveryAddressSnapshot;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItemEntity> items = new ArrayList<>();

  // Default constructor for JPA
  protected OrderEntity() {
  }

  // Constructor for creating new entity
  public OrderEntity(String id, String userId, BigDecimal totalPriceAmount, String totalPriceCurrency,
                     OrderStatus status, DeliveryAddressSnapshotEmbeddable deliveryAddressSnapshot,
                     LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.totalPriceAmount = totalPriceAmount;
    this.totalPriceCurrency = totalPriceCurrency;
    this.status = status;
    this.deliveryAddressSnapshot = deliveryAddressSnapshot;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters and setters
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public BigDecimal getTotalPriceAmount() {
    return totalPriceAmount;
  }

  public void setTotalPriceAmount(BigDecimal totalPriceAmount) {
    this.totalPriceAmount = totalPriceAmount;
  }

  public String getTotalPriceCurrency() {
    return totalPriceCurrency;
  }

  public void setTotalPriceCurrency(String totalPriceCurrency) {
    this.totalPriceCurrency = totalPriceCurrency;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public DeliveryAddressSnapshotEmbeddable getDeliveryAddressSnapshot() {
    return deliveryAddressSnapshot;
  }

  public void setDeliveryAddressSnapshot(DeliveryAddressSnapshotEmbeddable deliveryAddressSnapshot) {
    this.deliveryAddressSnapshot = deliveryAddressSnapshot;
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

  public List<OrderItemEntity> getItems() {
    return items;
  }

  public void setItems(List<OrderItemEntity> items) {
    this.items = items;
  }

  @Embeddable
  public class DeliveryAddressSnapshotEmbeddable {

    @Column(nullable = false)
    private String id;

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

    protected DeliveryAddressSnapshotEmbeddable() {
    }

    public DeliveryAddressSnapshotEmbeddable(String id, String street, String city,
                                             String state, String postalCode,
                                             String country, String recipientName) {
      this.id = id;
      this.street = street;
      this.city = city;
      this.state = state;
      this.postalCode = postalCode;
      this.country = country;
      this.recipientName = recipientName;
    }

    public String getId() {
      return id;
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
  }
}
