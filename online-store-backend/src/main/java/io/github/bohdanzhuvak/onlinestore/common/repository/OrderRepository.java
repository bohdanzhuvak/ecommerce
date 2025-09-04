package io.github.bohdanzhuvak.onlinestore.common.repository;

import io.github.bohdanzhuvak.onlinestore.common.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findAllByUser_Id(Long userId);
  
  boolean existsByDeliveryAddressId(Long deliveryAddressId);
}
