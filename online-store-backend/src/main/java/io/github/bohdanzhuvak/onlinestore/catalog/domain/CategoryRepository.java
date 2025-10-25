package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository {
  Category save(Category category);

  Optional<Category> findById(CategoryId categoryId);

  List<Category> findAll();

  List<Category> findAll(int offset, int limit);

  void deleteById(CategoryId categoryId);

  boolean existsById(CategoryId categoryId);
}
