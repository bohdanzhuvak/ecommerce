package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.cart.UpdateCartItemRequest;

public interface CartService {
  CartResponse getCart(Long userId);

  CartResponse addToCart(Long userId, AddToCartRequest request);

  CartResponse updateCart(Long userId, UpdateCartItemRequest request);

  void clearCart(Long userId);
}
