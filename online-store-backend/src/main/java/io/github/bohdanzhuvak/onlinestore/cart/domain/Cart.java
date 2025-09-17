package io.github.bohdanzhuvak.onlinestore.cart.domain;

import io.github.bohdanzhuvak.onlinestore.cart.application.ports.CatalogService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Cart {
  private final CartId id;
  private final UserId userId;
  private final List<CartItem> items;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  // Constructor for creating a new cart
  public Cart(CartId id, UserId userId) {
    if (id == null) {
      throw new IllegalArgumentException("Cart ID cannot be null");
    }
    if (userId == null) {
      throw new IllegalArgumentException("User ID cannot be null");
    }

    this.id = id;
    this.userId = userId;
    this.items = new ArrayList<>();
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  // Constructor for restoring from database
  private Cart(CartId id, UserId userId, List<CartItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.userId = userId;
    this.items = new ArrayList<>(items);
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Factory method for restoring from database
  public static Cart restore(CartId id, UserId userId, List<CartItem> items, LocalDateTime createdAt, LocalDateTime updatedAt) {
    return new Cart(id, userId, items != null ? items : new ArrayList<>(), createdAt, updatedAt);
  }

  // Business methods
  public void addItem(ProductId productId, CatalogService.ProductInfo productInfo, int quantity) {
    if (productId == null) {
      throw new IllegalArgumentException("Product ID cannot be null");
    }
    if (productInfo == null) {
      throw new IllegalArgumentException("Product info cannot be null");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }

    Optional<CartItem> existingItem = findItemByProductId(productId);
    if (existingItem.isPresent()) {
      existingItem.get().addQuantity(quantity);
    } else {
      // Convert primitive types to domain Value Objects
      Money unitPrice = productInfo.unitPrice();
      items.add(new CartItem(productId, productInfo.productName(), unitPrice, quantity));
    }
    this.updatedAt = LocalDateTime.now();
  }

  public void updateItemQuantity(ProductId productId, int quantity) {
    if (productId == null) {
      throw new IllegalArgumentException("Product ID cannot be null");
    }

    Optional<CartItem> existingItem = findItemByProductId(productId);
    if (existingItem.isPresent()) {
      if (quantity <= 0) {
        items.remove(existingItem.get());
      } else {
        existingItem.get().updateQuantity(quantity);
      }
      this.updatedAt = LocalDateTime.now();
    } else {
      throw new IllegalArgumentException("Product not found in cart");
    }
  }

  public void removeItem(ProductId productId) {
    if (productId == null) {
      throw new IllegalArgumentException("Product ID cannot be null");
    }

    boolean removed = items.removeIf(item -> item.getProductId().equals(productId));
    if (removed) {
      this.updatedAt = LocalDateTime.now();
    } else {
      throw new IllegalArgumentException("Product not found in cart");
    }
  }

  public void clear() {
    items.clear();
    this.updatedAt = LocalDateTime.now();
  }

  public Money getTotalPrice() {
    return items.stream()
        .map(CartItem::getTotalPrice)
        .reduce(Money.zero("USD"), Money::add);
  }

  public int getTotalItems() {
    return items.stream()
        .mapToInt(CartItem::getQuantity)
        .sum();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public boolean containsProduct(ProductId productId) {
    return findItemByProductId(productId).isPresent();
  }

  public Optional<CartItem> getItem(ProductId productId) {
    return findItemByProductId(productId);
  }

  private Optional<CartItem> findItemByProductId(ProductId productId) {
    return items.stream()
        .filter(item -> item.getProductId().equals(productId))
        .findFirst();
  }

  // Getters
  public CartId getId() {
    return id;
  }

  public UserId getUserId() {
    return userId;
  }

  public List<CartItem> getItems() {
    return Collections.unmodifiableList(items);
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Cart cart = (Cart) o;
    return Objects.equals(id, cart.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Cart{" +
        "id=" + id +
        ", userId=" + userId +
        ", itemsCount=" + items.size() +
        ", totalPrice=" + getTotalPrice() +
        '}';
  }
}
