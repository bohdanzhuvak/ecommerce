package io.github.bohdanzhuvak.onlinestore.features.admin.service;

import io.github.bohdanzhuvak.onlinestore.domain.model.User;
import io.github.bohdanzhuvak.onlinestore.domain.repository.UserRepository;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserRequestDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.dto.user.AdminUserResponseDto;
import io.github.bohdanzhuvak.onlinestore.features.admin.mapper.AdminUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

  @Override
  protected Page<AdminUserResponseDto> findByFiltersWithPagination(Map<String, String> filters, Pageable pageable) {
    // Implement user-specific filtering logic here
    // For now, return all users (can be enhanced with specific filters)
    Page<User> users = userRepository.findAll(pageable);
    return users.map(adminUserMapper::toResponseDto);
  }

  @Override
  public List<AdminUserResponseDto> findByFilters(Map<String, String> filters) {
    // Implement user-specific filtering logic here
    // For now, return all users (can be enhanced with specific filters)
    List<User> users = userRepository.findAll();
    return adminUserMapper.toResponseDtoList(users);
  }

  @Override
  public boolean hasPermission(Long entityId, String operation) {
    // Implement user-specific permission logic here
    // For now, allow all operations (can be enhanced with role-based permissions)
    return true;
  }

  @Override
  public Map<String, Object> getStatistics() {
    Map<String, Object> stats = super.getStatistics();

    // Add user-specific statistics
    long activeUsers = userRepository.findAll().stream()
        .filter(user -> user.getBalance().compareTo(java.math.BigDecimal.ZERO) >= 0)
        .count();

    stats.put("activeUsers", activeUsers);
    stats.put("totalUsers", userRepository.count());

    return stats;
  }
}
