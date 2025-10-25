package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.UseCase;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;

@UseCase
public class CreateCategoryUseCase {
  private final CategoryRepository categoryRepository;

  public CreateCategoryUseCase(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public Category execute(CreateCategoryCommand command) {
    CategoryId categoryId = CategoryId.generate();
    Category category = new Category(categoryId, command.name(), command.description());
    return categoryRepository.save(category);
  }

  public record CreateCategoryCommand(
      String name,
      String description
  ) {
  }
}
