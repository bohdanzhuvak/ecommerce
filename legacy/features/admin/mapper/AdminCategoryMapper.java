package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.category.AdminCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.category.AdminCategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AdminCategoryMapper extends AdminBaseMapper<Category, AdminCategoryResponse, AdminCategoryRequest> {

  @Override
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "createdAt", source = "createdAt")
  @Mapping(target = "updatedAt", source = "updatedAt")
  @Mapping(target = "metadata", ignore = true)
  @Mapping(target = "editable", ignore = true)
  @Mapping(target = "deletable", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  AdminCategoryResponse toResponseDto(Category entity);

  @Override
  @Mapping(target = "name", source = "name")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "products", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  Category toEntity(AdminCategoryRequest dto);

  @Override
  @Mapping(target = "name", source = "name")
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "products", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  void updateEntity(@MappingTarget Category entity, AdminCategoryRequest dto);
}
