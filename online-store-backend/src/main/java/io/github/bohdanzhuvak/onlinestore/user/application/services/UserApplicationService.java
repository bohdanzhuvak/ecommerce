package io.github.bohdanzhuvak.onlinestore.user.application.services;

import io.github.bohdanzhuvak.onlinestore.user.application.usecases.ActivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.ChangePasswordUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.DeactivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.GetAllUsersUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.GetUserProfileByIdUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecases.UpdateUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserApplicationService {
  private final CreateUserUseCase createUserUseCase;
  private final GetUserProfileByIdUseCase getUserProfileByIdUseCase;
  private final UpdateUserProfileUseCase updateUserProfileUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final GetAllUsersUseCase getAllUsersUseCase;
  private final ActivateUserUseCase activateUserUseCase;
  private final DeactivateUserUseCase deactivateUserUseCase;

  public UserApplicationService(CreateUserUseCase createUserUseCase,
                                GetUserProfileByIdUseCase getUserProfileByIdUseCase,
                                UpdateUserProfileUseCase updateUserProfileUseCase,
                                ChangePasswordUseCase changePasswordUseCase,
                                GetAllUsersUseCase getAllUsersUseCase,
                                ActivateUserUseCase activateUserUseCase,
                                DeactivateUserUseCase deactivateUserUseCase) {
    this.getUserProfileByIdUseCase = getUserProfileByIdUseCase;
    this.updateUserProfileUseCase = updateUserProfileUseCase;
    this.changePasswordUseCase = changePasswordUseCase;
    this.createUserUseCase = createUserUseCase;
    this.getAllUsersUseCase = getAllUsersUseCase;
    this.activateUserUseCase = activateUserUseCase;
    this.deactivateUserUseCase = deactivateUserUseCase;
  }

  // Customer operations
  public User registerUser(Email email, Password password, String firstName, String lastName) {
    CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(
        email, password, firstName, lastName);
    return createUserUseCase.execute(command);
  }

  public User getUserProfile(UserId userId) {
    return getUserProfileByIdUseCase.execute(userId);
  }

  public User updateUserProfile(UserId userId, String firstName, String lastName) {
    UpdateUserProfileUseCase.UpdateUserProfileCommand command = new UpdateUserProfileUseCase.UpdateUserProfileCommand(
        userId, firstName, lastName);
    return updateUserProfileUseCase.execute(command);
  }

  public User changePassword(UserId userId, Password currentPassword, Password newPassword) {
    ChangePasswordUseCase.ChangePasswordCommand command = new ChangePasswordUseCase.ChangePasswordCommand(
        userId, currentPassword, newPassword);
    return changePasswordUseCase.execute(command);
  }

  // Admin operations
  public User createUser(Email email, Password password, String firstName, String lastName, UserRole role) {
    CreateUserUseCase.CreateUserCommand command = new CreateUserUseCase.CreateUserCommand(
        email, password, firstName, lastName);
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
