package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.order.OrderResponse;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/orders")
@NoArgsConstructor
public class AdminOrderController {

  @GetMapping
  public Page<OrderResponse> getOrders(Pageable pageable) {
    throw new UnsupportedOperationException();
  }
}
