package io.github.bohdanzhuvak.onlinestore.admin.service;

import io.github.bohdanzhuvak.onlinestore.admin.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.admin.mapper.AdminOrderMapper;
import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import io.github.bohdanzhuvak.onlinestore.common.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
  private final OrderRepository orderRepository;
  private final AdminOrderMapper orderMapper;

  public Page<OrderResponse> getOrders(Pageable pageable) {
    Page<Order> orders = orderRepository.findAll(pageable);
    return orderMapper.toResponsePage(orders);
  }
}
