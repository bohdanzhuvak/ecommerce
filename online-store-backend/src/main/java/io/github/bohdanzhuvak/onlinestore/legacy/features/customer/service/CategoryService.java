package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.category.CategoryResponse;

import java.util.List;

public interface CategoryService {
  List<CategoryResponse> getCategories();
}
