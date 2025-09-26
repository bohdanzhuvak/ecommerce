package io.github.bohdanzhuvak.onlinestore.catalog.application.services;

import io.github.bohdanzhuvak.onlinestore.catalog.application.ports.ExternalCatalogOrchestrator;
import io.github.bohdanzhuvak.onlinestore.catalog.application.usecases.GetActiveProductUseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

public class ExternalCatalogOrchestratorService implements ExternalCatalogOrchestrator {

  private final GetActiveProductUseCase getActiveProductUseCase;

  public ExternalCatalogOrchestratorService(GetActiveProductUseCase getActiveProductUseCase) {
    this.getActiveProductUseCase = getActiveProductUseCase;
  }

  @Override
  public ProductInfo getActiveProduct(ProductId productId) {
    return toProductInfo(getActiveProductUseCase.execute(productId));
  }

  private ProductInfo toProductInfo(Product product) {
    return new ProductInfo(
        product.getId(),
        product.getName(),
        product.getPrice()
    );
  }
}
