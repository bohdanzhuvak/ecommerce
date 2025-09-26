package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.adapter.out.product;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.ProductPort;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductSnapshot;
import io.github.bohdanzhuvak.onlinestore.catalog.application.ports.ExternalCatalogOrchestrator;
import org.springframework.stereotype.Service;

@Service
public class ProductPortAdapter implements ProductPort {
  private final ExternalCatalogOrchestrator externalCatalogOrchestrator;

  public ProductPortAdapter(ExternalCatalogOrchestrator externalCatalogOrchestrator) {
    this.externalCatalogOrchestrator = externalCatalogOrchestrator;
  }

  @Override
  public ProductSnapshot getProduct(ProductId productId) {
    return toProductSnapshot(
        externalCatalogOrchestrator.getActiveProduct(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId.of(productId.getValue())));
  }

  private ProductSnapshot toProductSnapshot(ExternalCatalogOrchestrator.ProductInfo product) {
    return ProductSnapshot.of(
        ProductId.of(product.productId().getValue()),
        product.productName(),
        Money.of(product.unitPrice().getAmount(), product.unitPrice().getCurrency())
    );
  }
}
