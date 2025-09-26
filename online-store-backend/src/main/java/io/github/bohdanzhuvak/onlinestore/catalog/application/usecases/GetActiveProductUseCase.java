package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;

@UseCase
public class GetActiveProductUseCase {
  private final ProductRepository productRepository;

  public GetActiveProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(ProductId productId) {
    return productRepository.findById(productId).filter(Product::isActive).orElseThrow(() -> new IllegalArgumentException("Active product not found: " + productId));
  }
}
