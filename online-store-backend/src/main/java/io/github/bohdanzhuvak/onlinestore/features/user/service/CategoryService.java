package io.github.bohdanzhuvak.onlinestore.features.user.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.domain.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.features.user.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.features.user.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  public List<CategoryResponse> getCategories() {
    List<Category> categories = categoryRepository.findAll();
    return categoryMapper.toResponse(categories);
  }
}
