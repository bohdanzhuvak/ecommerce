package io.github.bohdanzhuvak.onlinestore.features.admin.mapper;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Admin mapper for User entity with react-admin support.
 * Handles mapping between User entity and admin DTOs.
 */
@Mapper(componentModel = "spring")
public interface AdminUserMapper extends AdminBaseMapper<User, AdminUserResponseDto, AdminUserRequestDto> {

  @Override
  @Mapping(target = "id", source = "id")
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "role", source = "role")
  @Mapping(target = "balance", source = "balance")
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "lastLoginAt", ignore = true)
  @Mapping(target = "totalOrders", ignore = true)
  @Mapping(target = "totalSpent", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "metadata", ignore = true)
  @Mapping(target = "editable", ignore = true)
  @Mapping(target = "deletable", ignore = true)
  @Mapping(target = "displayName", ignore = true)
  AdminUserResponseDto toResponseDto(User entity);

  @Override
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "role", source = "role")
  @Mapping(target = "balance", source = "balance")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "id", ignore = true)
  User toEntity(AdminUserRequestDto dto);

  @Override
  @Mapping(target = "username", source = "username")
  @Mapping(target = "email", source = "email")
  @Mapping(target = "role", source = "role")
  @Mapping(target = "balance", source = "balance")
  @Mapping(target = "password", ignore = true)
  @Mapping(target = "id", ignore = true)
  void updateEntity(@MappingTarget User entity, AdminUserRequestDto dto);

  /**
   * Generate display name for user
   *
   * @param user User entity
   * @return Display name
   */
  /*@Override
  default String generateDisplayName(User user) {
    return user.getUsername() + " (" + user.getEmail() + ")";
  }*/
}
