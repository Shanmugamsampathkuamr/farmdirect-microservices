package com.admin_service.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunnerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String district;
    private String verificationStatus;
    private String status;
}
