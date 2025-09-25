package io.github.bohdanzhuvak.onlinestore.order.application.ports.out;

import io.github.bohdanzhuvak.onlinestore.order.domain.CartItemSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;

import java.util.List;

public interface CartPort {
  /**
   * Gets cart items for a user
   *
   * @param userId the user ID
   * @return list of cart items
   */
  List<CartItemSnapshot> getCartItems(UserId userId);

  /**
   * Clears the cart for a user
   *
   * @param userId the user ID
   */
  void clearCart(UserId userId);
}
