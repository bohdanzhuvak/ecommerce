package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.order.OrderResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.mapper.AdminOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminOrderService {
  private final OrderRepository orderRepository;
  private final AdminOrderMapper orderMapper;

  public Page<OrderResponse> getOrders(Pageable pageable, Long userId) {
    Page<Order> orders;
    if (userId != null) {
      orders = orderRepository.findAllByUser_Id(userId, pageable);
    } else {
      orders = orderRepository.findAll(pageable);
    }
    return orderMapper.toResponsePage(orders);
  }

  public OrderResponse getOrder(Long id) {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Order not found with id: " + id));
    return orderMapper.toResponse(order);
  }
}
