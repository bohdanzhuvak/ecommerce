package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.persistence;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class CartEntity {
  @Id
  private String id;

  @Column(name = "user_id", nullable = false, unique = true)
  private String userId;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CartItemEntity> items = new ArrayList<>();

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  // Constructors
  public CartEntity() {
  }

  public CartEntity(String id, String userId, List<CartItemEntity> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
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

  public List<CartItemEntity> getItems() {
    return items;
  }

  public void setItems(List<CartItemEntity> items) {
    this.items = items != null ? new ArrayList<>(items) : new ArrayList<>();
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
}
