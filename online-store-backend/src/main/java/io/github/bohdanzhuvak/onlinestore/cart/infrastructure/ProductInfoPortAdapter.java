package io.github.bohdanzhuvak.onlinestore.cart.infrastructure;

import io.github.bohdanzhuvak.onlinestore.cart.application.ports.ProductInfoPort;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.application.ports.CatalogQuery;
import org.springframework.stereotype.Service;

@Service
public class ProductInfoPortAdapter implements ProductInfoPort {
  private final CatalogQuery catalogQuery;

  public ProductInfoPortAdapter(CatalogQuery catalogQuery) {
    this.catalogQuery = catalogQuery;
  }

  @Override
  public ProductInfo getProductInfo(ProductId productId) {
    return toProductInfo(
        catalogQuery.getActiveProduct(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId.of(productId.getValue())));
  }

  private ProductInfo toProductInfo(CatalogQuery.ProductInfo product) {
    return new ProductInfo(
        ProductId.of(product.productId().getValue()),
        product.productName(),
        Money.of(product.unitPrice().getAmount(), product.unitPrice().getCurrency())
    );
  }
}
