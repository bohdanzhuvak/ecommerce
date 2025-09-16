package io.github.bohdanzhuvak.onlinestore.infrastructure.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CartRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CartJpaRepository extends BaseJpaRepository<Cart, Long>, CartRepository {
}
