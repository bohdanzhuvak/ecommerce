package io.github.bohdanzhuvak.onlinestore.domain.factory;

import io.github.bohdanzhuvak.onlinestore.domain.model.Cart;
import io.github.bohdanzhuvak.onlinestore.domain.model.User;

import java.util.ArrayList;

public class CartFactory {
  private CartFactory() {
  }

  public static Cart create(User user) {
    return Cart.builder()
        .user(user)
        .items(new ArrayList<>())
        .build();
  }
}
