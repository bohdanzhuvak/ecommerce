package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.product.CreateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.ProductResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.product.UpdateProductRequest;
import io.github.bohdanzhuvak.onlinestore.admin.service.AdminProductService;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController {
  private final AdminProductService adminProductService;

  @PostMapping
  public ProductResponse addProduct(@Valid CreateProductRequest request) {
    return adminProductService.addProduct(request);
  }

  @PutMapping("/{id}")
  public ProductResponse updateProduct(@PathVariable Long id, @Valid UpdateProductRequest request) {
    return adminProductService.updateProduct(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteProduct(@PathVariable Long id) {
    adminProductService.deleteProduct(id);
  }

}
