package io.github.bohdanzhuvak.onlinestore.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.category.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryResponse toResponse(Category category);

  List<CategoryResponse> toResponse(List<Category> categories);
}
