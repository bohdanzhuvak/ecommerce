package io.github.bohdanzhuvak.onlinestore.common.repository;

import io.github.bohdanzhuvak.onlinestore.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
