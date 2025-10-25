package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;

@UseCase
public class DeleteCategoryUseCase {
  private final CategoryRepository categoryRepository;

  public DeleteCategoryUseCase(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public void execute(CategoryId categoryId) {
    if (!categoryRepository.existsById(categoryId)) {
      throw new ResourceNotFoundException("Category", categoryId);
    }
    categoryRepository.deleteById(categoryId);
  }
}
