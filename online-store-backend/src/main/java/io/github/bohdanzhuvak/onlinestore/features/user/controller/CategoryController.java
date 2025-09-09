package io.github.bohdanzhuvak.onlinestore.features.user.controller;

import io.github.bohdanzhuvak.onlinestore.features.user.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.user.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;

  @GetMapping
  public List<CategoryResponse> getCategories() {
    return categoryService.getCategories();
  }
}
