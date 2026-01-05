package io.github.bohdanzhuvak.onlinestore.user.application.service;

import io.github.bohdanzhuvak.onlinestore.architecture.PageResult;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ActivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.AddDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ChangePasswordUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeactivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeleteDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeleteUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetAllUsersUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetDeliveryAddressListUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByIdUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.UpdateDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.UpdateUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserRole;

import java.util.List;

public class WebUserOrchestratorService {
  private final CreateUserUseCase createUserUseCase;
  private final GetUserProfileByIdUseCase getUserProfileByIdUseCase;
  private final UpdateUserProfileUseCase updateUserProfileUseCase;
  private final ChangePasswordUseCase changePasswordUseCase;
  private final GetAllUsersUseCase getAllUsersUseCase;
  private final ActivateUserUseCase activateUserUseCase;
  private final DeactivateUserUseCase deactivateUserUseCase;
  private final GetDeliveryAddressListUseCase getDeliveryAddressListUseCase;
  private final DeleteDeliveryAddressUseCase deleteDeliveryAddressUseCase;
  private final AddDeliveryAddressUseCase addDeliveryAddressUseCase;
  private final UpdateDeliveryAddressUseCase updateDeliveryAddressUseCase;
  private final DeleteUserUseCase deleteUserUseCase;

  public WebUserOrchestratorService(CreateUserUseCase createUserUseCase,
                                    GetUserProfileByIdUseCase getUserProfileByIdUseCase,
                                    UpdateUserProfileUseCase updateUserProfileUseCase,
                                    ChangePasswordUseCase changePasswordUseCase,
                                    GetAllUsersUseCase getAllUsersUseCase,
                                    ActivateUserUseCase activateUserUseCase,
                                    DeactivateUserUseCase deactivateUserUseCase,
                                    GetDeliveryAddressListUseCase getDeliveryAddressListUseCase,
                                    DeleteDeliveryAddressUseCase deleteDeliveryAddressUseCase,
                                    AddDeliveryAddressUseCase addDeliveryAddressUseCase,
                                    UpdateDeliveryAddressUseCase updateDeliveryAddressUseCase,
                                    DeleteUserUseCase deleteUserUseCase) {
    this.getUserProfileByIdUseCase = getUserProfileByIdUseCase;
    this.updateUserProfileUseCase = updateUserProfileUseCase;
    this.changePasswordUseCase = changePasswordUseCase;
    this.createUserUseCase = createUserUseCase;
    this.getAllUsersUseCase = getAllUsersUseCase;
    this.activateUserUseCase = activateUserUseCase;
    this.deactivateUserUseCase = deactivateUserUseCase;
    this.getDeliveryAddressListUseCase = getDeliveryAddressListUseCase;
    this.deleteDeliveryAddressUseCase = deleteDeliveryAddressUseCase;
    this.addDeliveryAddressUseCase = addDeliveryAddressUseCase;
    this.updateDeliveryAddressUseCase = updateDeliveryAddressUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
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

  public PageResult<User> getAllUsers(UserRole role, boolean activeOnly, int page, int pageSize) {
    GetAllUsersUseCase.GetAllUsersCommand command = new GetAllUsersUseCase.GetAllUsersCommand(
        role, activeOnly, page, pageSize);
    return getAllUsersUseCase.execute(command);
  }

  public User activateUser(UserId userId) {
    return activateUserUseCase.execute(userId);
  }

  public User deactivateUser(UserId userId) {
    return deactivateUserUseCase.execute(userId);
  }

  public List<DeliveryAddress> getDeliveryAddresses(UserId userId) {
    return getDeliveryAddressListUseCase.execute(userId);
  }

  public void deleteDeliveryAddress(UserId userId, DeliveryAddressId deliveryAddressId) {
    DeleteDeliveryAddressUseCase.DeleteDeliveryAddressCommand command = new DeleteDeliveryAddressUseCase.DeleteDeliveryAddressCommand(userId, deliveryAddressId);
    deleteDeliveryAddressUseCase.execute(command);
  }

  public DeliveryAddress addDeliveryAddress(UserId userId, DeliveryAddress deliveryAddress, boolean isDefault) {
    AddDeliveryAddressUseCase.AddDeliveryAddressCommand command = new AddDeliveryAddressUseCase.AddDeliveryAddressCommand(userId, deliveryAddress, isDefault);
    return addDeliveryAddressUseCase.execute(command);
  }

  public DeliveryAddress updateDeliveryAddress(UserId userId, DeliveryAddress deliveryAddress, boolean isDefault) {
    UpdateDeliveryAddressUseCase.UpdateDeliveryAddressCommand command = new UpdateDeliveryAddressUseCase.UpdateDeliveryAddressCommand(userId, deliveryAddress, isDefault);
    return updateDeliveryAddressUseCase.execute(command);
  }

  public void deleteUserProfile(UserId userId) {
    deleteUserUseCase.execute(userId);
  }
}
