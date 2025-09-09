package io.github.bohdanzhuvak.onlinestore.features.admin.controller;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {
  private final AdminOrderService adminOrderService;

  @GetMapping
  public Page<OrderResponse> getOrders(Pageable pageable, @RequestParam(required = false) Long userId) {
    return adminOrderService.getOrders(pageable, userId);
  }

  @GetMapping("/{id}")
  public OrderResponse getOrder(@PathVariable Long id) {
    return adminOrderService.getOrder(id);
  }
}
