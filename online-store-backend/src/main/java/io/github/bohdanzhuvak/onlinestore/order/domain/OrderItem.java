package io.github.bohdanzhuvak.onlinestore.order.domain;

import java.math.BigDecimal;

public record OrderItem(ProductId productId, String productName, Money unitPrice, int quantity) {
  public OrderItem(ProductId productId, String productName, Money unitPrice, int quantity) {
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

  public Money getTotalPrice() {
    return unitPrice.multiply(BigDecimal.valueOf(quantity));
  }

  @Override
  public String toString() {
    return "OrderItem{" +
        "productId=" + productId +
        ", productName='" + productName + '\'' +
        ", unitPrice=" + unitPrice +
        ", quantity=" + quantity +
        '}';
  }
}
