package io.github.bohdanzhuvak.onlinestore.features.customer.dto.delivery;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeliveryAddressRequest {
    @NotBlank(message = "Street is required")
    private String street;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "Postal code is required")
    @Pattern(regexp = "^[0-9A-Za-z\\s-]{3,10}$", message = "Invalid postal code format")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phone;

    private Boolean isDefault = false;
}
