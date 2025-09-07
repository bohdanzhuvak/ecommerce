package io.github.bohdanzhuvak.onlinestore.admin.dto.order;

import io.github.bohdanzhuvak.onlinestore.admin.dto.delivery.DeliveryAddressResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
  private Long id;
  private Long userId;
  private LocalDateTime createdAt;
  private BigDecimal totalPrice;
  private String status;
  private List<OrderItemResponse> items;
  private DeliveryAddressResponse deliveryAddress;
}
