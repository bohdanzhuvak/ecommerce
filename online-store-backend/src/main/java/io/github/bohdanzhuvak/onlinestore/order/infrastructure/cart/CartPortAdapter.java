package io.github.bohdanzhuvak.onlinestore.order.infrastructure.cart;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.CartPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartPortAdapter implements CartPort {
  @Override
  public List<CartItem> getCartItems(String userId) {
    return List.of();
  }

  @Override
  public void clearCart(String userId) {

  }
}
