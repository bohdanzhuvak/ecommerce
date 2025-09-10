package io.github.bohdanzhuvak.onlinestore.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UserResponse;
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
