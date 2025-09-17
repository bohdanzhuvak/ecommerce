package io.github.bohdanzhuvak.onlinestore.cart.infrastructure;

import io.github.bohdanzhuvak.onlinestore.cart.application.ports.CatalogService;
import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.catalog.application.InternalCatalogService;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CatalogServiceImpl implements CatalogService {
  private final InternalCatalogService internalCatalogService;

  public CatalogServiceImpl(InternalCatalogService internalCatalogService) {
    this.internalCatalogService = internalCatalogService;
  }

  @Override
  public Optional<ProductInfo> getProductInfo(ProductId productId) {
    return internalCatalogService.getActiveProduct(io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId.of(productId.getValue()))
        .map(this::toProductInfo);
  }

  private ProductInfo toProductInfo(Product product) {
    return new ProductInfo(
        ProductId.of(product.getId().getValue()),
        product.getName(),
        Money.of(product.getPrice().getAmount(), product.getPrice().getCurrency())
    );
  }
}
