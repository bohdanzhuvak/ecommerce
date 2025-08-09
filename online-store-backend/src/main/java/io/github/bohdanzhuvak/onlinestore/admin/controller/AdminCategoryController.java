package io.github.bohdanzhuvak.onlinestore.admin.controller;

import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.UpdateCategoryRequest;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/categories")
@NoArgsConstructor
public class AdminCategoryController {

  @PostMapping
  public CategoryResponse addCategory(@RequestBody @Valid CreateCategoryRequest request) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/{id}")
  public CategoryResponse updateCategory(@PathVariable String id, @RequestBody @Valid UpdateCategoryRequest request) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }
}
