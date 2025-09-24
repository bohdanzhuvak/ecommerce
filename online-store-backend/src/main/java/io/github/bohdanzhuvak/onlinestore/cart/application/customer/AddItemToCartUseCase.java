package io.github.bohdanzhuvak.onlinestore.cart.application.customer;

import io.github.bohdanzhuvak.onlinestore.cart.application.ports.ProductInfoPort;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class AddItemToCartUseCase {
  private final CartRepository cartRepository;
  private final ProductInfoPort productInfoPort;

  public AddItemToCartUseCase(CartRepository cartRepository, ProductInfoPort productInfoPort) {
    this.cartRepository = cartRepository;
    this.productInfoPort = productInfoPort;
  }

  public Cart execute(AddItemToCartCommand command) {
    // Get product information from catalog
    ProductInfoPort.ProductInfo productInfo = productInfoPort.getProductInfo(command.productId());

    // Find existing cart or create new one
    Cart cart = cartRepository.findByUserId(command.userId())
        .orElse(new Cart(CartId.generate(), command.userId()));

    // Add item to cart with validated product info
    cart.addItem(command.productId(), productInfo, command.quantity());

    return cartRepository.save(cart);
  }

  public record AddItemToCartCommand(
      UserId userId,
      ProductId productId,
      int quantity
  ) {
  }
}
