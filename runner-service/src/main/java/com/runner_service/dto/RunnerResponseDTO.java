package com.runner_service.dto;


import com.runner_service.model.RunnerStatus;
import com.runner_service.model.RunnerVerificationStatus;
import lombok.*;
import java.time.LocalDateTime;

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
    private String vehicleType;
    private String vehicleNumber;
    private String aadharNumber;
    private String licenseNumber;
    private String aadharDocumentPath;
    private String licenseDocumentPath;
    private RunnerStatus status;
    private RunnerVerificationStatus verificationStatus;
    private LocalDateTime createdAt;
}