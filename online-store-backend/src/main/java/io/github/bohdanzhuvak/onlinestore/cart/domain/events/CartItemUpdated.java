package io.github.bohdanzhuvak.onlinestore.cart.domain.events;

import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

import java.time.LocalDateTime;

public class CartItemUpdated {
  private final CartId cartId;
  private final UserId userId;
  private final ProductId productId;
  private final int newQuantity;
  private final LocalDateTime occurredAt;

  public CartItemUpdated(CartId cartId, UserId userId, ProductId productId, int newQuantity) {
    this.cartId = cartId;
    this.userId = userId;
    this.productId = productId;
    this.newQuantity = newQuantity;
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

  public int getNewQuantity() {
    return newQuantity;
  }

  public LocalDateTime getOccurredAt() {
    return occurredAt;
  }
}
