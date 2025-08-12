package io.github.bohdanzhuvak.onlinestore.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AdminUserMapper {
  UserResponse toResponse(User user);

  @Mapping(target = "password", ignore = true)
  @Mapping(target = "id", ignore = true)
  User updateUser(@MappingTarget User user, UpdateUserRequest userRequest);

  default Page<UserResponse> toResponsePage(Page<User> userPage) {
    return userPage.map(this::toResponse);
  }
}
