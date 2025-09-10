package io.github.bohdanzhuvak.onlinestore.features.customer.controller;

import io.github.bohdanzhuvak.onlinestore.common.controller.BaseController;
import io.github.bohdanzhuvak.onlinestore.common.mapper.BaseMapper;
import io.github.bohdanzhuvak.onlinestore.common.service.BaseService;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.features.customer.dto.user.UserDto;
import io.github.bohdanzhuvak.onlinestore.features.customer.mapper.UserMapper;
import io.github.bohdanzhuvak.onlinestore.features.customer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Example of concrete controller implementation using BaseController.
 * This shows how to implement a controller for specific entity types.
 */
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController extends BaseController<User, UserDto, Long> {

  private final UserService userService;
  private final UserMapper userMapper;

  @Override
  protected BaseService<User, Long> getService() {
    return userService;
  }

  @Override
  protected BaseMapper<User, UserDto> getMapper() {
    return userMapper;
  }
}
