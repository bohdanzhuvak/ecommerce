package io.github.bohdanzhuvak.onlinestore.features.customer.mapper;

import io.github.bohdanzhuvak.onlinestore.common.mapper.BaseMapper;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * User mapper implementation using MapStruct.
 * This shows how to implement BaseMapper for specific entity types.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {

  @Override
  UserDto toDto(User entity);

  @Override
  @Mapping(target = "password", ignore = true)
    // Don't map password from DTO
  User toEntity(UserDto dto);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "password", ignore = true)
    // Don't map password from DTO
  User toEntityForCreate(UserDto dto);

  @Override
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "password", ignore = true)
    // Don't update password via DTO
  void updateEntity(@MappingTarget User entity, UserDto dto);

  @Override
  default Page<UserDto> toDtoPage(Page<User> entityPage) {
    return entityPage.map(this::toDto);
  }

  @Override
  default List<UserDto> toDtoList(List<User> entities) {
    return entities.stream().map(this::toDto).toList();
  }

  @Override
  default List<User> toEntityList(List<UserDto> dtos) {
    return dtos.stream().map(this::toEntity).toList();
  }
}
