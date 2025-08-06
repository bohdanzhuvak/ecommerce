package io.github.bohdanzhuvak.onlinestore.controller;

import io.github.bohdanzhuvak.onlinestore.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.dto.category.UpdateCategoryRequest;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@NoArgsConstructor
public class CategoryController {

  @GetMapping
  public String getCategories() {
    throw new UnsupportedOperationException();
  }

  @PostMapping
  public String addCategory(@RequestBody CreateCategoryRequest request) {
    throw new UnsupportedOperationException();
  }

  @PutMapping("/{id}")
  public String updateCategory(@PathVariable String id, @RequestBody UpdateCategoryRequest request) {
    throw new UnsupportedOperationException();
  }

  @DeleteMapping("/{id}")
  public String deleteCategory(@PathVariable String id) {
    throw new UnsupportedOperationException();
  }
}
