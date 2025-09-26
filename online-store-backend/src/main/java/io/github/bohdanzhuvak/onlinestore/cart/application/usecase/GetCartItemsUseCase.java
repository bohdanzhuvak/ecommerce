package io.github.bohdanzhuvak.onlinestore.cart.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.List;

@UseCase
public class GetCartItemsUseCase {
  private final CartRepository cartRepository;

  public GetCartItemsUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public List<CartItem> execute(GetCartItemsCommand command) {
    return cartRepository.getCartItems(command.userId());
  }

  public record GetCartItemsCommand(UserId userId) {
  }
}
