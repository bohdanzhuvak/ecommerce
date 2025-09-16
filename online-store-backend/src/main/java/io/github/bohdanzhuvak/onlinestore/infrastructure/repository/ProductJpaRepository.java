package io.github.bohdanzhuvak.onlinestore.infrastructure.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.domain.repository.ProductRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface ProductJpaRepository extends BaseJpaRepository<Product, Long>, ProductRepository {
}
