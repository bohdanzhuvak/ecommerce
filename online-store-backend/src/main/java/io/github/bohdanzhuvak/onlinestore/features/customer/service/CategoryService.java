package io.github.bohdanzhuvak.onlinestore.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.features.customer.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
  List<CategoryResponse> getCategories();
}
