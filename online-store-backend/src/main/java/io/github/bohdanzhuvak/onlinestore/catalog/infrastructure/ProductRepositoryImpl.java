package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
  private final JpaProductRepository jpaProductRepository;
  private final ProductMapper productMapper;

  public ProductRepositoryImpl(JpaProductRepository jpaProductRepository, ProductMapper productMapper) {
    this.jpaProductRepository = jpaProductRepository;
    this.productMapper = productMapper;
  }

  @Override
  public Product save(Product product) {
    ProductEntity entity = productMapper.toEntity(product);
    ProductEntity savedEntity = jpaProductRepository.save(entity);
    return productMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Product> findById(ProductId id) {
    return jpaProductRepository.findById(id.getValue())
        .map(productMapper::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return jpaProductRepository.findAll().stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void delete(ProductId id) {
    jpaProductRepository.deleteById(id.getValue());
  }

  @Override
  public List<Product> findByCategoryId(CategoryId categoryId) {
    return jpaProductRepository.findByCategoryId(categoryId.getValue()).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findActiveProducts() {
    return jpaProductRepository.findActiveProducts().stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findActiveProductsByCategoryId(CategoryId categoryId) {
    return jpaProductRepository.findActiveProductsByCategoryId(categoryId.getValue()).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findByNameContaining(String name) {
    return jpaProductRepository.findByNameContaining(name).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findByPriceRange(Money minPrice, Money maxPrice) {
    if (!minPrice.getCurrency().equals(maxPrice.getCurrency())) {
      throw new IllegalArgumentException("Cannot search by price range with different currencies");
    }
    return jpaProductRepository.findByPriceRange(
            minPrice.getAmount(),
            maxPrice.getAmount(),
            minPrice.getCurrency()
        ).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findAvailableProducts() {
    return jpaProductRepository.findAvailableProducts().stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return jpaProductRepository.findAll(pageable).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findActiveProducts(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return jpaProductRepository.findActiveProducts(pageable).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Product> findByCategoryId(CategoryId categoryId, int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return jpaProductRepository.findActiveProductsByCategoryId(categoryId.getValue(), pageable).stream()
        .map(productMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public long count() {
    return jpaProductRepository.count();
  }

  @Override
  public long countActiveProducts() {
    return jpaProductRepository.countActiveProducts();
  }

  @Override
  public long countByCategoryId(CategoryId categoryId) {
    return jpaProductRepository.countByCategoryId(categoryId.getValue());
  }

  @Override
  public boolean existsById(ProductId id) {
    return jpaProductRepository.existsById(id.getValue());
  }

  @Override
  public boolean existsByName(String name) {
    return jpaProductRepository.existsByName(name);
  }
}
