package io.github.bohdanzhuvak.onlinestore.order.application.ports;

import java.math.BigDecimal;
import java.util.List;

public interface CartService {
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
      BigDecimal unitPriceAmount,
      String unitPriceCurrency,
      int quantity
  ) {
  }
}
