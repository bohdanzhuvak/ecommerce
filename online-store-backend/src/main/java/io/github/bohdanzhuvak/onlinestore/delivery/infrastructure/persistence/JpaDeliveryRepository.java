package io.github.bohdanzhuvak.onlinestore.delivery.infrastructure.persistence;

import io.github.bohdanzhuvak.onlinestore.delivery.domain.DeliveryStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaDeliveryRepository extends JpaRepository<DeliveryEntity, String> {

  Optional<DeliveryEntity> findByOrderId(String orderId);

  List<DeliveryEntity> findByUserId(String userId);

  @Query("SELECT d FROM DeliveryEntity d WHERE d.userId = :userId ORDER BY d.createdAt DESC")
  List<DeliveryEntity> findByUserId(@Param("userId") String userId, Pageable pageable);

  List<DeliveryEntity> findByStatus(DeliveryStatus status);

  @Query("SELECT d FROM DeliveryEntity d WHERE d.status = :status ORDER BY d.createdAt DESC")
  List<DeliveryEntity> findByStatus(@Param("status") DeliveryStatus status, Pageable pageable);

  Optional<DeliveryEntity> findByTrackingNumber(String trackingNumber);

  @Query("SELECT COUNT(d) FROM DeliveryEntity d WHERE d.userId = :userId")
  long countByUserId(@Param("userId") String userId);

  @Query("SELECT COUNT(d) FROM DeliveryEntity d WHERE d.status = :status")
  long countByStatus(@Param("status") DeliveryStatus status);

  boolean existsByOrderId(String orderId);
}
