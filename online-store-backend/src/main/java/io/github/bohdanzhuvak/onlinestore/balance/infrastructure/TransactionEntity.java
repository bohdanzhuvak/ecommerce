package io.github.bohdanzhuvak.onlinestore.balance.infrastructure;

import io.github.bohdanzhuvak.onlinestore.balance.domain.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "balance_transactions")
public class TransactionEntity {
  @Id
  private String id;

  @Column(nullable = false)
  private String userId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private TransactionType type;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal amount;

  @Column(nullable = false, length = 3)
  private String currency;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal balanceAfter;

  @Column(length = 500)
  private String description;

  @Column(name = "order_id")
  private String orderId;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  // Default constructor for JPA
  protected TransactionEntity() {
  }

  // Constructor for creating new entity
  public TransactionEntity(String id, String userId, TransactionType type, BigDecimal amount,
                           String currency, BigDecimal balanceAfter, String description,
                           String orderId, LocalDateTime createdAt) {
    this.id = id;
    this.userId = userId;
    this.type = type;
    this.amount = amount;
    this.currency = currency;
    this.balanceAfter = balanceAfter;
    this.description = description;
    this.orderId = orderId;
    this.createdAt = createdAt;
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

  public TransactionType getType() {
    return type;
  }

  public void setType(TransactionType type) {
    this.type = type;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public BigDecimal getBalanceAfter() {
    return balanceAfter;
  }

  public void setBalanceAfter(BigDecimal balanceAfter) {
    this.balanceAfter = balanceAfter;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
