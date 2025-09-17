package io.github.bohdanzhuvak.onlinestore.catalog.application.ports;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Product;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.ProductId;

import java.util.Optional;

public interface CatalogService {
  Optional<Product> getActiveProduct(ProductId productId);
}
