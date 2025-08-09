package io.github.bohdanzhuvak.onlinestore.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue
  private Long id;
  private String username;
  private String password;
  private String email;
  private Role role;
}
