package io.github.bohdanzhuvak.onlinestore.catalog.infrastructure.adapter.out.persitence;

import io.github.bohdanzhuvak.onlinestore.catalog.domain.Category;
import io.github.bohdanzhuvak.onlinestore.catalog.domain.CategoryId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {

  public Category toDomain(CategoryEntity entity) {
    return Category.restore(
        CategoryId.of(entity.getId()),
        entity.getName(),
        entity.getDescription(),
        entity.getCreatedAt(),
        entity.getUpdatedAt()
    );
  }

  public CategoryEntity toEntity(Category category) {
    return new CategoryEntity(
        category.getId().getValue(),
        category.getName(),
        category.getDescription(),
        category.getCreatedAt(),
        category.getUpdatedAt()
    );
  }

  public List<Category> toDomainList(List<CategoryEntity> entities) {
    return entities.stream()
        .map(this::toDomain)
        .collect(Collectors.toList());
  }

  public List<CategoryEntity> toEntityList(List<Category> categories) {
    return categories.stream()
        .map(this::toEntity)
        .collect(Collectors.toList());
  }
}
