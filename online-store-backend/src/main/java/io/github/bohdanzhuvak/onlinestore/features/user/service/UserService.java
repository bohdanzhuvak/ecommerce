package io.github.bohdanzhuvak.onlinestore.features.user.service;

import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.features.user.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.features.user.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.features.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserResponse getUser(Long userId) {
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    return userMapper.toResponse(user);
  }

  public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

    user = userMapper.updateUser(user, updateUserRequest);
    user = userRepository.save(user);

    return userMapper.toResponse(user);
  }

  public void deleteUser(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new NotFoundException("User not found with id: " + userId);
    }

    userRepository.deleteById(userId);
  }

}
