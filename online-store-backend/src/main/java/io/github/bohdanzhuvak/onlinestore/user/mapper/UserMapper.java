package io.github.bohdanzhuvak.onlinestore.user.mapper;

import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.user.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.user.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
  UserResponse toResponse(User user);


  @Mapping(target = "id", ignore = true)
  @Mapping(target = "password", ignore = true)
  User updateUser(@MappingTarget User user, UpdateUserRequest userRequest);
}
