package io.github.bohdanzhuvak.onlinestore.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "delivery_addresses")
public class DeliveryAddress extends AuditableEntity {

  @NotNull(message = "User is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

  @NotBlank(message = "Street is required")
  @Size(max = 200, message = "Street must not exceed 200 characters")
  @Column(nullable = false, length = 200)
    private String street;

  @NotBlank(message = "City is required")
  @Size(max = 100, message = "City must not exceed 100 characters")
  @Column(nullable = false, length = 100)
    private String city;

  @NotBlank(message = "Postal code is required")
  @Size(max = 20, message = "Postal code must not exceed 20 characters")
  @Column(nullable = false, length = 20)
    private String postalCode;

  @NotBlank(message = "Country is required")
  @Size(max = 100, message = "Country must not exceed 100 characters")
  @Column(nullable = false, length = 100)
    private String country;

  @NotBlank(message = "Phone is required")
  @Size(max = 20, message = "Phone must not exceed 20 characters")
  @Column(nullable = false, length = 20)
    private String phone;

  @NotNull(message = "Is default flag is required")
    @Column(nullable = false)
    @Builder.Default
    private Boolean isDefault = false;

  @NotNull(message = "Is technical flag is required")
    @Column(nullable = false)
    @Builder.Default
    private Boolean isTechnical = false;

    @Column(name = "original_id")
    private Long originalId;
}
