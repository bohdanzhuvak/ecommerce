package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCartItemRequest {
  private Long productId;
  private int quantity;
}
