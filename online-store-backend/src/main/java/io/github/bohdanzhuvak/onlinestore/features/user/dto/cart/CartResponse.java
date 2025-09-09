package io.github.bohdanzhuvak.onlinestore.features.user.dto.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
  private List<CartItemResponse> items;
  private BigDecimal totalPrice;
}
