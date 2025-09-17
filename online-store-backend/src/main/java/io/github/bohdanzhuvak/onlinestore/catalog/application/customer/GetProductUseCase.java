package io.github.bohdanzhuvak.onlinestore.catalog.application.customer;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetProductUseCase {
  private final ProductRepository productRepository;

  public GetProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Optional<Product> execute(ProductId productId) {
    return productRepository.findById(productId)
        .filter(Product::isActive);
  }
}
