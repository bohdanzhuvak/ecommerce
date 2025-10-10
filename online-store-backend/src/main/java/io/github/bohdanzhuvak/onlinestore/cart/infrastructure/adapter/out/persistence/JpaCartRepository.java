package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, String> {
  Optional<CartEntity> findByUserId(String userId);

  boolean existsByUserId(String userId);

  @Query("SELECT ci FROM CartItemEntity ci JOIN ci.cart c WHERE c.userId = :userId")
  List<CartItemEntity> findItemsByUserId(@Param("userId") String userId);
}
