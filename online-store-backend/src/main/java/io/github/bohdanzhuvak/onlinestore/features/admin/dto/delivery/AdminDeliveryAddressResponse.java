package io.github.bohdanzhuvak.onlinestore.features.admin.dto.delivery;

import io.github.bohdanzhuvak.onlinestore.features.admin.dto.AdminResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminDeliveryAddressResponse extends AdminResponseDto {
  private Long id;
  private Long userId;
  private String street;
  private String city;
  private String postalCode;
  private String country;
  private String phone;
  private LocalDateTime createdAt;
}
