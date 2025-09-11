package io.github.bohdanzhuvak.onlinestore.domain.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
}
