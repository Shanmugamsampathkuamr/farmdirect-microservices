package com.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {

    @NotBlank(message = "Farmer Id is required")
    private Long farmerId;

    @NotBlank(message =  "corpName is required")
    private String cropName;

    private String description;

    @NotBlank(message = "Quantity is required")
    @Positive(message = "Quantitiy must be a positive")
    private Double quantityInKg;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "District is required")
    private String District;

    private String village;

    @NotNull(message =  "Harvest date is required")
    private LocalDate harvestDate;

    @NotNull(message = "Storable days is requird")
    @Positive(message = "Storable days must be positive")
    private Integer storableDays;


}
