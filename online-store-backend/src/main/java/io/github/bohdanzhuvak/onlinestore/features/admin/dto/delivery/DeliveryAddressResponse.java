package io.github.bohdanzhuvak.onlinestore.features.admin.dto.delivery;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DeliveryAddressResponse {
  private Long id;
  private Long userId;
  private String street;
  private String city;
  private String postalCode;
  private String country;
  private String phone;
  private LocalDateTime createdAt;
}
