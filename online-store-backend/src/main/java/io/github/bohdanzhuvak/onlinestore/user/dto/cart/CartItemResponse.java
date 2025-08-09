package io.github.bohdanzhuvak.onlinestore.user.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItemResponse {
  private Long productId;
  private String productName;
  private int quantity;
  private BigDecimal price;
}
