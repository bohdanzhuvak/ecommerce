package io.github.bohdanzhuvak.onlinestore.cart.application.usecase;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.ProductPort;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductSnapshot;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

@UseCase
public class AddItemToCartUseCase {
  private final CartRepository cartRepository;
  private final ProductPort productPort;

  public AddItemToCartUseCase(CartRepository cartRepository, ProductPort productPort) {
    this.cartRepository = cartRepository;
    this.productPort = productPort;
  }

  public Cart execute(AddItemToCartCommand command) {
    // Get product information from catalog
    ProductSnapshot productSnapshot = productPort.getProduct(command.productId());

    // Find existing cart or create new one
    Cart cart = cartRepository.findByUserId(command.userId())
        .orElse(new Cart(CartId.generate(), command.userId()));

    // Add item to cart with validated product info
    cart.addItem(command.productId(), productSnapshot, command.quantity());

    return cartRepository.save(cart);
  }

  public record AddItemToCartCommand(
      UserId userId,
      ProductId productId,
      int quantity
  ) {
  }
}
