package io.github.bohdanzhuvak.onlinestore.cart.application.port.in;

import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.List;

public interface ExternalCartOrchestrator {
  List<CartItem> getCartItems(UserId userId);

  void clearCart(UserId userId);
}
