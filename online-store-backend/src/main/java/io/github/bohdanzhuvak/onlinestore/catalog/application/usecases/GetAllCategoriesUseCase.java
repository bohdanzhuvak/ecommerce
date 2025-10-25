package io.github.bohdanzhuvak.onlinestore.catalog.application.usecases;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
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

  public PageResult<Category> execute(int page, int pageSize) {
    // React-admin sends 1-based page numbers, convert to 0-based offset
    int offset = (page - 1) * pageSize;
    List<Category> categories = categoryRepository.findAll(offset, pageSize);
    long total = categoryRepository.count();
    return PageResult.of(categories, total, page, pageSize);
  }
}
