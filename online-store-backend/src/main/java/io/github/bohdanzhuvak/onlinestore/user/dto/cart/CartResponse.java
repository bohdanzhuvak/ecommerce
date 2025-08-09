package io.github.bohdanzhuvak.onlinestore.user.dto.cart;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartResponse {
  private Long userId;
  private List<CartItemResponse> items;
  private BigDecimal totalPrice;
}
