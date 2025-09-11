package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.Category;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryRequest;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.category.AdminCategoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminCategoryMapper extends AdminBaseMapper<Category, AdminCategoryResponse, AdminCategoryRequest> {

}
