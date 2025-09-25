package io.github.bohdanzhuvak.onlinestore.cart.application.port.out;

import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductSnapshot;

public interface ProductPort {
  /**
   * Gets product information needed for cart operations
   *
   * @param productId the product ID as string
   * @return product information or empty if product not found
   */
  ProductSnapshot getProduct(ProductId productId);
}
