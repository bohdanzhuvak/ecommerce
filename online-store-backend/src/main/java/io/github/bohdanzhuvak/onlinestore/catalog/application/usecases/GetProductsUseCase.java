package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;

import java.util.List;

@UseCase
public class GetProductsUseCase {
  private final ProductRepository productRepository;

  public GetProductsUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute() {
    return productRepository.findActiveProducts();
  }

  public List<Product> execute(int offset, int limit) {
    return productRepository.findActiveProducts(offset, limit);
  }
}
