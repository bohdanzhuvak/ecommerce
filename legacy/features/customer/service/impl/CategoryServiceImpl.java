package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.mapper.CategoryMapper;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<CategoryResponse> getCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categoryMapper.toResponse(categories);
  }
}
