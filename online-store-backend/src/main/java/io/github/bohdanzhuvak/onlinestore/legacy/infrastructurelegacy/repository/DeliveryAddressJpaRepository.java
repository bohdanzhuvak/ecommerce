package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.domain.repository.DeliveryAddressRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface DeliveryAddressJpaRepository extends BaseJpaRepository<DeliveryAddress, Long>, DeliveryAddressRepository {

  @Modifying
  @Query("UPDATE DeliveryAddress da SET da.isDefault = false WHERE da.user.id = :userId AND da.isTechnical = false")
  void clearDefaultAddress(@Param("userId") Long userId);

  @Modifying
  @Query("UPDATE DeliveryAddress da SET da.isTechnical = true WHERE da.id = :addressId")
  void markAsTechnical(@Param("addressId") Long addressId);
}
