package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
