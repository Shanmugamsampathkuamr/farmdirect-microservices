package com.admin_service.dto;

import com.admin_service.model.AdminRole;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminResponseDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private AdminRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
}
