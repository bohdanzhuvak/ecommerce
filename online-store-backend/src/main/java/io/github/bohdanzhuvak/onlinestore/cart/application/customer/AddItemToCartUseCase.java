package io.github.bohdanzhuvak.onlinestore.cart.application.customer;

import io.github.bohdanzhuvak.onlinestore.cart.application.ports.CatalogService;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartRepository;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class AddItemToCartUseCase {
  private final CartRepository cartRepository;
  private final CatalogService catalogService;

  public AddItemToCartUseCase(CartRepository cartRepository, CatalogService catalogService) {
    this.cartRepository = cartRepository;
    this.catalogService = catalogService;
  }

  public Cart execute(AddItemToCartCommand command) {
    // Get product information from catalog
    CatalogService.ProductInfo productInfo = catalogService.getProductInfo(command.productId())
        .orElseThrow(() -> new IllegalArgumentException("Product not found: " + command.productId()));

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
