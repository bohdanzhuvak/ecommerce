package io.github.bohdanzhuvak.onlinestore.cart.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaCartRepository extends JpaRepository<CartEntity, String> {
  Optional<CartEntity> findByUserId(String userId);

  boolean existsByUserId(String userId);
}
