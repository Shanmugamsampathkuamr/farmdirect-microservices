package com.admin_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRequestDTO {

    @NotBlank(message =  "FullName is required")
    private String fullName;

    @NotBlank(message =  "Email is required")
    @Email(message = "Invalid mail format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6 , message = "password must be at least 6 char")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Invalid indian phone number")
    private String phone;
}
