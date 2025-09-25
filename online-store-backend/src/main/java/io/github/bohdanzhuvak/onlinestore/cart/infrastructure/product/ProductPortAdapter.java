package io.github.bohdanzhuvak.onlinestore.cart.infrastructure.product;

import io.github.bohdanzhuvak.onlinestore.cart.application.port.out.ProductPort;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductSnapshot;
import io.github.bohdanzhuvak.onlinestore.catalog.application.ports.CatalogQuery;
import org.springframework.stereotype.Service;

@Service
public class ProductPortAdapter implements ProductPort {
  private final CatalogQuery catalogQuery;

  public ProductPortAdapter(CatalogQuery catalogQuery) {
    this.catalogQuery = catalogQuery;
  }

  @Override
  public ProductSnapshot getProduct(ProductId productId) {
    return toProductSnapshot(
        catalogQuery.getActiveProduct(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId.of(productId.getValue())));
  }

  private ProductSnapshot toProductSnapshot(CatalogQuery.ProductInfo product) {
    return ProductSnapshot.of(
        ProductId.of(product.productId().getValue()),
        product.productName(),
        Money.of(product.unitPrice().getAmount(), product.unitPrice().getCurrency())
    );
  }
}
