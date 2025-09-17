package io.github.bohdanzhuvak.onlinestore.cart.application;

import io.github.bohdanzhuvak.onlinestore.cart.application.customer.AddItemToCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.customer.ClearCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.customer.GetCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.customer.RemoveItemFromCartUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.application.customer.UpdateCartItemUseCase;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.UserId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartApplicationService {
  private final GetCartUseCase getCartUseCase;
  private final AddItemToCartUseCase addItemToCartUseCase;
  private final UpdateCartItemUseCase updateCartItemUseCase;
  private final RemoveItemFromCartUseCase removeItemFromCartUseCase;
  private final ClearCartUseCase clearCartUseCase;

  public CartApplicationService(GetCartUseCase getCartUseCase,
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
  public Optional<Cart> getCart(UserId userId) {
    return getCartUseCase.execute(userId);
  }

  public Cart addItemToCart(UserId userId, ProductId productId, int quantity) {
    AddItemToCartUseCase.AddItemToCartCommand command = new AddItemToCartUseCase.AddItemToCartCommand(
        userId, productId, quantity);
    return addItemToCartUseCase.execute(command);
  }

  public Cart updateCartItem(UserId userId, ProductId productId, int quantity) {
    UpdateCartItemUseCase.UpdateCartItemCommand command = new UpdateCartItemUseCase.UpdateCartItemCommand(
        userId, productId, quantity);
    return updateCartItemUseCase.execute(command);
  }

  public Cart removeItemFromCart(UserId userId, ProductId productId) {
    RemoveItemFromCartUseCase.RemoveItemFromCartCommand command = new RemoveItemFromCartUseCase.RemoveItemFromCartCommand(
        userId, productId);
    return removeItemFromCartUseCase.execute(command);
  }

  public Cart clearCart(UserId userId) {
    ClearCartUseCase.ClearCartCommand command = new ClearCartUseCase.ClearCartCommand(userId);
    return clearCartUseCase.execute(command);
  }
}
