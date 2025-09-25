package io.github.bohdanzhuvak.onlinestore.order.application.ports.out;

import io.github.bohdanzhuvak.onlinestore.order.domain.Money;

import java.util.List;

public interface CartPort {
  /**
   * Gets cart items for a user
   *
   * @param userId the user ID
   * @return list of cart items
   */
  List<CartItem> getCartItems(String userId);

  /**
   * Clears the cart for a user
   *
   * @param userId the user ID
   */
  void clearCart(String userId);

  /**
   * Cart item information
   */
  record CartItem(
      String productId,
      String productName,
      Money unitPriceAmount,
      int quantity
  ) {
  }
}
