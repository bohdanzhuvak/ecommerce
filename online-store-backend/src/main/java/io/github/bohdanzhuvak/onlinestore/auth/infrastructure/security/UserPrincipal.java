package io.github.bohdanzhuvak.onlinestore.auth.infrastructure.security;

import io.github.bohdanzhuvak.onlinestore.auth.domain.Email;
import io.github.bohdanzhuvak.onlinestore.auth.domain.User;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserId;
import io.github.bohdanzhuvak.onlinestore.auth.domain.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record UserPrincipal(UserId id, Email email, UserRole role) implements UserDetails {

  public static UserPrincipal from(User user) {
    return new UserPrincipal(user.getId(), user.getEmail(), user.getRole());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return email.getValue();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}


