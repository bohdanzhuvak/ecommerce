package io.github.bohdanzhuvak.onlinestore.order.infrastructure.adapter.out.persistence;

import io.github.bohdanzhuvak.onlinestore.order.domain.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaOrderRepository extends JpaRepository<OrderEntity, String> {

  List<OrderEntity> findByUserId(String userId);

  List<OrderEntity> findByStatus(OrderStatus status);

  List<OrderEntity> findByUserIdAndStatus(String userId, OrderStatus status);

  @Query("SELECT o FROM OrderEntity o WHERE o.userId = :userId")
  List<OrderEntity> findByUserId(@Param("userId") String userId, Pageable pageable);

  @Query("SELECT o FROM OrderEntity o WHERE o.status = :status")
  List<OrderEntity> findByStatus(@Param("status") OrderStatus status, Pageable pageable);

  @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.userId = :userId")
  long countByUserId(@Param("userId") String userId);

  @Query("SELECT COUNT(o) FROM OrderEntity o WHERE o.status = :status")
  long countByStatus(@Param("status") OrderStatus status);
}
