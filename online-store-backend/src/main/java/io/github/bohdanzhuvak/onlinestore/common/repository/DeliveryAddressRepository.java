package io.github.bohdanzhuvak.onlinestore.common.repository;

import io.github.bohdanzhuvak.onlinestore.common.model.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
  List<DeliveryAddress> findByUserIdAndIsTechnicalFalseOrderByIsDefaultDescCreatedAtDesc(Long userId);

  Optional<DeliveryAddress> findByUserIdAndIsDefaultTrueAndIsTechnicalFalse(Long userId);

  @Modifying
  @Query("UPDATE DeliveryAddress da SET da.isDefault = false WHERE da.user.id = :userId AND da.isTechnical = false")
  void clearDefaultAddress(@Param("userId") Long userId);

  @Modifying
  @Query("UPDATE DeliveryAddress da SET da.isTechnical = true WHERE da.id = :addressId")
  void markAsTechnical(@Param("addressId") Long addressId);
}
