package com.runner_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.resource.transaction.spi.SynchronizationRegistry;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RunnerRequestDTO {

    @NotBlank(message = "full name is requird")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email( message = "invalid email formate")
    private String email;

    @NotBlank(message = "Passwprd is required")
    @Size(min = 6 , message = "password must be at least 6 char")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid Indian phone")
    private String phone;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "district is required")
    private String district;

    @NotBlank(message = "vehicleType is required")
    private String vehicleType;

    @NotBlank(message = "vehicleType is required")
    private String vehicleNumber;

    @NotBlank(message = "aadhar is required")
    @Pattern(regexp = "^[0-9]{12}$" , message = "Invalid aadhar number")
    private String aadharNumber;

    @NotBlank(message = "licenseNumber is required")
    private String licenseNumber;

    private String state;

    private String village;

    private Double latitude;

    private Double longitude;

    private String pincode;



}
