package io.github.bohdanzhuvak.onlinestore.cart.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

@UseCase
public class ClearCartUseCase {
  private final CartRepository cartRepository;

  public ClearCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Cart execute(ClearCartCommand command) {
    Cart cart = cartRepository.findByUserId(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + command.userId()));

    cart.clear();

    return cartRepository.save(cart);
  }

  public record ClearCartCommand(UserId userId) {
  }
}
