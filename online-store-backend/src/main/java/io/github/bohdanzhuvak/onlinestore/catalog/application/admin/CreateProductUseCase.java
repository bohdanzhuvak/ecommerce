package io.github.bohdanzhuvak.onlinestore.catalog.application.admin;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateProductUseCase {
  private final ProductRepository productRepository;

  public CreateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(CreateProductCommand command) {
    // Check if product with this name already exists
    if (productRepository.existsByName(command.name())) {
      throw new IllegalArgumentException("Product with name '" + command.name() + "' already exists");
    }

    // Create new product
    Product product = new Product(
        ProductId.generate(),
        command.name(),
        command.description(),
        command.price(),
        command.stock(),
        command.categoryId()
    );

    // Add images if provided
    if (command.images() != null) {
      command.images().forEach(product::addImage);
    }

    return productRepository.save(product);
  }

  public record CreateProductCommand(String name, String description, Money price, int stock, CategoryId categoryId,
                                     List<String> images) {
  }
}
