package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;

import java.util.Optional;

public interface CartRepository extends BaseRepository<Cart, Long> {
  Optional<Cart> findByUserId(Long userId);
}
