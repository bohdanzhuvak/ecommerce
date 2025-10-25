package io.github.bohdanzhuvak.onlinestore.user.application.service;

import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByEmailUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByIdUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ValidateCredentialsUseCase;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddress;
import io.github.bohdanzhuvak.onlinestore.user.domain.DeliveryAddressId;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;

public class ExternalUserOrchestratorService implements ExternalUserOrchestrator {
  private final CreateUserUseCase createUserUseCase;
  private final ValidateCredentialsUseCase validateCredentialsUseCase;
  private final GetUserProfileByEmailUseCase getUserProfileByEmailUseCase;
  private final GetUserProfileByIdUseCase getUserProfileByIdUseCase;
  private final GetDeliveryAddressUseCase getDeliveryAddressUseCase;

  public ExternalUserOrchestratorService(CreateUserUseCase createUserUseCase,
                                         ValidateCredentialsUseCase validateCredentialsUseCase,
                                         GetUserProfileByEmailUseCase getUserProfileByEmailUseCase,
                                         GetUserProfileByIdUseCase getUserProfileByIdUseCase,
                                         GetDeliveryAddressUseCase getDeliveryAddressUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.validateCredentialsUseCase = validateCredentialsUseCase;
    this.getUserProfileByEmailUseCase = getUserProfileByEmailUseCase;
    this.getUserProfileByIdUseCase = getUserProfileByIdUseCase;
    this.getDeliveryAddressUseCase = getDeliveryAddressUseCase;
  }

  @Override
  public UserInfo findByEmail(Email email) {
    return toUserInfo(getUserProfileByEmailUseCase.execute(email));
  }

  @Override
  public UserInfo findById(UserId userId) {
    return toUserInfo(getUserProfileByIdUseCase.execute(userId));
  }

  @Override
  public boolean existsByEmail(Email email) {
    return false;
  }

  @Override
  public UserInfo createUser(Email email, Password password, String firstName, String lastName) {
    return toUserInfo(createUserUseCase.execute(new CreateUserUseCase.CreateUserCommand(email, password, firstName, lastName)));
  }

  @Override
  public boolean validateCredentials(Email email, String rawPassword) {
    return validateCredentialsUseCase.execute(email, rawPassword);
  }

  @Override
  public DeliveryAddress getDeliveryAddressById(UserId userId, DeliveryAddressId addressId) {
    GetDeliveryAddressUseCase.GetDeliveryAddressCommand command = new GetDeliveryAddressUseCase.GetDeliveryAddressCommand(userId, addressId);
    return getDeliveryAddressUseCase.execute(command);
  }

  private UserInfo toUserInfo(User user) {
    return new UserInfo(
        user.getId(),
        user.getEmail(),
        user.getRole(),
        user.getFirstName(),
        user.getLastName(),
        user.isActive()
    );
  }
}
