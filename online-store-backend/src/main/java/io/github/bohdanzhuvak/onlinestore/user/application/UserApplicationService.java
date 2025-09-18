package io.github.bohdanzhuvak.onlinestore.user.application;

import io.github.bohdanzhuvak.onlinestore.user.application.admin.ActivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.admin.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.admin.DeactivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.admin.GetAllUsersUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.customer.ChangePasswordUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.customer.GetUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.customer.RegisterUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.customer.UpdateUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserApplicationService {
  private final RegisterUserUseCase registerUserUseCase;
  private final GetUserProfileUseCase getUserProfileUseCase;
  private final UpdateUserProfileUseCase updateUserProfileUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final CreateUserUseCase createUserUseCase;
  private final GetAllUsersUseCase getAllUsersUseCase;
  private final ActivateUserUseCase activateUserUseCase;
  private final DeactivateUserUseCase deactivateUserUseCase;

  public UserApplicationService(RegisterUserUseCase registerUserUseCase,
                                GetUserProfileUseCase getUserProfileUseCase,
                                UpdateUserProfileUseCase updateUserProfileUseCase,
                                ChangePasswordUseCase changePasswordUseCase,
                                CreateUserUseCase createUserUseCase,
                                GetAllUsersUseCase getAllUsersUseCase,
                                ActivateUserUseCase activateUserUseCase,
                                DeactivateUserUseCase deactivateUserUseCase) {
    this.registerUserUseCase = registerUserUseCase;
    this.getUserProfileUseCase = getUserProfileUseCase;
    this.updateUserProfileUseCase = updateUserProfileUseCase;
    this.changePasswordUseCase = changePasswordUseCase;
    this.createUserUseCase = createUserUseCase;
    this.getAllUsersUseCase = getAllUsersUseCase;
    this.activateUserUseCase = activateUserUseCase;
    this.deactivateUserUseCase = deactivateUserUseCase;
  }

  // Customer operations
  public User registerUser(Email email, String password, String firstName, String lastName) {
    RegisterUserUseCase.RegisterUserCommand command = new RegisterUserUseCase.RegisterUserCommand(
        email, password, firstName, lastName);
    return registerUserUseCase.execute(command);
  }

  public Optional<User> getUserProfile(UserId userId) {
    return getUserProfileUseCase.execute(userId);
  }

  public User updateUserProfile(UserId userId, String firstName, String lastName) {
    UpdateUserProfileUseCase.UpdateUserProfileCommand command = new UpdateUserProfileUseCase.UpdateUserProfileCommand(
        userId, firstName, lastName);
    return updateUserProfileUseCase.execute(command);
  }

  public User changePassword(UserId userId, String currentPassword, String newPassword) {
    ChangePasswordUseCase.ChangePasswordCommand command = new ChangePasswordUseCase.ChangePasswordCommand(
        userId, currentPassword, newPassword);
    return changePasswordUseCase.execute(command);
  }

  // Admin operations
  public User createUser(Email email, String password, String firstName, String lastName, UserRole role) {
    CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(
        email, password, firstName, lastName, role);
    return createUserUseCase.execute(command);
  }

  public List<User> getAllUsers(UserRole role, boolean activeOnly, int offset, int limit) {
    GetAllUsersUseCase.GetAllUsersCommand command = new GetAllUsersUseCase.GetAllUsersCommand(
        role, activeOnly, offset, limit);
    return getAllUsersUseCase.execute(command);
  }

  public User activateUser(UserId userId) {
    return activateUserUseCase.execute(userId);
  }

  public User deactivateUser(UserId userId) {
    return deactivateUserUseCase.execute(userId);
  }
}
