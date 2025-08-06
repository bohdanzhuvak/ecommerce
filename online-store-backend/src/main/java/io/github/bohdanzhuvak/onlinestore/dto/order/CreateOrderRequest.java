package io.github.bohdanzhuvak.onlinestore.dto.order;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
  private List<Long> cartItemIds;
}
