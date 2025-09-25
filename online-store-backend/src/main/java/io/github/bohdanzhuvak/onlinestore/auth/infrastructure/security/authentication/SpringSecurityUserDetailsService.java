package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.authentication;

import io.github.bohdanzhuvak.onlinestore.auth.application.port.out.UserInfoPort;
import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpringSecurityUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

  private final UserInfoPort userInfoPort;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userInfoPort.findByEmail(Email.of(email));
    return UserPrincipal.from(user);
  }
}


