package io.github.bohdanzhuvak.onlinestore.features.admin.controller.impl;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.UpdateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminCategoryService;
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
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
  private final AdminCategoryService adminCategoryService;

  @GetMapping
  public Page<CategoryResponse> getCategories(Pageable pageable) {
    return adminCategoryService.getCategories(pageable);
  }

  @GetMapping("/{id}")
  public CategoryResponse getCategory(@PathVariable Long id) {
    return adminCategoryService.getCategory(id);
  }

  @PostMapping
  public CategoryResponse addCategory(@RequestBody @Valid CreateCategoryRequest request) {
    return adminCategoryService.addCategory(request);
  }

  @PutMapping("/{id}")
  public CategoryResponse updateCategory(@PathVariable Long id, @RequestBody @Valid UpdateCategoryRequest request) {
    return adminCategoryService.updateCategory(id, request);
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable Long id) {
    adminCategoryService.deleteCategory(id);
  }
}
