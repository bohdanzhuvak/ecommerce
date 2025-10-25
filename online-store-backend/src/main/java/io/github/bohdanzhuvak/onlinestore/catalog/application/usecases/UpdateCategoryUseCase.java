package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.ResourceNotFoundException;
import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;

@UseCase
public class UpdateCategoryUseCase {
  private final CategoryRepository categoryRepository;

  public UpdateCategoryUseCase(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category execute(UpdateCategoryCommand command) {
    Category category = categoryRepository.findById(command.categoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Category", command.categoryId()));

    if (command.name() != null) {
      category.updateName(command.name());
    }

    if (command.description() != null) {
      category.updateDescription(command.description());
    }

    return categoryRepository.save(category);
  }

  public record UpdateCategoryCommand(
      CategoryId categoryId,
      String name,
      String description
  ) {
  }
}
