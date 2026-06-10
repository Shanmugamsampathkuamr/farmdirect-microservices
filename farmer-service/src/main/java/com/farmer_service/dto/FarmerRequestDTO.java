package com.farmer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
@Builder
public class FarmerRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required ")
    @Email(message = "Invalid email formate")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6 , message = "Password must be at least 6 char")
    private String password;

    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$",message = "Invalid Indian phone number")
    private String phone;

    @NotBlank(message = "village is required")
    private String village;

    @NotBlank(message = "city is required")
    private String city;

    @NotBlank(message = "Distirict is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String farmSizeInAcres;

    @NotBlank(message =  "Aadhar number is required")
    @Pattern(regexp = "^[0-9]{12}$",message = "Invalid Aadhar number ")
    private String aadharNumber;


    private Double latitude;

    private Double longitude;

    private String pincode;



}
