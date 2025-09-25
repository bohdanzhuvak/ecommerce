package io.github.bohdanzhuvak.onlinestore.cart.application.port.in;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.Optional;

public interface WebCartOrchestrator {
  Optional<Cart> getCart(UserId userId);

  Cart addItemToCart(UserId userId, ProductId productId, int quantity);

  Cart updateCartItem(UserId userId, ProductId productId, int quantity);

  Cart removeItemFromCart(UserId userId, ProductId productId);

  Cart clearCart(UserId userId);
}
