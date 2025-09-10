package io.github.bohdanzhuvak.onlinestore.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
  private final ProductService productService;

  @GetMapping
  public Page<ProductResponse> getProducts(Pageable pageable) {
    return productService.getProducts(pageable);
  }

  @GetMapping("/{id}")
  public ProductResponse getProduct(@PathVariable Long id) {
    return productService.getProduct(id);
  }

}
