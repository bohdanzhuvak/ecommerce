package io.github.bohdanzhuvak.onlinestore.cart.domain;

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
}
