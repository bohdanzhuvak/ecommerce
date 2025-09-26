package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.order;

import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.AdminResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.delivery.AdminDeliveryAddressResponse;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AdminOrderResponse extends AdminResponseDto {
  private Long id;
  private Long userId;
  private LocalDateTime createdAt;
  private BigDecimal totalPrice;
  private String status;
  private List<AdminOrderItemResponse> items;
  private AdminDeliveryAddressResponse deliveryAddress;
}
