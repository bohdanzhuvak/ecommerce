package io.github.bohdanzhuvak.onlinestore.user.application.service;

import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByEmailUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByIdUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ValidateCredentialsUseCase;
import io.github.bohdanzhuvak.onlinestore.user.domain.Email;
import io.github.bohdanzhuvak.onlinestore.user.domain.Password;
import io.github.bohdanzhuvak.onlinestore.user.domain.User;
import io.github.bohdanzhuvak.onlinestore.user.domain.UserId;
import org.springframework.stereotype.Service;

@Service
public class ExternalUserOrchestratorService implements ExternalUserOrchestrator {
  private final CreateUserUseCase createUserUseCase;
  private final ValidateCredentialsUseCase validateCredentialsUseCase;
  GetUserProfileByEmailUseCase getUserProfileByEmailUseCase;
  GetUserProfileByIdUseCase getUserProfileByIdUseCase;

  public ExternalUserOrchestratorService(CreateUserUseCase createUserUseCase, ValidateCredentialsUseCase validateCredentialsUseCase) {
    this.createUserUseCase = createUserUseCase;
    this.validateCredentialsUseCase = validateCredentialsUseCase;
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
  public boolean validateCredentials(Email email, Password password) {
    return validateCredentialsUseCase.execute(email, password);
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
