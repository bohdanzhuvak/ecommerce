package io.github.bohdanzhuvak.onlinestore.cart.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItem {
  private final ProductId productId;
  private final String productName;
  private final Money unitPrice;
  private int quantity;

  public CartItem(ProductId productId, String productName, Money unitPrice, int quantity) {
    if (productId == null) {
      throw new IllegalArgumentException("Product ID cannot be null");
    }
    if (productName == null || productName.trim().isEmpty()) {
      throw new IllegalArgumentException("Product name cannot be null or empty");
    }
    if (unitPrice == null) {
      throw new IllegalArgumentException("Unit price cannot be null");
    }
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }

    this.productId = productId;
    this.productName = productName.trim();
    this.unitPrice = unitPrice;
    this.quantity = quantity;
  }


  public void updateQuantity(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("Quantity must be positive");
    }
    this.quantity = quantity;
  }

  public void addQuantity(int additionalQuantity) {
    if (additionalQuantity <= 0) {
      throw new IllegalArgumentException("Additional quantity must be positive");
    }
    this.quantity += additionalQuantity;
  }

  public Money getTotalPrice() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }

  public ProductId getProductId() {
    return productId;
  }

  public String getProductName() {
    return productName;
  }

  public Money getUnitPrice() {
    return unitPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CartItem cartItem = (CartItem) o;
    return Objects.equals(productId, cartItem.productId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(productId);
  }

  @Override
  public String toString() {
    return "CartItem{" +
        "productId=" + productId +
        ", productName='" + productName + '\'' +
        ", unitPrice=" + unitPrice +
        ", quantity=" + quantity +
        '}';
  }
}
