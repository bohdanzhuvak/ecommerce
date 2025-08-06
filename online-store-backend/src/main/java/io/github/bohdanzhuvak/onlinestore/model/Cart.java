package io.github.bohdanzhuvak.onlinestore.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

@Entity
public class Cart {
  @Id
  @GeneratedValue
  private Long id;

  @OneToOne
  private User user;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  private List<CartItem> items;
}
