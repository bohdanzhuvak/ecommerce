package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductRepository;

@UseCase
public class UpdateProductUseCase {
  private final ProductRepository productRepository;

  public UpdateProductUseCase(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Product execute(UpdateProductCommand command) {
    Product product = productRepository.findById(command.productId())
        .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + command.productId()));

    // Update fields if provided
    if (command.name() != null) {
      // Check name uniqueness (if name changed)
      if (!product.getName().equals(command.name()) &&
          productRepository.existsByName(command.name())) {
        throw new IllegalArgumentException("Product with name '" + command.name() + "' already exists");
      }
      product.updateName(command.name());
    }

    if (command.description() != null) {
      product.updateDescription(command.description());
    }

    if (command.price() != null) {
      product.updatePrice(command.price());
    }

    if (command.stock() != null) {
      product.updateStock(command.stock());
    }

    if (command.categoryId() != null) {
      product.changeCategory(command.categoryId());
    }

    return productRepository.save(product);
  }

  public record UpdateProductCommand(ProductId productId, String name, String description, Money price, Integer stock,
                                     CategoryId categoryId) {
  }
}
