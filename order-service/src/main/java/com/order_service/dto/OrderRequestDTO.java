package com.order_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotNull(message =  "Ordered quantity is required")
    @Positive(message = "Ordered quantity is positive")
    private Double orderedQuantityInKg;

    @NotBlank(message = "delivery Address is required")
    private String deliveryAddress;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message =  "district is requird")
    private String district;
}
