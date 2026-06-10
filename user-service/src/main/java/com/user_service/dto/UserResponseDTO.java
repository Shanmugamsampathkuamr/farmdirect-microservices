package com.user_service.dto;


import com.user_service.model.UserRole;
import com.user_service.model.UserStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String city;
    private String district;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime createdAt;
}
