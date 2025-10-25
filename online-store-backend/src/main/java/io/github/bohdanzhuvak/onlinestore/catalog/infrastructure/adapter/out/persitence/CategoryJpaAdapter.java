package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.out.persitence;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class CategoryJpaAdapter implements CategoryRepository {
  private final JpaCategoryRepository jpaCategoryRepository;
  private final CategoryMapper categoryMapper;

  public CategoryJpaAdapter(JpaCategoryRepository jpaCategoryRepository, CategoryMapper categoryMapper) {
    this.jpaCategoryRepository = jpaCategoryRepository;
    this.categoryMapper = categoryMapper;
  }

  @Override
  public Category save(Category category) {
    CategoryEntity entity = categoryMapper.toEntity(category);
    CategoryEntity savedEntity = jpaCategoryRepository.save(entity);
    return categoryMapper.toDomain(savedEntity);
  }

  @Override
  public Optional<Category> findById(CategoryId categoryId) {
    return jpaCategoryRepository.findById(categoryId.getValue())
        .map(categoryMapper::toDomain);
  }

  @Override
  public List<Category> findAll() {
    return jpaCategoryRepository.findAll().stream()
        .map(categoryMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public List<Category> findAll(int offset, int limit) {
    Pageable pageable = PageRequest.of(offset / limit, limit);
    return jpaCategoryRepository.findAll(pageable).stream()
        .map(categoryMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(CategoryId categoryId) {
    jpaCategoryRepository.deleteById(categoryId.getValue());
  }

  @Override
  public long count() {
    return jpaCategoryRepository.count();
  }

  @Override
  public boolean existsById(CategoryId categoryId) {
    return jpaCategoryRepository.existsById(categoryId.getValue());
  }
}
