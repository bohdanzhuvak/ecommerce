package io.github.bohdanzhuvak.onlinestore.features.admin.controller.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.features.admin.controller.AdminBaseController;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController extends AdminBaseController<Order, AdminOrderRequest, AdminOrderResponse, Long> {
  private final AdminOrderService adminOrderService;

  @Override
  protected AdminOrderService getAdminService() {
    return adminOrderService;
  }
}
