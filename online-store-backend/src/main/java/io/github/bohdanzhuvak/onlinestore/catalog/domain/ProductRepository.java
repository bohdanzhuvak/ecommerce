package io.github.bohdanzhuvak.onlinestore.catalog.domain;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
  // Basic methods
  Product save(Product product);

  Optional<Product> findById(ProductId id);

  List<Product> findAll();

  void delete(ProductId id);

  // Search and filtering
  List<Product> findByCategoryId(CategoryId categoryId);

  List<Product> findActiveProducts();

  List<Product> findActiveProductsByCategoryId(CategoryId categoryId);

  List<Product> findByNameContaining(String name);

  List<Product> findByPriceRange(Money minPrice, Money maxPrice);

  List<Product> findAvailableProducts();

  // Pagination
  List<Product> findAll(int offset, int limit);

  List<Product> findActiveProducts(int offset, int limit);

  List<Product> findByCategoryId(CategoryId categoryId, int offset, int limit);

  // Counting
  long count();

  long countActiveProducts();

  long countByCategoryId(CategoryId categoryId);

  // Existence checks
  boolean existsById(ProductId id);

  boolean existsByName(String name);
}
