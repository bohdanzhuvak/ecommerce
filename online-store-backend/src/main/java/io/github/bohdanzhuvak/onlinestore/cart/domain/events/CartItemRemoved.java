package io.github.bohdanzhuvak.onlinestore.cart.domain.events;

import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

import java.time.LocalDateTime;

public class CartItemRemoved {
  private final CartId cartId;
  private final UserId userId;
  private final ProductId productId;
  private final LocalDateTime occurredAt;

  public CartItemRemoved(CartId cartId, UserId userId, ProductId productId) {
    this.cartId = cartId;
    this.userId = userId;
    this.productId = productId;
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

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
