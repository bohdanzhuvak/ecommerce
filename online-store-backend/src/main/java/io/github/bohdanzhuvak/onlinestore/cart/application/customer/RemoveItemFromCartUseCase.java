package io.github.bohdanzhuvak.onlinestore.cart.application.customer;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class RemoveItemFromCartUseCase {
  private final CartRepository cartRepository;

  public RemoveItemFromCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Cart execute(RemoveItemFromCartCommand command) {
    Cart cart = cartRepository.findByUserId(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + command.userId()));

    cart.removeItem(command.productId());

    return cartRepository.save(cart);
  }

  public record RemoveItemFromCartCommand(
      UserId userId,
      ProductId productId
  ) {
  }
}
