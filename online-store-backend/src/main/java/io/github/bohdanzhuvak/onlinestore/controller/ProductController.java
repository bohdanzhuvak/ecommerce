package io.github.bohdanzhuvak.onlinestore.controller;

import io.github.bohdanzhuvak.onlinestore.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.dto.product.UpdateProductRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@NoArgsConstructor
public class ProductController {

  @GetMapping
  public List<ProductResponse> getProducts() {
    throw new UnsupportedOperationException();
  }

  @GetMapping("/{id}")
  public ProductResponse getProduct(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable String id, UpdateProductRequest request) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }

}
