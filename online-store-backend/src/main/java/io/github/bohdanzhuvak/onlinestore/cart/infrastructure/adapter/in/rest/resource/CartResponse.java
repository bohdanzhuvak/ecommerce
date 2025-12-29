package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.in.rest.resource;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Cart;
import io.github.bohdanzhuvak.onlinestore.cart.domain.CartItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO for cart response
 */
public record CartResponse(
    @Schema(description = "Cart ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String id,

    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED)
    String userId,

    @Schema(description = "List of cart items", requiredMode = Schema.RequiredMode.REQUIRED)
    List<CartItemResponse> items,

    @Schema(description = "Total cart amount", requiredMode = Schema.RequiredMode.REQUIRED)
    MoneyResponse totalAmount,

    @Schema(description = "Total number of items in cart", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "Product ID", requiredMode = Schema.RequiredMode.REQUIRED)
      String productId,

      @Schema(description = "Product name", requiredMode = Schema.RequiredMode.REQUIRED)
      String productName,

      @Schema(description = "Product unit price", requiredMode = Schema.RequiredMode.REQUIRED)
      MoneyResponse productPrice,

      @Schema(description = "Quantity", requiredMode = Schema.RequiredMode.REQUIRED)
      int quantity,

      @Schema(description = "Total price for this item", requiredMode = Schema.RequiredMode.REQUIRED)
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
      @Schema(description = "Amount value", requiredMode = Schema.RequiredMode.REQUIRED)
      BigDecimal amount,

      @Schema(description = "Currency code", requiredMode = Schema.RequiredMode.REQUIRED)
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
