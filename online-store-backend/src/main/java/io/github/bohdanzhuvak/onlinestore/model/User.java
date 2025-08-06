package io.github.bohdanzhuvak.onlinestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class User {
  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;
  private String email;
  private Role role;
}
