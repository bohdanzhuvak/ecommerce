package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.user.dto.cart.AddToCartRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.CartResponse;
import io.github.bohdanzhuvak.onlinestore.user.dto.cart.UpdateCartItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {

  public CartResponse addToCart(Long userId, AddToCartRequest request) {
    throw new UnsupportedOperationException();
  }

  public CartResponse updateCart(Long userId, UpdateCartItemRequest request) {
    throw new UnsupportedOperationException();
  }

  public void clearCart(Long userId) {
    throw new UnsupportedOperationException();
  }
}
