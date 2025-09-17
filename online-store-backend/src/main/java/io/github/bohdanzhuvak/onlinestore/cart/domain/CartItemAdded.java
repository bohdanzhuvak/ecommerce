package io.github.bohdanzhuvak.onlinestore.cart.domain;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

import java.time.LocalDateTime;

public class CartItemAdded {
  private final CartId cartId;
  private final UserId userId;
  private final ProductId productId;
  private final String productName;
  private final Money unitPrice;
  private final int quantity;
  private final LocalDateTime occurredAt;

  public CartItemAdded(CartId cartId, UserId userId, ProductId productId, String productName,
                       Money unitPrice, int quantity) {
    this.cartId = cartId;
    this.userId = userId;
    this.productId = productId;
    this.productName = productName;
    this.unitPrice = unitPrice;
    this.quantity = quantity;
    this.occurredAt = LocalDateTime.now();
  }

  public CartId getCartId() {
    return cartId;
  }

  public UserId getUserId() {
    return userId;
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

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
