package io.github.bohdanzhuvak.onlinestore.features.admin.dto.order;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AdminOrderItemResponse extends AdminResponseDto {
  private Long id;
  private Long productId;
  private String productName;
  private Long orderId;
  private Integer quantity;
  private BigDecimal pricePerUnit;
}
