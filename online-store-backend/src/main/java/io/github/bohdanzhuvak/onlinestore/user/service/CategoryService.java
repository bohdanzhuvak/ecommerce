package io.github.bohdanzhuvak.onlinestore.user.service;

import io.github.bohdanzhuvak.onlinestore.common.model.Category;
import io.github.bohdanzhuvak.onlinestore.common.repository.CategoryRepository;
import io.github.bohdanzhuvak.onlinestore.user.dto.category.CategoryResponse;
import io.github.bohdanzhuvak.onlinestore.user.mapper.CategoryMapper;
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
