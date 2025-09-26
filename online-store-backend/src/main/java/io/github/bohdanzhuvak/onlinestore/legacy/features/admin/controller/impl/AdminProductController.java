package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.controller.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.Product;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.controller.AdminBaseController;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.product.AdminProductResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.service.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller for User management with react-admin support.
 * Provides full CRUD operations for admin interface.
 */
@RestController
@RequestMapping("/api/v1/admin/products")
@RequiredArgsConstructor
public class AdminProductController extends AdminBaseController<Product, AdminProductRequest, AdminProductResponse, Long> {

  private final AdminProductService adminProductService;

  @Override
  protected AdminProductService getAdminService() {
    return adminProductService;
  }
}
