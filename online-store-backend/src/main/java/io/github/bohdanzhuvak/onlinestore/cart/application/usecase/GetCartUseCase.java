package io.github.bohdanzhuvak.onlinestore.cart.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.Optional;

@UseCase
public class GetCartUseCase {
  private final CartRepository cartRepository;

  public GetCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Optional<Cart> execute(UserId userId) {
    return cartRepository.findByUserId(userId);
  }
}
