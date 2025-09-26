package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.common.exception.impl.NotFoundException;

@UseCase
public class GetProductUseCase {
  private final ProductRepository productRepository;

  public GetProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(ProductId productId) {
    return productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
  }
}
