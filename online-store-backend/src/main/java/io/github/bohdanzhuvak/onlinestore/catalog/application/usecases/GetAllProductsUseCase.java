package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetAllProductsUseCase {
  private final ProductRepository productRepository;

  public GetAllProductsUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute() {
    return productRepository.findAll();
  }

  public List<Product> execute(int offset, int limit) {
    return productRepository.findAll(offset, limit);
  }

  public Optional<Product> execute(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId productId) {
    return productRepository.findById(productId);
  }
}
