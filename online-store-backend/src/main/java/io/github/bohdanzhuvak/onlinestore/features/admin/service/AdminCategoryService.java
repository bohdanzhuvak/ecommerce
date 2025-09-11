package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.mapper.AdminCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService extends AbstractAdminFullAccessService<Category, AdminCategoryResponse, AdminCategoryRequest, Long> {
  private final CategoryRepository categoryRepository;
  private final AdminCategoryMapper categoryMapper;


  @Override
  protected CategoryRepository getRepository() {
    return categoryRepository;
  }

  @Override
  protected AdminCategoryMapper getMapper() {
    return categoryMapper;
  }
}
