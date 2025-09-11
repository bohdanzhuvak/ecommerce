package io.github.bohdanzhuvak.onlinestore.features.admin.controller.impl;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.features.admin.controller.AdminBaseController;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserResponseDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Admin controller for User management with react-admin support.
 * Provides full CRUD operations for admin interface.
 */
@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class AdminUserController extends AdminBaseController<User, AdminUserRequestDto, AdminUserResponseDto, Long> {

  private final AdminUserService adminUserService;

  @Override
  protected AdminUserService getAdminService() {
    return adminUserService;
  }
}
