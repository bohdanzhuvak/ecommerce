package io.github.bohdanzhuvak.onlinestore.order.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.order.application.ports.out.OrderRepository;
import io.github.bohdanzhuvak.onlinestore.order.domain.Order;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import io.github.bohdanzhuvak.onlinestore.order.domain.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderJpaAdapter implements OrderRepository {
  private final JpaOrderRepository jpaOrderRepository;
  private final OrderMapper orderMapper;

  public OrderJpaAdapter(JpaOrderRepository jpaOrderRepository, OrderMapper orderMapper) {
    this.jpaOrderRepository = jpaOrderRepository;
    this.orderMapper = orderMapper;
  }

  @Override
  public Order save(Order order) {
    OrderEntity entity = orderMapper.toEntity(order);
    OrderEntity savedEntity = jpaOrderRepository.save(entity);
    return orderMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Order> findById(OrderId id) {
    return jpaOrderRepository.findById(id.getValue())
        .map(orderMapper::toDomain);
  }

  @Override
  public List<Order> findByUserId(UserId userId) {
    return orderMapper.toDomainList(jpaOrderRepository.findByUserId(userId.getValue()));
  }

  @Override
  public List<Order> findByStatus(OrderStatus status) {
    return orderMapper.toDomainList(jpaOrderRepository.findByStatus(status));
  }

  @Override
  public List<Order> findByUserIdAndStatus(UserId userId, OrderStatus status) {
    return orderMapper.toDomainList(jpaOrderRepository.findByUserIdAndStatus(userId.getValue(), status));
  }

  @Override
  public List<Order> findAll() {
    return orderMapper.toDomainList(jpaOrderRepository.findAll());
  }

  @Override
  public List<Order> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return orderMapper.toDomainList(jpaOrderRepository.findAll(pageable).getContent());
  }

  @Override
  public List<Order> findByUserId(UserId userId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return orderMapper.toDomainList(jpaOrderRepository.findByUserId(userId.getValue(), pageable));
  }

  @Override
  public List<Order> findByStatus(OrderStatus status, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return orderMapper.toDomainList(jpaOrderRepository.findByStatus(status, pageable));
  }

  @Override
  public long count() {
    return jpaOrderRepository.count();
  }

  @Override
  public long countByUserId(UserId userId) {
    return jpaOrderRepository.countByUserId(userId.getValue());
  }

  @Override
  public long countByStatus(OrderStatus status) {
    return jpaOrderRepository.countByStatus(status);
  }

  @Override
  public boolean existsById(OrderId id) {
    return jpaOrderRepository.existsById(id.getValue());
  }

  @Override
  public void delete(OrderId id) {
    jpaOrderRepository.deleteById(id.getValue());
  }
}
