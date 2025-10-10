package io.github.bohdanzhuvak.onlinestore.legacy.features.customer.service;

import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.legacy.features.customer.dto.user.UserResponse;

public interface UserService {
  UserResponse getUser(Long userId);

  UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest);

  void deleteUser(Long userId);
}
