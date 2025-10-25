package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.out.persitence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, String> {
  boolean existsByName(String name);
}
