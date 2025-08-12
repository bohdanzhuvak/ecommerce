package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.UpdateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.admin.service.AdminCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
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
