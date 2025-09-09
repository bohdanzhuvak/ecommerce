package io.github.bohdanzhuvak.onlinestore.features.user.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.features.user.dto.category.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryResponse toResponse(Category category);

  List<CategoryResponse> toResponse(List<Category> categories);
}
