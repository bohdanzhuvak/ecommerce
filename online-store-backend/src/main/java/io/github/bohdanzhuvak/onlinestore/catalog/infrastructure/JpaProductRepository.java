package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaProductRepository extends JpaRepository<ProductEntity, String> {

  // Basic methods
  @Query("SELECT p FROM ProductEntity p WHERE p.active = true")
  List<ProductEntity> findActiveProducts();

  @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND p.categoryId = :categoryId")
  List<ProductEntity> findActiveProductsByCategoryId(@Param("categoryId") String categoryId);

  @Query("SELECT p FROM ProductEntity p WHERE p.categoryId = :categoryId")
  List<ProductEntity> findByCategoryId(@Param("categoryId") String categoryId);

  @Query("SELECT p FROM ProductEntity p WHERE p.name LIKE %:name%")
  List<ProductEntity> findByNameContaining(@Param("name") String name);

  @Query("SELECT p FROM ProductEntity p WHERE p.priceAmount >= :minPrice AND p.priceAmount <= :maxPrice AND p.priceCurrency = :currency")
  List<ProductEntity> findByPriceRange(@Param("minPrice") java.math.BigDecimal minPrice,
                                       @Param("maxPrice") java.math.BigDecimal maxPrice,
                                       @Param("currency") String currency);

  @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND p.stock > 0")
  List<ProductEntity> findAvailableProducts();

  // Pagination
  @Query("SELECT p FROM ProductEntity p WHERE p.active = true")
  List<ProductEntity> findActiveProducts(Pageable pageable);

  @Query("SELECT p FROM ProductEntity p WHERE p.active = true AND p.categoryId = :categoryId")
  List<ProductEntity> findActiveProductsByCategoryId(@Param("categoryId") String categoryId, Pageable pageable);

  // Counting
  @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.active = true")
  long countActiveProducts();

  @Query("SELECT COUNT(p) FROM ProductEntity p WHERE p.categoryId = :categoryId")
  long countByCategoryId(@Param("categoryId") String categoryId);

  // Existence checks
  boolean existsByName(String name);
}
