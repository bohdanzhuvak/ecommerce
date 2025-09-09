package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.UpdateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.mapper.AdminCategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCategoryService {
  private final CategoryRepository categoryRepository;
  private final AdminCategoryMapper categoryMapper;

  public Page<CategoryResponse> getCategories(Pageable pageable) {
    Page<Category> categoryPage = categoryRepository.findAll(pageable);
    return categoryMapper.toResponsePage(categoryPage);
  }

  public CategoryResponse getCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category not found with id: " + id));
    return categoryMapper.toResponse(category);
  }

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
