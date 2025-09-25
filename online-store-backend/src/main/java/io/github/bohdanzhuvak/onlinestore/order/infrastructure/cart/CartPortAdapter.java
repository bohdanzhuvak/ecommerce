package io.github.bohdanzhuvak.onlinestore.order.infrastructure.cart;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.ExternalCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.CartPort;
import io.github.bohdanzhuvak.onlinestore.order.domain.CartItemSnapshot;
import io.github.bohdanzhuvak.onlinestore.order.domain.Money;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartPortAdapter implements CartPort {
  private final ExternalCartOrchestrator externalCartOrchestrator;

  public CartPortAdapter(ExternalCartOrchestrator externalCartOrchestrator) {
    this.externalCartOrchestrator = externalCartOrchestrator;
  }

  @Override
  public List<CartItemSnapshot> getCartItems(UserId userId) {
    return toCartItemSnapshotList(externalCartOrchestrator.getCartItems(toCartUserId(userId)));
  }

  @Override
  public void clearCart(UserId userId) {
    externalCartOrchestrator.clearCart(toCartUserId(userId));
  }

  private List<CartItemSnapshot> toCartItemSnapshotList(List<CartItem> cartItems) {
    return cartItems.stream()
        .map(this::toCartItemSnapshot)
        .toList();
  }

  private CartItemSnapshot toCartItemSnapshot(CartItem cartItem) {
    return CartItemSnapshot.of(
        cartItem.getProductId().getValue(),
        cartItem.getProductName(),
        cartItem.getQuantity(),
        toMoney(cartItem.getUnitPrice())
    );
  }

  private Money toMoney(io.github.bohdanzhuvak.onlinestore.cart.domain.Money unitPrice) {
    return Money.of(unitPrice.getAmount(), unitPrice.getCurrency());
  }

  private io.github.bohdanzhuvak.onlinestore.cart.domain.UserId toCartUserId(UserId userId) {
    return io.github.bohdanzhuvak.onlinestore.cart.domain.UserId.of(userId.getValue());
  }
}
