package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.product.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
  Page<ProductResponse> getProducts(Pageable pageable);

  ProductResponse getProduct(Long id);
}
