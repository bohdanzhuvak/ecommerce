package io.github.bohdanzhuvak.onlinestore.features.customer.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotNull(message = "Delivery address ID is required")
    private Long deliveryAddressId;
}
