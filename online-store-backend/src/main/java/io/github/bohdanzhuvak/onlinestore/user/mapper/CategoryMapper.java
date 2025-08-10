package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.common.model.Category;
import io.github.bohdanzhuvak.onlinestore.user.dto.category.CategoryResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryResponse toResponse(Category category);

  List<CategoryResponse> toResponse(List<Category> categories);
}
