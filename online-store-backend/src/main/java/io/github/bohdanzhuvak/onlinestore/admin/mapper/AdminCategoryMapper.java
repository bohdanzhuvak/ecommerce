package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CreateCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.common.model.Category;
import io.github.bohdanzhuvak.onlinestore.admin.dto.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminCategoryMapper {
  CategoryResponse toResponse(Category category);

  Category createCategory(CreateCategoryRequest categoryRequest);

  List<CategoryResponse> toResponse(List<Category> categories);
}
