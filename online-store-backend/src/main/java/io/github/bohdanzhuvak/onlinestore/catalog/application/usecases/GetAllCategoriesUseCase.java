package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;

import java.util.List;

@UseCase
public class GetAllCategoriesUseCase {
  private final CategoryRepository categoryRepository;

  public GetAllCategoriesUseCase(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> execute() {
    return categoryRepository.findAll();
  }

  public List<Category> execute(int offset, int limit) {
    return categoryRepository.findAll(offset, limit);
  }
}
