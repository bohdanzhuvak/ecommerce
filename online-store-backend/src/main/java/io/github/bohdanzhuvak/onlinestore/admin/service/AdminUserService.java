package io.github.bohdanzhuvak.onlinestore.admin.service;

import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UpdateUserRequest;
import io.github.bohdanzhuvak.onlinestore.admin.dto.user.UserResponse;
import io.github.bohdanzhuvak.onlinestore.admin.mapper.AdminUserMapper;
import io.github.bohdanzhuvak.onlinestore.common.exception.impl.NotFoundException;
import io.github.bohdanzhuvak.onlinestore.common.model.User;
import io.github.bohdanzhuvak.onlinestore.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminUserService {
  private final UserRepository userRepository;
  private final AdminUserMapper adminUserMapper;

  public Page<UserResponse> getUsers(Pageable pageable) {
    Page<User> userPage = userRepository.findAll(pageable);
    return adminUserMapper.toResponsePage(userPage);
  }

  public UserResponse updateUser(Long userId, UpdateUserRequest updateUserRequest) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

    user = adminUserMapper.updateUser(user, updateUserRequest);
    user = userRepository.save(user);

    return adminUserMapper.toResponse(user);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
