package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {
  private Long productId;
  private String productName;
  private int quantity;
  private BigDecimal pricePerUnit;
}
