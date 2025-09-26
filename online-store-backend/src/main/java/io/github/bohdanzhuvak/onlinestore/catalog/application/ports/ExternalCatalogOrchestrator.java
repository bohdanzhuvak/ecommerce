package io.github.bohdanzhuvak.onlinestore.catalog.application.ports;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Money;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

public interface ExternalCatalogOrchestrator {
  ProductInfo getActiveProduct(ProductId productId);

  record ProductInfo(
      ProductId productId,
      String productName,
      Money unitPrice
  ) {
  }
}
