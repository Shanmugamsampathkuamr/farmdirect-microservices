package com.farmer_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDTO {

    private Long referenceId;
    private String referenceType;
    private String city;
    private String district;
    private String state;
    private String village;
    private String pincode;
    private Double latitude;
    private Double longitude;

}
