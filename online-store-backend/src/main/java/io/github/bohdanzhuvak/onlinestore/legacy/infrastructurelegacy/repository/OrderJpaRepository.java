package io.github.bohdanzhuvak.onlinestore.legacy.infrastructurelegacy.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Order;
import io.github.bohdanzhuvak.onlinestore.domain.repository.OrderRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface OrderJpaRepository extends BaseJpaRepository<Order, Long>, OrderRepository {
}
