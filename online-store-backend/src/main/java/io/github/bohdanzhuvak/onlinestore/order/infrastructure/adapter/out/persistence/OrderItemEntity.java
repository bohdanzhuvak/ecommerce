package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItemEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false)
  private String productName;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal unitPriceAmount;

  @Column(nullable = false, length = 3)
  private String unitPriceCurrency;

  @Column(nullable = false)
  private Integer quantity;

  // Default constructor for JPA
  protected OrderItemEntity() {
  }

  // Constructor for creating new entity
  public OrderItemEntity(OrderEntity order, String productId, String productName,
                         BigDecimal unitPriceAmount, String unitPriceCurrency, Integer quantity) {
    this.order = order;
    this.productId = productId;
    this.productName = productName;
    this.unitPriceAmount = unitPriceAmount;
    this.unitPriceCurrency = unitPriceCurrency;
    this.quantity = quantity;
  }

  // Getters and setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public OrderEntity getOrder() {
    return order;
  }

  public void setOrder(OrderEntity order) {
    this.order = order;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public BigDecimal getUnitPriceAmount() {
    return unitPriceAmount;
  }

  public void setUnitPriceAmount(BigDecimal unitPriceAmount) {
    this.unitPriceAmount = unitPriceAmount;
  }

  public String getUnitPriceCurrency() {
    return unitPriceCurrency;
  }

  public void setUnitPriceCurrency(String unitPriceCurrency) {
    this.unitPriceCurrency = unitPriceCurrency;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
