package io.github.bohdanzhuvak.onlinestore.dto.cart;

import lombok.Data;

@Data
public class UpdateCartItemRequest {
  private Long productId;
  private int quantity;
}
