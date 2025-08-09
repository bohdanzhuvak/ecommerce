package io.github.bohdanzhuvak.onlinestore.user.controller;

import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categories")
@NoArgsConstructor
public class CategoryController {

  @GetMapping
  public String getCategories() {
    throw new UnsupportedOperationException();
  }
}
