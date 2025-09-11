package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.AdminOrderResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.mapper.AdminOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService extends AbstractAdminFullAccessService<Order, AdminOrderResponse, AdminOrderRequest, Long> {
  private final OrderRepository orderRepository;
  private final AdminOrderMapper orderMapper;


  @Override
  protected OrderRepository getRepository() {
    return orderRepository;
  }

  @Override
  protected AdminOrderMapper getMapper() {
    return orderMapper;
  }
}
