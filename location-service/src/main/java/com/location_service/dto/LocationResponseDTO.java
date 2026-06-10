package com.location_service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationResponseDTO {

    private Long id;
    private Long referenceId;
    private String referenceType;
    private String city;
    private String district;
    private String state;
    private String village;
    private String pincode;
    private Double latitude;
    private Double longitude;
    private LocalDateTime createdAt;
}
