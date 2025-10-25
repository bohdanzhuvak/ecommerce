package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;

import java.util.List;
import java.util.Optional;

@UseCase
public class GetAllProductsUseCase {
  private final ProductRepository productRepository;

  public GetAllProductsUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public List<Product> execute() {
    return productRepository.findAll();
  }

  public PageResult<Product> execute(int page, int pageSize) {
    // React-admin sends 1-based page numbers, convert to 0-based offset
    int offset = (page - 1) * pageSize;
    List<Product> products = productRepository.findAll(offset, pageSize);
    long total = productRepository.count();
    return PageResult.of(products, total, page, pageSize);
  }

  public Optional<Product> execute(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId productId) {
    return productRepository.findById(productId);
  }
}
