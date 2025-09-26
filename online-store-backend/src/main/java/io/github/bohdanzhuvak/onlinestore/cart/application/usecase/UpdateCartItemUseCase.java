package io.github.bohdanzhuvak.onlinestore.cart.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

@UseCase
public class UpdateCartItemUseCase {
  private final CartRepository cartRepository;

  public UpdateCartItemUseCase(CartRepository cartRepository) {
    this.cartRepository = cartRepository;
  }

  public Cart execute(UpdateCartItemCommand command) {
    Cart cart = cartRepository.findByUserId(command.userId())
        .orElseThrow(() -> new IllegalArgumentException("Cart not found for user: " + command.userId()));

    cart.updateItemQuantity(command.productId(), command.quantity());

    return cartRepository.save(cart);
  }

  public record UpdateCartItemCommand(
      UserId userId,
      ProductId productId,
      int quantity
  ) {
  }
}
