package io.github.bohdanzhuvak.onlinestore.user.dto.order;

import io.github.bohdanzhuvak.onlinestore.user.dto.delivery.DeliveryAddressResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
  private Long id;
  private LocalDateTime createdAt;
  private BigDecimal totalPrice;
  private String status;
  private DeliveryAddressResponse deliveryAddress;
  private List<OrderItemResponse> items;
}
