package io.github.bohdanzhuvak.onlinestore.catalog.application.customer;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchProductsUseCase {
  private final ProductRepository productRepository;

  public SearchProductsUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> searchByName(String name) {
    if (name == null || name.trim().isEmpty()) {
      return productRepository.findActiveProducts();
    }
    return productRepository.findByNameContaining(name.trim());
  }

  public List<Product> searchByCategory(CategoryId categoryId) {
    return productRepository.findActiveProductsByCategoryId(categoryId);
  }

  public List<Product> searchByPriceRange(Money minPrice, Money maxPrice) {
    return productRepository.findByPriceRange(minPrice, maxPrice);
  }

  public List<Product> searchAvailable() {
    return productRepository.findAvailableProducts();
  }
}
