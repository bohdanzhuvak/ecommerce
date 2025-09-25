package io.github.bohdanzhuvak.onlinestore.cart.application.port.out;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.List;
import java.util.Optional;

public interface CartRepository {
  // Basic methods
  Cart save(Cart cart);

  Optional<Cart> findById(CartId cartId);

  Optional<Cart> findByUserId(UserId userId);

  void delete(CartId cartId);

  // Existence checks
  boolean existsById(CartId cartId);

  boolean existsByUserId(UserId userId);

  List<CartItem> getCartItems(UserId userId);
}
