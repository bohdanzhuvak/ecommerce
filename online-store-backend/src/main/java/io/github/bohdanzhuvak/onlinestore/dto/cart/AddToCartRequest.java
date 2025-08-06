package io.github.bohdanzhuvak.onlinestore.dto.cart;

import lombok.Data;

@Data
public class AddToCartRequest {
  private Long productId;
  private int quantity;
}
