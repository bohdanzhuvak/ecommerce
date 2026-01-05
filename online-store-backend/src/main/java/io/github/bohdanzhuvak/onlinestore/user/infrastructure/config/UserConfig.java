package io.github.bohdanzhuvak.onlinestore.user.infrastructure.config;

import io.github.bohdanzhuvak.onlinestore.user.application.port.in.ExternalUserOrchestrator;
import io.github.bohdanzhuvak.onlinestore.user.application.service.ExternalUserOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.user.application.service.WebUserOrchestratorService;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ActivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.AddDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ChangePasswordUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.CreateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeactivateUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeleteDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.DeleteUserUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetAllUsersUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetDeliveryAddressListUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByEmailUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.GetUserProfileByIdUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.UpdateDeliveryAddressUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.UpdateUserProfileUseCase;
import io.github.bohdanzhuvak.onlinestore.user.application.usecase.ValidateCredentialsUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

  @Bean
  public ExternalUserOrchestrator externalUserOrchestrator(
      CreateUserUseCase createUserUseCase,
      ValidateCredentialsUseCase validateCredentialsUseCase,
      GetUserProfileByEmailUseCase getUserProfileByEmailUseCase,
      GetUserProfileByIdUseCase getUserProfileByIdUseCase,
      GetDeliveryAddressUseCase getDeliveryAddressUseCase
  ) {
    return new ExternalUserOrchestratorService(
        createUserUseCase,
        validateCredentialsUseCase,
        getUserProfileByEmailUseCase,
        getUserProfileByIdUseCase,
        getDeliveryAddressUseCase
    );
  }

  @Bean
  public WebUserOrchestratorService webUserOrchestratorService(
      CreateUserUseCase createUserUseCase,
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
    return new WebUserOrchestratorService(
        createUserUseCase,
        getUserProfileByIdUseCase,
        updateUserProfileUseCase,
        changePasswordUseCase,
        getAllUsersUseCase,
        activateUserUseCase,
        deactivateUserUseCase,
        getDeliveryAddressListUseCase,
        deleteDeliveryAddressUseCase,
        addDeliveryAddressUseCase,
        updateDeliveryAddressUseCase,
        deleteUserUseCase
    );
  }
}
