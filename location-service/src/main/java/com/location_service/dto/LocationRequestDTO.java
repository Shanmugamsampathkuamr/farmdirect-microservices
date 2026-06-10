package com.location_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {

    @NotNull (message = "Referenced ID is required")
    private Long referenceId;

    @NotBlank(message = "Reference type is required")
    private String referenceType;

    @NotBlank(message =  "city is required")
    private String city;

    @NotBlank(message =  "district is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String village;

    private String pincode;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "longitude is required")
    private Double longitude;

}
