package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends BaseRepository<DeliveryAddress, Long> {
  List<DeliveryAddress> findByUserIdAndIsTechnicalFalseOrderByIsDefaultDescCreatedAtDesc(Long userId);

  Optional<DeliveryAddress> findByUserIdAndIsDefaultTrueAndIsTechnicalFalse(Long userId);

  /**
   * Clear default address for user
   */
  void clearDefaultAddress(Long userId);

  /**
   * Mark address as technical
   */
  void markAsTechnical(Long addressId);
}
