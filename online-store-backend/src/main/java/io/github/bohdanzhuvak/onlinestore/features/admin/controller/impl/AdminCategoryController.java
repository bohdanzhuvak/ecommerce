package io.github.bohdanzhuvak.onlinestore.features.admin.controller.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.features.admin.controller.AdminBaseController;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController extends AdminBaseController<Category, AdminCategoryRequest, AdminCategoryResponse, Long> {
  private final AdminCategoryService adminCategoryService;


  @Override
  protected AdminCategoryService getAdminService() {
    return adminCategoryService;
  }
}
