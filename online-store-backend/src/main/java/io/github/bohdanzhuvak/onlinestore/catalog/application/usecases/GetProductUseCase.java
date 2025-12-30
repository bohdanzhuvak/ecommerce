package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;

@UseCase
public class GetProductUseCase {
  private final ProductRepository productRepository;

  public GetProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(ProductId productId) {
    return productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
  }
}
