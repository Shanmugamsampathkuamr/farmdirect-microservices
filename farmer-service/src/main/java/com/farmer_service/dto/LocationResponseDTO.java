package com.farmer_service.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationResponseDTO {
    private Long id;
    private Long referenceId;
    private String referenceType;
    private String city;
    private String district;
    private String state;
}
