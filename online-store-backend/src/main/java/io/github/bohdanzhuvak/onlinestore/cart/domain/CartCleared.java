package io.github.bohdanzhuvak.onlinestore.cart.domain;

import java.time.LocalDateTime;

public class CartCleared {
  private final CartId cartId;
  private final UserId userId;
  private final LocalDateTime occurredAt;

  public CartCleared(CartId cartId, UserId userId) {
    this.cartId = cartId;
    this.userId = userId;
    this.occurredAt = LocalDateTime.now();
  }

  public CartId getCartId() {
    return cartId;
  }

  public UserId getUserId() {
    return userId;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
