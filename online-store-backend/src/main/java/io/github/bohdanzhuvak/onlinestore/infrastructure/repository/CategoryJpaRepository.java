package io.github.bohdanzhuvak.onlinestore.infrastructure.repository;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CategoryJpaRepository extends BaseJpaRepository<Category, Long>, CategoryRepository {
}
