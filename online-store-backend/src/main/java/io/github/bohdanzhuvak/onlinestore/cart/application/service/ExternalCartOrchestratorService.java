package io.github.bohdanzhuvak.onlinestore.cart.application.service;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.in.ExternalCartOrchestrator;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.ClearCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.usecase.GetCartItemsUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;

import java.util.List;

public class ExternalCartOrchestratorService implements ExternalCartOrchestrator {
  private final GetCartItemsUseCase getCartItemsUseCase;
  private final ClearCartUseCase clearCartUseCase;

  public ExternalCartOrchestratorService(GetCartItemsUseCase getCartItemsUseCase, ClearCartUseCase clearCartUseCase) {
    this.getCartItemsUseCase = getCartItemsUseCase;
    this.clearCartUseCase = clearCartUseCase;
  }

  @Override
  public List<CartItem> getCartItems(UserId userId) {
    GetCartItemsUseCase.GetCartItemsCommand command = new GetCartItemsUseCase.GetCartItemsCommand(userId);
    return getCartItemsUseCase.execute(command);
  }

  @Override
  public void clearCart(UserId userId) {
    ClearCartUseCase.ClearCartCommand command = new ClearCartUseCase.ClearCartCommand(userId);
    clearCartUseCase.execute(command);
  }
}
