package io.github.bohdanzhuvak.onlinestore.features.admin.dto.order;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminOrderItemRequest extends AdminRequestDto {
  private Long productId;
  private Long orderId;
  private Integer quantity;
  private BigDecimal pricePerUnit;
}
