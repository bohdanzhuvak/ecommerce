package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.model.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.DeliveryAddressRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.order.AdminOrderRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.order.AdminOrderResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper.AdminOrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminOrderService extends AbstractAdminFullAccessService<Order, AdminOrderResponse, AdminOrderRequest, Long> {
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final DeliveryAddressRepository deliveryAddressRepository;
  private final AdminOrderMapper orderMapper;


  @Override
  protected OrderRepository getRepository() {
    return orderRepository;
  }

  @Override
  protected AdminOrderMapper getMapper() {
    return orderMapper;
  }

  @Override
  @Transactional
  public AdminOrderResponse save(AdminOrderRequest dto) {
    Order order = getMapper().toEntity(dto);

    // Load user if userId is provided
    if (dto.getUserId() != null) {
      User user = userRepository.findById(dto.getUserId())
          .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
      order.setUser(user);
    }

    // Load delivery address if deliveryAddressId is provided
    if (dto.getDeliveryAddressId() != null) {
      DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(dto.getDeliveryAddressId())
          .orElseThrow(() -> new RuntimeException("Delivery address not found with id: " + dto.getDeliveryAddressId()));
      order.setDeliveryAddress(deliveryAddress);
    }

    // Set status if provided
    if (dto.getStatus() != null) {
      try {
        order.setStatus(OrderStatus.valueOf(dto.getStatus()));
      } catch (IllegalArgumentException e) {
        throw new RuntimeException("Invalid order status: " + dto.getStatus());
      }
    }

    Order saved = orderRepository.save(order);
    return getMapper().toResponseDto(saved);
  }

  @Override
  @Transactional
  public AdminOrderResponse update(Long id, AdminOrderRequest dto) {
    Order existing = orderRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));

    getMapper().updateEntity(existing, dto);

    // Load user if userId is provided
    if (dto.getUserId() != null) {
      User user = userRepository.findById(dto.getUserId())
          .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
      existing.setUser(user);
    }

    // Load delivery address if deliveryAddressId is provided
    if (dto.getDeliveryAddressId() != null) {
      DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(dto.getDeliveryAddressId())
          .orElseThrow(() -> new RuntimeException("Delivery address not found with id: " + dto.getDeliveryAddressId()));
      existing.setDeliveryAddress(deliveryAddress);
    }

    // Set status if provided
    if (dto.getStatus() != null) {
      try {
        existing.setStatus(OrderStatus.valueOf(dto.getStatus()));
      } catch (IllegalArgumentException e) {
        throw new RuntimeException("Invalid order status: " + dto.getStatus());
      }
    }

    Order updated = orderRepository.save(existing);
    return getMapper().toResponseDto(updated);
  }
}
