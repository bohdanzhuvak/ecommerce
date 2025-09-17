package io.github.bohdanzhuvak.onlinestore.catalog.application.admin;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductUseCase {
  private final ProductRepository productRepository;

  public DeleteProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public void execute(ProductId productId) {
    if (!productRepository.existsById(productId)) {
      throw new IllegalArgumentException("Product not found with id: " + productId);
    }

    productRepository.delete(productId);
  }
}
