package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;

import java.util.Optional;

@UseCase
public class GetCategoryUseCase {
  private final CategoryRepository categoryRepository;

  public GetCategoryUseCase(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Optional<Category> execute(CategoryId categoryId) {
    return categoryRepository.findById(categoryId);
  }
}
