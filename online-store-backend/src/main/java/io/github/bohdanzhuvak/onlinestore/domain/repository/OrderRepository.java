package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
  List<Order> findAllByUser_Id(Long userId);

  Page<Order> findAllByUser_Id(Long userId, Pageable pageable);

  boolean existsByDeliveryAddressId(Long deliveryAddressId);
}
