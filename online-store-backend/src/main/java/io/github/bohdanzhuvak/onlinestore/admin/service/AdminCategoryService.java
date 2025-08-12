package io.github.bohdanzhuvak.onlinestore.admin.service;

import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.UpdateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.admin.mapper.AdminCategoryMapper;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
  private final CategoryRepository categoryRepository;
  private final AdminCategoryMapper categoryMapper;

  public CategoryResponse addCategory(CreateCategoryRequest request) {
    return categoryMapper.toResponse(categoryRepository.save(categoryMapper.createCategory(request)));
  }

  public CategoryResponse updateCategory(Long id, UpdateCategoryRequest request) {
    return categoryRepository.findById(id)
        .map(category -> {
          category.setName(request.getName());
          return categoryMapper.toResponse(categoryRepository.save(category));
        })
        .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
  }

  public void deleteCategory(Long id) {
    if (!categoryRepository.existsById(id)) {
      throw new NotFoundException("Category not found with id: " + id);
    }
    categoryRepository.deleteById(id);
  }
}
