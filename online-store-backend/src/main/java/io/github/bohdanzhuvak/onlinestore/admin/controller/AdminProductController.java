package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
  private final AdminProductService adminProductService;

  @GetMapping
  public Page<ProductResponse> getProducts(Pageable pageable) {
    return adminProductService.getProducts(pageable);
  }

  @GetMapping("/{id}")
  public ProductResponse getProduct(@PathVariable Long id) {
    return adminProductService.getProduct(id);
  }

  @PostMapping
  public ProductResponse addProduct(@Valid @RequestBody CreateProductRequest request) {
    return adminProductService.addProduct(request);
  }

  @PutMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest request) {
    return adminProductService.updateProduct(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    adminProductService.deleteProduct(id);
  }

}
