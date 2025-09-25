package io.github.bohdanzhuvak.onlinestore.cart.application.service;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.WebCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.AddItemToCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.ClearCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.GetCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.RemoveItemFromCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.UpdateCartItemUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.Optional;

public class WebCartOrchestratorService implements WebCartOrchestrator {
  private final GetCartUseCase getCartUseCase;
  private final AddItemToCartUseCase addItemToCartUseCase;
  private final UpdateCartItemUseCase updateCartItemUseCase;
  private final RemoveItemFromCartUseCase removeItemFromCartUseCase;
  private final ClearCartUseCase clearCartUseCase;

  public WebCartOrchestratorService(GetCartUseCase getCartUseCase,
                                    AddItemToCartUseCase addItemToCartUseCase,
                                    UpdateCartItemUseCase updateCartItemUseCase,
                                    RemoveItemFromCartUseCase removeItemFromCartUseCase,
                                    ClearCartUseCase clearCartUseCase) {
    this.getCartUseCase = getCartUseCase;
    this.addItemToCartUseCase = addItemToCartUseCase;
    this.updateCartItemUseCase = updateCartItemUseCase;
    this.removeItemFromCartUseCase = removeItemFromCartUseCase;
    this.clearCartUseCase = clearCartUseCase;
  }

  // Customer operations
  @Override
  public Optional<Cart> getCart(UserId userId) {
    return getCartUseCase.execute(userId);
  }

  @Override
  public Cart addItemToCart(UserId userId, ProductId productId, int quantity) {
    AddItemToCartUseCase.AddItemToCartCommand command = new AddItemToCartUseCase.AddItemToCartCommand(
        userId, productId, quantity);
    return addItemToCartUseCase.execute(command);
  }

  @Override
  public Cart updateCartItem(UserId userId, ProductId productId, int quantity) {
    UpdateCartItemUseCase.UpdateCartItemCommand command = new UpdateCartItemUseCase.UpdateCartItemCommand(
        userId, productId, quantity);
    return updateCartItemUseCase.execute(command);
  }

  @Override
  public Cart removeItemFromCart(UserId userId, ProductId productId) {
    RemoveItemFromCartUseCase.RemoveItemFromCartCommand command = new RemoveItemFromCartUseCase.RemoveItemFromCartCommand(
        userId, productId);
    return removeItemFromCartUseCase.execute(command);
  }

  @Override
  public Cart clearCart(UserId userId) {
    ClearCartUseCase.ClearCartCommand command = new ClearCartUseCase.ClearCartCommand(userId);
    return clearCartUseCase.execute(command);
  }
}
