package io.github.bohdanzhuvak.onlinestore.legacy.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.user.AdminUserRequestDto;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.dto.user.AdminUserResponseDto;
import io.github.bohdanzhuvak.onlinestore.legacy.features.admin.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Admin service for User management with react-admin support.
 * Extends AbstractAdminFullAccessService with user-specific admin operations.
 */
@Service
@RequiredArgsConstructor
public class AdminUserService extends AbstractAdminFullAccessService<User, AdminUserResponseDto, AdminUserRequestDto, Long> {

  private final UserRepository userRepository;
  private final AdminUserMapper adminUserMapper;

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }

  @Override
  protected AdminUserMapper getMapper() {
    return adminUserMapper;
  }
}
