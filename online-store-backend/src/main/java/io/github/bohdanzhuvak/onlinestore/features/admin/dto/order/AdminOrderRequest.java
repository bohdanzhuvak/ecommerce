package io.github.bohdanzhuvak.onlinestore.features.admin.dto.order;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminRequestDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class AdminOrderRequest extends AdminRequestDto {
  private Long userId;
  private BigDecimal totalPrice;
  private String status;
  private List<AdminOrderItemRequest> items;
  private Long deliveryAddressId;
}
