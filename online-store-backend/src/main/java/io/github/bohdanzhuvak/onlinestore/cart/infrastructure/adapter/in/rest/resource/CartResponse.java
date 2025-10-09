package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for cart response
 */
public record CartResponse(
    String id,
    String userId,
    List<CartItemResponse> items,
    MoneyResponse totalAmount,
    int totalItems
) {

  public static CartResponse from(Cart cart) {
    return new CartResponse(
        cart.getId().getValue(),
        cart.getUserId().getValue(),
        cart.getItems().stream()
            .map(CartItemResponse::from)
            .toList(),
        MoneyResponse.from(cart.getTotalPrice()),
        cart.getTotalItems()
    );
  }

  public record CartItemResponse(
      String productId,
      String productName,
      MoneyResponse productPrice,
      int quantity,
      MoneyResponse totalPrice
  ) {

    public static CartItemResponse from(CartItem item) {
      return new CartItemResponse(
          item.getProductId().getValue(),
          item.getProductName(),
          MoneyResponse.from(item.getUnitPrice()),
          item.getQuantity(),
          MoneyResponse.from(item.getTotalPrice())
      );
    }
  }

  public record MoneyResponse(
      BigDecimal amount,
      String currency
  ) {

    public static MoneyResponse from(io.github.bohdanzhuvak.onlinestore.cart.domain.Money money) {
      return new MoneyResponse(
          money.getAmount(),
          money.getCurrency()
      );
    }
  }
}
