package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.delivery.application.port.out.DeliveryRepository;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.Delivery;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.OrderId;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.TrackingNumber;
import io.github.bohdanzhuvak.onlinestore.delivery.domain.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DeliveryJpaAdapter implements DeliveryRepository {
  private final JpaDeliveryRepository jpaDeliveryRepository;
  private final DeliveryMapper deliveryMapper;

  public DeliveryJpaAdapter(JpaDeliveryRepository jpaDeliveryRepository, DeliveryMapper deliveryMapper) {
    this.jpaDeliveryRepository = jpaDeliveryRepository;
    this.deliveryMapper = deliveryMapper;
  }

  @Override
  public Delivery save(Delivery delivery) {
    DeliveryEntity entity = deliveryMapper.toEntity(delivery);
    DeliveryEntity savedEntity = jpaDeliveryRepository.save(entity);
    return deliveryMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Delivery> findById(DeliveryId id) {
    return jpaDeliveryRepository.findById(id.getValue())
        .map(deliveryMapper::toDomain);
  }

  @Override
  public Optional<Delivery> findByOrderId(OrderId orderId) {
    return jpaDeliveryRepository.findByOrderId(orderId.getValue())
        .map(deliveryMapper::toDomain);
  }

  @Override
  public List<Delivery> findByUserId(UserId userId) {
    return deliveryMapper.toDomainList(jpaDeliveryRepository.findByUserId(userId.getValue()));
  }

  @Override
  public List<Delivery> findByStatus(DeliveryStatus status) {
    return deliveryMapper.toDomainList(jpaDeliveryRepository.findByStatus(status));
  }

  @Override
  public Optional<Delivery> findByTrackingNumber(TrackingNumber trackingNumber) {
    return jpaDeliveryRepository.findByTrackingNumber(trackingNumber.getValue())
        .map(deliveryMapper::toDomain);
  }

  @Override
  public List<Delivery> findByUserId(UserId userId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return deliveryMapper.toDomainList(jpaDeliveryRepository.findByUserId(userId.getValue(), pageable));
  }

  @Override
  public List<Delivery> findByStatus(DeliveryStatus status, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return deliveryMapper.toDomainList(jpaDeliveryRepository.findByStatus(status, pageable));
  }

  @Override
  public List<Delivery> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return deliveryMapper.toDomainList(jpaDeliveryRepository.findAll(pageable).getContent());
  }

  @Override
  public long countByUserId(UserId userId) {
    return jpaDeliveryRepository.countByUserId(userId.getValue());
  }

  @Override
  public long countByStatus(DeliveryStatus status) {
    return jpaDeliveryRepository.countByStatus(status);
  }

  @Override
  public long count() {
    return jpaDeliveryRepository.count();
  }

  @Override
  public boolean existsById(DeliveryId id) {
    return jpaDeliveryRepository.existsById(id.getValue());
  }

  @Override
  public boolean existsByOrderId(OrderId orderId) {
    return jpaDeliveryRepository.existsByOrderId(orderId.getValue());
  }

  @Override
  public void delete(DeliveryId id) {
    jpaDeliveryRepository.deleteById(id.getValue());
  }
}
