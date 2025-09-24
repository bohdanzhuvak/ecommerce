package io.github.bohdanzhuvak.onlinestore.cart.application.ports;

import io.github.bohdanzhuvak.onlinestore.cart.domain.Money;
import io.github.bohdanzhuvak.onlinestore.cart.domain.ProductId;

public interface ProductInfoPort {
  /**
   * Gets product information needed for cart operations
   *
   * @param productId the product ID as string
   * @return product information or empty if product not found
   */
  ProductInfo getProductInfo(ProductId productId);

  /**
   * Product information for cart operations
   * Uses primitive types to avoid coupling with other bounded contexts
   */
  record ProductInfo(
      ProductId productId,
      String productName,
      Money unitPrice
  ) {
  }
}
