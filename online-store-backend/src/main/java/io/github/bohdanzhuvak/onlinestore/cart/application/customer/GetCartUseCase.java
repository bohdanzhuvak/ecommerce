package io.github.bohdanzhuvak.onlinestore.cart.application.customer;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCartUseCase {
  private final CartRepository cartRepository;

  public GetCartUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Optional<Cart> execute(UserId userId) {
    return cartRepository.findByUserId(userId);
  }
}
